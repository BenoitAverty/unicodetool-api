package org.unicodetool.graphql.schema;

import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLList;
import graphql.schema.GraphQLObjectType;
import org.unicodetool.graphql.datafetchers.CodepointsDataFetcher;

public class CodepointsQuery {
    public static GraphQLFieldDefinition.Builder graphQLField() {
        return GraphQLFieldDefinition.newFieldDefinition()
                .name("codepoints")
                .type(GraphQLList.list(Codepoint.graphQLObject()))
                .dataFetcher(new CodepointsDataFetcher());
    }
}
