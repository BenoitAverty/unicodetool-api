package org.unicodetool.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.unicodetool.graphql.schema.Codepoint;
import org.unicodetool.graphql.schema.CodepointValue;
import org.unicodetool.ucd.UnicodeCharacterDatabaseFinder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
        this.codepointConverter = converter;
    }

    public List<Codepoint> findCodepointForCharacters(String input) {

        if(input.length() == 1) {
            int codepoint = Character.codePointAt(input, 0);

            return unicodeCharacterDatabaseFinder.findCodepoint(CodepointValue.of(codepoint).getValue())
                    .map(codepointConverter::convert)
                    .map(Arrays::asList)
                    .orElse(Collections.emptyList());
        }

        return Collections.emptyList();
    }
}
