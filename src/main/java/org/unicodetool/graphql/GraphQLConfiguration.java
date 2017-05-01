package org.unicodetool.graphql;

import graphql.GraphQL;
import graphql.Scalars;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLSchema;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.unicodetool.graphql.schema.Codepoint;
import org.unicodetool.graphql.schema.CodepointQuery;
import org.unicodetool.graphql.schema.CodepointsQuery;

import java.util.Arrays;

@Configuration
public class GraphQLConfiguration {

    private Object List;

    @Bean
    public GraphQLSchema graphQLSchema() {

        GraphQLObjectType queryType = GraphQLObjectType.newObject()
                .name("Root")
                .field(CodepointsQuery.graphQLField())
                .field(CodepointQuery.graphQLField())
                .build();

        return GraphQLSchema.newSchema()
                .query(queryType)
                .build();
    }

    @Bean
    public GraphQL graphQL() {
        return GraphQL.newGraphQL(graphQLSchema()).build();
    }
}
