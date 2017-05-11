package org.unicodetool.graphql;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.coxautodev.graphql.tools.SchemaParser;
import com.oembedler.moon.graphql.boot.SchemaParserDictionary;
import graphql.Scalars;
import graphql.execution.ExecutionStrategy;
import graphql.execution.SimpleExecutionStrategy;
import graphql.schema.GraphQLScalarType;
import graphql.schema.GraphQLSchema;
import graphql.servlet.SimpleGraphQLServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.unicodetool.graphql.schema.Codepoint;
import org.unicodetool.graphql.schema.CodepointValue;
import org.unicodetool.graphql.schema.Properties;

import java.util.List;

@Configuration
public class GraphQLConfiguration {

    @Bean
    public GraphQLScalarType codepointValueGraphQLScalar() {
        return CodepointValue.scalar();
    }

    @Bean
    SchemaParserDictionary schemaParserDictionary() {
        return new SchemaParserDictionary().dictionary(Codepoint.class, Properties.class);
    }

    /*@Bean
    ExecutionStrategy executionStrategy() {
        return new SimpleExecutionStrategy();
    }

    @Bean
    GraphQLSchema graphQLSchema(List<GraphQLResolver<?>> graphQLResolvers) {
        return SchemaParser.newParser()
                .file("unicode.graphqls")
                .dictionary(Codepoint.class, Properties.class)
                .scalars(CodepointValue.scalar())
                .resolvers(graphQLResolvers)
                .build()
                .makeExecutableSchema()
        ;
    }

    @Bean
    public ServletRegistrationBean<SimpleGraphQLServlet> graphqlServletRegistrationBean(GraphQLSchema graphQLSchema, ExecutionStrategy executionStrategy) {
        return new ServletRegistrationBean<>(
                new SimpleGraphQLServlet(graphQLSchema, executionStrategy),
                "/graphql");
    }*/
}
