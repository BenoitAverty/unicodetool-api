package org.unicodetool.graphql;

import com.oembedler.moon.graphql.boot.SchemaParserDictionary;
import graphql.schema.GraphQLScalarType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.unicodetool.graphql.schema.Codepoint;
import org.unicodetool.graphql.schema.CodepointValue;
import org.unicodetool.graphql.schema.Properties;
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
                Properties.class
        );
    }
}
