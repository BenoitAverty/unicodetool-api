package org.unicodetool.graphql.schema;

import com.coxautodev.graphql.tools.GraphQLRootResolver;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.stereotype.Component;
import org.unicodetool.graphql.schema.Codepoint;
import org.unicodetool.graphql.schema.Properties;

import java.util.Arrays;
import java.util.List;

@Component
public class Query implements GraphQLRootResolver {
    public Codepoint codepoint(Integer value) {
        return new Codepoint(
                value,
                "Name for value "+value,
                new Properties(
                        "Name for value " + value,
                        "Probably Basic latin ?"
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
