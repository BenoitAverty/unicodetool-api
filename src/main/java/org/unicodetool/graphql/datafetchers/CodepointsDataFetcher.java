package org.unicodetool.graphql.datafetchers;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.unicodetool.graphql.schema.Codepoint;

import java.util.Arrays;
import java.util.List;

public class CodepointsDataFetcher implements DataFetcher<List<Codepoint>> {

    @Override
    public List<Codepoint> get(DataFetchingEnvironment environment) {
        Codepoint c1 = new Codepoint();
        c1.value = 0x41;
        c1.name = "LATIN CAPITAL LETTER A";
        c1.properties.name = c1.name;
        c1.properties.block = "Basic Latin";

        Codepoint c2 = new Codepoint();
        c2.value = 0x42;
        c2.name = "LATIN CAPITAL LETTER B";
        c2.properties.name = c2.name;
        c2.properties.block = "Basic Latin";

        Codepoint c3 = new Codepoint();
        c3.value = 0x43;
        c3.name = "LATIN CAPITAL LETTER C";
        c3.properties.name = c3.name;
        c3.properties.block = "Basic Latin";

        return Arrays.asList(c1, c2, c3);
    }
}
