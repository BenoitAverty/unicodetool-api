package org.unicodetool.graphql.schema;

import com.coxautodev.graphql.tools.GraphQLRootResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.unicodetool.graphql.exceptions.CodepointNotFound;
import org.unicodetool.ucd.UnicodeCharacterDatabaseFinder;
import org.unicodetool.ucd.schema.CodePoint;

import java.util.Arrays;
import java.util.List;

@Component
public class Query implements GraphQLRootResolver {

    private final UnicodeCharacterDatabaseFinder unicodeCharacterDatabaseFinder;

    @Autowired
    public Query(UnicodeCharacterDatabaseFinder ucdf) {
        this.unicodeCharacterDatabaseFinder = ucdf;
    }

    public Codepoint codepoint(int value) {
        CodePoint cp = unicodeCharacterDatabaseFinder.findCodepoint(value).orElseThrow(CodepointNotFound::new);

        return new Codepoint(
                value,
                cp.getNa(),
                new Properties(
                        cp.getNa(),
                        cp.getBlk()
                )
        );
    }

    public List<Codepoint> codepoints() {
        Codepoint c1 = new Codepoint(
                0x41,
                "LATIN CAPITAL LETTER A",
                new Properties(
                        "LATIN CAPITAL LETTER A",
                        "Basic Latin"
                )
        );
        Codepoint c2 = new Codepoint(
                0x42,
                "LATIN CAPITAL LETTER B",
                new Properties(
                        "LATIN CAPITAL LETTER B",
                        "Basic Latin"
                )
        );
        Codepoint c3 = new Codepoint(
                0x43,
                "LATIN CAPITAL LETTER C",
                new Properties(
                        "LATIN CAPITAL LETTER C",
                        "Basic Latin"
                )
        );

        return Arrays.asList(c1, c2, c3);
    }
}
