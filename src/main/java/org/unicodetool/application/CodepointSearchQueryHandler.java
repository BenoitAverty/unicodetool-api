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

    private UnicodeCharacterDatabaseFinder unicodeCharacterDatabaseFinder;

    @Autowired
    public CodepointSearchQueryHandler(UnicodeCharacterDatabaseFinder finder) {
        this.unicodeCharacterDatabaseFinder = finder;
    }

    public List<Codepoint> findByName(String name) {
        return unicodeCharacterDatabaseFinder.findByName(name)
                .map(CodepointConverter.withFormattedValue("0041"))
                .collect(Collectors.toList());
    }
}
