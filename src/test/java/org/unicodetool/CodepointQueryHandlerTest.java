package org.unicodetool;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.unicodetool.application.CodepointQueryHandler;
import org.unicodetool.application.exceptions.CodepointFormatException;
import org.unicodetool.application.exceptions.ValueOutsideRangeException;
import org.unicodetool.graphql.schema.Codepoint;
import org.unicodetool.ucd.UnicodeCharacterDatabaseFinder;

import javax.swing.text.html.Option;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

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

        this.codepointQueryHandler = new CodepointQueryHandler(finder);
    }

    @Nested
    @DisplayName("Finding Codepoint By Value")
    class findByValue {
        @Test
        @DisplayName("Existing Codepoint")
        public void findExistingCodepoint() {
            final String expectedName = "LATIN CAPITAL LETTER A";
            final String expectedBlock = "ASCII";

            final Optional<Codepoint> actual = codepointQueryHandler.findCodepoint(65);

            assertTrue(actual.isPresent(), "Codepoint was not found");
            assertAll("Returned codepoint is not correct",
                    () -> assertEquals(65, actual.get().getValue(),
                            "Incorrect value."),
                    () -> assertEquals(expectedName, actual.get().getName(),
                            "Incorrect name."),
                    () -> assertEquals(expectedBlock, actual.get().getProperties().getBlock(),
                            "Incorrect block.")
            );
        }

        @Test
        @DisplayName("Value outside range")
        public void findOutsideRange() {
            assertThrows(ValueOutsideRangeException.class, () -> codepointQueryHandler.findCodepoint(1114112));
        }

        @Test
        @DisplayName("Negative value")
        public void findNegative() {
            assertThrows(ValueOutsideRangeException.class, () -> codepointQueryHandler.findCodepoint(-1));
        }
    }

    @Nested
    @DisplayName("Finding Codepoint By String Value")
    class findByStringValue {
        @Test
        @DisplayName("Existing Codepoint")
        public void findExistingCodepoint() {
            final String expectedName = "LATIN CAPITAL LETTER A";
            final String expectedBlock = "ASCII";

            final Optional<Codepoint> actual = codepointQueryHandler.findCodepoint("0041");

            assertTrue(actual.isPresent(), "Codepoint was not found");
            assertAll("Returned codepoint is not correct",
                    () -> assertEquals(65, actual.get().getValue(),
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

            final Optional<Codepoint> actual = codepointQueryHandler.findCodepoint("U+0041");

            assertTrue(actual.isPresent(), "Codepoint was not found");
        }

        @Test
        @DisplayName("Parameter with \"0x\" prefix")
        public void findCodepointWithPrefix2() {

            final Optional<Codepoint> actual = codepointQueryHandler.findCodepoint("0x0041");

            assertTrue(actual.isPresent(), "Codepoint was not found");
        }

        @Test
        @DisplayName("Parameter with \"0x\" and \"U+\" prefix")
        public void findCodepointWithPrefix3() {

            final Optional<Codepoint> actual = codepointQueryHandler.findCodepoint("U+0x0041");

            assertTrue(actual.isPresent(), "Codepoint was not found");
        }

        @Test
        @DisplayName("Value outside range")
        public void findOutsideRange() {
            assertThrows(ValueOutsideRangeException.class, () -> codepointQueryHandler.findCodepoint("110000"));
        }

        @ParameterizedTest
        @ValueSource(strings = { "ABCG", "test", "", "FFFFFFFF", "0xU+0041" })
        @DisplayName("Value with incorrect format")
        public void findWithIncorrectFormat(String strValue) {
            assertThrows(
                    CodepointFormatException.class,
                    () -> codepointQueryHandler.findCodepoint(strValue)
            );
        }
    }
}
