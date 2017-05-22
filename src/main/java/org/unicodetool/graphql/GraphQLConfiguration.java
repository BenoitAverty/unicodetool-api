package org.unicodetool.graphql;

import com.oembedler.moon.graphql.boot.SchemaParserDictionary;
import graphql.schema.GraphQLScalarType;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.unicodetool.graphql.schema.*;
import org.unicodetool.graphql.schema.Character;

@Configuration
public class GraphQLConfiguration {

    /**
     * Define the bean for the custom GraphQLScalarType
     *
     * @see CodepointValue
     */
    @Bean
    public GraphQLScalarType codepointValueGraphQLScalar() {
        return CodepointValue.scalar();
    }

    /**
     * Dictionary for the classes used in the graphQL schema
     *
     * @see Codepoint
     * @see Properties
     */
    @Bean
    SchemaParserDictionary schemaParserDictionary() {
        return new SchemaParserDictionary().dictionary(
                Codepoint.class,
                Character.class,
                NonCharacter.class,
                Surrogate.class,
                Reserved.class,
                Properties.class,
                NameAlias.class
        );
    }

    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/graphql", config);
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
        bean.setOrder(0);
        return bean;
    }
}
