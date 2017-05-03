package org.unicodetool.graphql;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.coxautodev.graphql.tools.SchemaParser;
import graphql.execution.ExecutionStrategy;
import graphql.execution.SimpleExecutionStrategy;
import graphql.schema.GraphQLSchema;
import graphql.servlet.SimpleGraphQLServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.unicodetool.graphql.schema.Codepoint;
import org.unicodetool.graphql.schema.Properties;

import java.util.List;

/**
 * Created by benoit on 5/1/17.
 */
@Configuration
public class GraphQLConfiguration {

    @Bean
    ExecutionStrategy executionStrategy() {
        return new SimpleExecutionStrategy();
    }

    @Bean
    GraphQLSchema graphQLSchema(List<GraphQLResolver<?>> graphQLResolvers) {
        return SchemaParser.newParser()
                .file("unicode.graphqls")
                .dictionary(Codepoint.class, Properties.class)
                .resolvers(graphQLResolvers)
                .build()
                .makeExecutableSchema()
        ;
    }

    @Bean
    public ServletRegistrationBean graphqlServletRegistrationBean(GraphQLSchema graphQLSchema, ExecutionStrategy executionStrategy) {
        return new ServletRegistrationBean(
                new SimpleGraphQLServlet(graphQLSchema, executionStrategy),
                "/graphql");
    }
}
