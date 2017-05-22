package org.unicodetool.application;

import lombok.NoArgsConstructor;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ContainerExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.unicodetool.application.exceptions.CodepointFormatException;
import org.unicodetool.application.exceptions.ValueOutsideRangeException;
import org.unicodetool.graphql.schema.*;
import org.unicodetool.graphql.schema.Character;
import org.unicodetool.ucd.UnicodeCharacterDatabaseFinder;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Tests for the {@link CodepointQueryHandler} use cases.
 */
public class CodepointQueryHandlerTest {


    private CodepointQueryHandler codepointQueryHandler;

    @BeforeEach
    public void setUp() throws URISyntaxException, IOException {
        Jaxb2Marshaller m = new Jaxb2Marshaller();
        m.setPackagesToScan("org.unicodetool.ucd.schema");

        Resource testUcd = new ClassPathResource("/ucd/ucd.test.flat.xml");

        UnicodeCharacterDatabaseFinder finder = new UnicodeCharacterDatabaseFinder(m, testUcd.getInputStream());
        finder.init();

        CodepointConverter converter = new CodepointConverter();

        this.codepointQueryHandler = new CodepointQueryHandler(finder, converter);
    }


    @Nested
    @DisplayName("Finding Codepoint By String Value")
    class ByStringValue {
        @Test
        @DisplayName("Existing Codepoint")
        public void findExistingCodepoint() {
            final String expectedName = "LATIN CAPITAL LETTER A";
            final String expectedBlock = "ASCII";
            final CodepointValue expectedCodepointValue = CodepointValue.of("0041");

            final Optional<Codepoint> actual = codepointQueryHandler.findCodepoint(CodepointValue.of("0041"));

            assertTrue(actual.isPresent(), "Codepoint was not found");
            assertAll("Returned codepoint is not correct",
                    () -> assertEquals(expectedCodepointValue, actual.get().getValue(),
                            "Incorrect value."),
                    () -> assertEquals(expectedName, actual.get().getName(),
                            "Incorrect name."),
                    () -> assertEquals(expectedBlock, actual.get().getProperties().getBlock(),
                            "Incorrect block.")
            );
        }

        @Test
        @DisplayName("Parameter with \"U+\" prefix")
        public void findCodepointWithPrefix1() {

            final Optional<Codepoint> actual = codepointQueryHandler.findCodepoint(CodepointValue.of("U+0041"));

            assertTrue(actual.isPresent(), "Codepoint was not found");
        }

        @Test
        @DisplayName("Parameter with \"0x\" prefix")
        public void findCodepointWithPrefix2() {

            final Optional<Codepoint> actual = codepointQueryHandler.findCodepoint(CodepointValue.of("0x0041"));

            assertTrue(actual.isPresent(), "Codepoint was not found");
        }

        @Test
        @DisplayName("Parameter with \"0x\" and \"U+\" prefix")
        public void findCodepointWithPrefix3() {

            final Optional<Codepoint> actual = codepointQueryHandler.findCodepoint(CodepointValue.of("U+0x0041"));

            assertTrue(actual.isPresent(), "Codepoint was not found");
        }

        @Test
        @DisplayName("Parameter with lowercase letters")
        public void findCodepointWithLowercase() {

            final Optional<Codepoint> actual = codepointQueryHandler.findCodepoint(CodepointValue.of("U+001f"));

            assertTrue(actual.isPresent(), "Codepoint was not found");
        }

        @Test
        @DisplayName("Value on the limit of a range codepoint")
        public void findCodepointAtRangeLimit() {
            final CodepointValue expectedCodepointValue = CodepointValue.of("FFFFF");

            final Optional<Codepoint> actual = codepointQueryHandler.findCodepoint(CodepointValue.of("FFFFF"));

            assertTrue(actual.isPresent(), "Codepoint was not found");
            assertEquals(expectedCodepointValue, actual.get().getValue(),
                            "Incorrect value.");
        }

        @Test
        @DisplayName("Value outside range")
        public void findOutsideRange() {
            assertThrows(
                    ValueOutsideRangeException.class,
                    () -> codepointQueryHandler.findCodepoint(CodepointValue.of("110000"))
            );
        }

        @ParameterizedTest
        @ValueSource(strings = { "ABCG", "test", "", "FFFFFFFF", "0xU+0041" })
        @DisplayName("Value with incorrect format")
        public void findWithIncorrectFormat(String strValue) {
            assertThrows(
                    CodepointFormatException.class,
                    () -> codepointQueryHandler.findCodepoint(CodepointValue.of(strValue))
            );
        }

        @DisplayName("Finding different character types")
        @ParameterizedTest(name = "Finding a Codepoint of type {1}")
        @ArgumentsSource(CodepointTypesArgsSource.class)
        public void testFindTypes(CodepointValue inputCodepoint, Class expectedType) {
            final Class actualType = codepointQueryHandler.findCodepoint(inputCodepoint).get().getClass();

            assertTrue(expectedType.isAssignableFrom(actualType),
                    "Returned codepoint should be a "+expectedType+" but it is a "+actualType);
        }
    }
}

/**
 * Provides arguments for the {@link CodepointQueryHandlerTest.ByStringValue#testFindTypes(CodepointValue, Class)} test
 */
//TODO: See if there is a way to simplify with @MethodSource...
class CodepointTypesArgsSource implements ArgumentsProvider {
    public CodepointTypesArgsSource() {}
    @Override
    public Stream<? extends Arguments> arguments(ContainerExtensionContext context) throws Exception {
        return Stream.of(
                ObjectArrayArguments.create(CodepointValue.of("0041"), Character.class),
                ObjectArrayArguments.create(CodepointValue.of("FFFFE"), NonCharacter.class),
                ObjectArrayArguments.create(CodepointValue.of("D801"), Surrogate.class),
                ObjectArrayArguments.create(CodepointValue.of("0378"), Reserved.class)
        );
    }
}
