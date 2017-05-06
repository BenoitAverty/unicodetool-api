package org.unicodetool.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.unicodetool.graphql.schema.Codepoint;
import org.unicodetool.graphql.schema.Properties;
import org.unicodetool.ucd.UnicodeCharacterDatabaseFinder;

import javax.swing.text.html.Option;
import java.util.Optional;

/**
 * Application service that handles "codepoint" queries from the graphql interface (find  single codepoint)
 */
@Service
public class CodepointQueryHandler {

    private final UnicodeCharacterDatabaseFinder unicodeCharacterDatabaseFinder;

    @Autowired
    public CodepointQueryHandler(UnicodeCharacterDatabaseFinder finder) {
        this.unicodeCharacterDatabaseFinder = finder;
    }

    /**
     * Find a codepoint by its integer value.
     *
     * @param value The integer value of the codepoint.
     * @return The codepoint represented by a graphql object.
     */
    public Optional<Codepoint> findCodepoint(int value) {

        return unicodeCharacterDatabaseFinder.findCodepoint(value)
                .map(xmlCodepoint -> new Codepoint(
                        Integer.parseInt(xmlCodepoint.getCp(), 16),
                        xmlCodepoint.getNa(),
                        new Properties(
                                xmlCodepoint.getNa(),
                                xmlCodepoint.getBlk()
                        )
                ));
    }
}
