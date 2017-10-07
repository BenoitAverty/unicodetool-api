package org.unicodetool.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.unicodetool.graphql.schema.Character;
import org.unicodetool.graphql.schema.Codepoint;
import org.unicodetool.ucd.UnicodeCharacterDatabaseFinder;

import java.util.Optional;

/**
 * Application service that handles "characters" queries from the graphql interface (find codepoints based on strings)
 */
@Service
public class CharactersQueryHandler {
    private final UnicodeCharacterDatabaseFinder unicodeCharacterDatabaseFinder;
    private final CodepointConverter codepointConverter;

    @Autowired
    public CharactersQueryHandler(UnicodeCharacterDatabaseFinder finder, CodepointConverter converter) {
        this.unicodeCharacterDatabaseFinder = finder;
        codepointConverter = converter;
    }

    public Optional<Codepoint> findCodepointForCharacter(String input) {
        return unicodeCharacterDatabaseFinder.findCodepoint("00AF")
                .map(codepointConverter::convert);
    }
}
