package org.unicodetool.graphql.schema;

import graphql.Scalars;
import graphql.schema.GraphQLArgument;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLList;
import graphql.schema.GraphQLNonNull;
import org.unicodetool.graphql.datafetchers.CodepointDataFetcher;
import org.unicodetool.graphql.datafetchers.CodepointsDataFetcher;

public class CodepointQuery {
    public static GraphQLFieldDefinition.Builder graphQLField() {
        return GraphQLFieldDefinition.newFieldDefinition()
                .name("codepoint")
                .type(Codepoint.graphQLObject())
                .argument(GraphQLArgument.newArgument()
                        .name("value")
                        .type(GraphQLNonNull.nonNull(Scalars.GraphQLInt))
                        .description("The Codepoint number as int")
                )
                .dataFetcher(new CodepointDataFetcher());
    }
}
