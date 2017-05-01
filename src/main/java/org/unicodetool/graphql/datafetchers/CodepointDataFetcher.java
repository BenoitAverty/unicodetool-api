package org.unicodetool.graphql.datafetchers;


import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.unicodetool.graphql.schema.Codepoint;

public class CodepointDataFetcher implements DataFetcher<Codepoint> {

    @Override
    public Codepoint get(DataFetchingEnvironment environment) {
        Codepoint result = new Codepoint();
        result.value = environment.getArgument("value");
        result.name = "Correct name for value " + result.value;
        result.properties.name = result.name;
        result.properties.block = "Basic Latin (probably)";

        return result;
    }
}
