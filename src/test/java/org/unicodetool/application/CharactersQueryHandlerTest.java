package org.unicodetool.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
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

/**
 * Tests for the {@link CharactersQueryHandler} use cases.
 */
public class CharactersQueryHandlerTest {

        private CharactersQueryHandler charactersQueryHandler;

        @BeforeEach
        public void setUp() throws URISyntaxException, IOException {
            Jaxb2Marshaller m = new Jaxb2Marshaller();
            m.setPackagesToScan("org.unicodetool.ucd.schema");

            Resource testUcd = new ClassPathResource("/ucd/ucd.test.flat.xml");

            UnicodeCharacterDatabaseFinder finder = new UnicodeCharacterDatabaseFinder(m, testUcd.getInputStream());
            finder.init();

            CodepointConverter converter = new CodepointConverter();

            this.charactersQueryHandler = new CharactersQueryHandler(finder, converter);
        }

    @Nested
    @DisplayName("Finding codepoints from a single character")
    class FindByName {

        @DisplayName("Simple ascii character")
        @Test
        public void findByCompleteName() {
            final String expectedName = "LATIN CAPITAL LETTER A";
            final String expectedBlock = "ASCII";
            final CodepointValue expectedCodepointValue = CodepointValue.of("0041");

            final List<Codepoint> actual = charactersQueryHandler.findCodepointForCharacters("A");

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
    }
}
