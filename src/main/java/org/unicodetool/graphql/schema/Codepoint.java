package org.unicodetool.graphql.schema;

import graphql.Scalars;
import graphql.schema.DataFetcher;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLObjectType;
import graphql.schema.StaticDataFetcher;

import java.util.Arrays;
import java.util.HashMap;

public class Codepoint {
    public Integer value;
    public String name;

    public Properties properties = new Properties();

    public static GraphQLObjectType graphQLObject() {
        return GraphQLObjectType.newObject()
                .name("Codepoint")
                .description("A Unicode codepoint.")
                .field(GraphQLFieldDefinition.newFieldDefinition()
                        .name("value")
                        .type(Scalars.GraphQLInt)
                )
                .field(GraphQLFieldDefinition.newFieldDefinition()
                        .name("name")
                        .type(Scalars.GraphQLString)
                )
                .field(GraphQLFieldDefinition.newFieldDefinition()
                        .name("properties")
                        .type(Properties.graphQLObject())
                )
                .build();
    }
}
