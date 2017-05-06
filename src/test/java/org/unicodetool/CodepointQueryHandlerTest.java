package org.unicodetool;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.unicodetool.application.CodepointQueryHandler;
import org.unicodetool.graphql.schema.Codepoint;
import org.unicodetool.ucd.UnicodeCharacterDatabaseFinder;

import javax.swing.text.html.Option;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.net.URISyntaxException;
import java.util.Optional;

/**
 * Tests for the {@link CodepointQueryHandler} use cases.
 */
public class CodepointQueryHandlerTest {


    private CodepointQueryHandler codepointQueryHandler;

    @BeforeEach
    public void setUp() throws URISyntaxException {
        Jaxb2Marshaller m = new Jaxb2Marshaller();
        m.setPackagesToScan("org.unicodetool.ucd.schema");

        File testUcd = new File(this.getClass().getResource("/ucd/ucd.test.flat.xml").toURI());

        UnicodeCharacterDatabaseFinder finder = new UnicodeCharacterDatabaseFinder(m, testUcd);
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
    }

}
