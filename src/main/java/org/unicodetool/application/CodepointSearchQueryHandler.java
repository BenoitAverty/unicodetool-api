package org.unicodetool.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.unicodetool.graphql.schema.Codepoint;
import org.unicodetool.ucd.UnicodeCharacterDatabaseFinder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Application service that handles "codepointSearch" queries from the graphql interface (find codepoints based on several criteria)
 */
@Service
public class CodepointSearchQueryHandler {

    private final UnicodeCharacterDatabaseFinder unicodeCharacterDatabaseFinder;
    private final CodepointConverter codepointConverter;

    @Autowired
    public CodepointSearchQueryHandler(UnicodeCharacterDatabaseFinder finder, CodepointConverter converter) {
        this.unicodeCharacterDatabaseFinder = finder;
        this.codepointConverter = converter;
    }

    public List<Codepoint> findByName(String name) {

        String formattedName = name.trim();
        if(formattedName.isEmpty()) {
            return new ArrayList<>();
        }

        return unicodeCharacterDatabaseFinder.findByName(formattedName).stream()
                .map(codepointConverter::convert)
                .collect(Collectors.toList());
    }
}
