package org.unicodetool.application;

import org.junit.jupiter.api.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.unicodetool.graphql.schema.Codepoint;
import org.unicodetool.graphql.schema.CodepointValue;
import org.unicodetool.ucd.UnicodeCharacterDatabaseFinder;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for the {@link CodepointSearchQueryHandler} use cases.
 */
public class CodepointSearchQueryHandlerTest {


    private CodepointSearchQueryHandler codepointSearchQueryHandler;

    @BeforeEach
    public void setUp() throws URISyntaxException, IOException {
        Jaxb2Marshaller m = new Jaxb2Marshaller();
        m.setPackagesToScan("org.unicodetool.ucd.schema");

        Resource testUcd = new ClassPathResource("/ucd/ucd.test.flat.xml");

        UnicodeCharacterDatabaseFinder finder = new UnicodeCharacterDatabaseFinder(m, testUcd.getInputStream());
        finder.init();

        CodepointConverter converter = new CodepointConverter();

        this.codepointSearchQueryHandler = new CodepointSearchQueryHandler(finder, converter);
    }

    @Nested
    @DisplayName("Finding codepoints by name")
    class FindByName {

        @DisplayName("Complete name provided")
        @Test
        public void findByCompleteName() {
            final String expectedName = "LATIN CAPITAL LETTER A";
            final String expectedBlock = "ASCII";
            final CodepointValue expectedCodepointValue = CodepointValue.of("0041");

            final List<Codepoint> actual = codepointSearchQueryHandler.findByName("LATIN CAPITAL LETTER A");

            assertEquals(1, actual.size(), "Expected 1 codepoint but got "+ actual.size());
            assertAll("Returned codepoint is not correct",
                    () -> assertEquals(expectedCodepointValue, actual.get(0).getValue(),
                            "Incorrect value."),
                    () -> assertEquals(expectedName, actual.get(0).getName(),
                            "Incorrect name."),
                    () -> assertEquals(expectedBlock, actual.get(0).getProperties().getBlock(),
                            "Incorrect block.")
            );
        }

        @DisplayName("Partial name provided")
        @Test
        public void findByPartialName() {
            final CodepointValue expectedCodepointValue = CodepointValue.of("0041");

            final List<Codepoint> actual = codepointSearchQueryHandler.findByName("CAPITAL LETTER A");

            assertEquals(1, actual.size(), "Expected 1 codepoint but got "+ actual.size());
            assertEquals(expectedCodepointValue, actual.get(0).getValue(), "Returned codepoint has incorrect value");
        }

        @DisplayName("With case mismatch")
        @Test
        public void findByLowercaseName() {
            final CodepointValue expectedCodepointValue = CodepointValue.of("0041");

            final List<Codepoint> actual = codepointSearchQueryHandler.findByName("capital letter a");

            assertEquals(1, actual.size(), "Expected 1 codepoint but got "+ actual.size());
            assertEquals(expectedCodepointValue, actual.get(0).getValue(), "Returned codepoint has incorrect value");
        }

        @DisplayName("With several matches")
        @Test
        public void findSeveralByName() {
            final CodepointValue expectedCodepointValue1 = CodepointValue.of("0041");
            final String expectedName1 = "LATIN CAPITAL LETTER A";
            final CodepointValue expectedCodepointValue2 = CodepointValue.of("1740");
            final String expectedName2 = "BUHID LETTER A";
            final CodepointValue expectedCodepointValue3 = CodepointValue.of("1820");
            final String expectedName3 = "MONGOLIAN LETTER A";

            final List<Codepoint> actual = codepointSearchQueryHandler.findByName("LETTER A");

            assertEquals(3, actual.size(), "Expected 3 codepoint but got "+ actual.size());
            assertAll(
                    () -> assertEquals(expectedCodepointValue1, actual.get(0).getValue(), "First Codepoint doesn't have the right value"),
                    () -> assertEquals(expectedName1, actual.get(0).getName(), "First Codepoint doesn't have the right name"),
                    () -> assertEquals(expectedCodepointValue2, actual.get(1).getValue(), "Second Codepoint doesn't have the right value"),
                    () -> assertEquals(expectedName2, actual.get(1).getName(), "Second Codepoint doesn't have the right name"),
                    () -> assertEquals(expectedCodepointValue3, actual.get(2).getValue(), "Third Codepoint doesn't have the right value"),
                    () -> assertEquals(expectedName3, actual.get(2).getName(), "Third Codepoint doesn't have the right name")
            );
        }

        @DisplayName("With an empty name")
        @Test
        public void findByEmptyName() {

            final List<Codepoint> actual = codepointSearchQueryHandler.findByName(" ");

            assertTrue(actual.isEmpty(), "Expected an empty list, but is has elements.");
        }
    }
}