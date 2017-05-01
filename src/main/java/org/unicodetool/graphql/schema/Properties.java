package org.unicodetool.graphql.schema;

import graphql.Scalars;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLObjectType;

public class Properties {
    public String name;
    public String block;

    public static GraphQLObjectType graphQLObject() {
        return GraphQLObjectType.newObject()
                .name("Properties")
                .description("Unicode character properties.")
                .field(GraphQLFieldDefinition.newFieldDefinition()
                        .name("name")
                        .type(Scalars.GraphQLString)
                )
                .field(GraphQLFieldDefinition.newFieldDefinition()
                        .name("block")
                        .type(Scalars.GraphQLString)
                )
                .build();
    }
}
