package org.unicodetool.graphql.schema;

import graphql.language.IntValue;
import graphql.language.StringValue;
import graphql.schema.Coercing;
import graphql.schema.GraphQLScalarType;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Represent the value of a codepoint.
 *
 * This is a custom scalar type for this GraphQL Schema and can be used to express a codepoint in various forms:
 * <li>
 *     <ul>Decimal integer (e.g. 65)</ul>
 *     <ul>Unicode representation (e.g. U+0041)</ul>
 *     <ul>Hexadecimal integer (e.g. 0x41)</ul>
 *     <ul>Unicode representation (e.g. 0041)</ul>
 * </li>
 */
@AllArgsConstructor
public class CodepointValue {

    @Getter
    private String value;

    public static CodepointValue of(String value) {
        return new CodepointValue(value);
    }

    public static CodepointValue of(Integer value) {
        return new CodepointValue(String.format("%04X", value & 0xFFFFFF));
    }

    public static GraphQLScalarType scalar() {
        final String description = "Numeric value of a codepoint. can be used to express a codepoint in various forms:\n"+
                " - Decimal integer (e.g. 65)\n"+
                " - Unicode representation (e.g. \"U+0041\")\n"+
                " - Unicode representation without suffix (e.g. \"0041\")\n" +
                " - Hexadecimal integer (e.g. \"0x41\")";

        return new GraphQLScalarType("CodepointValue", description, new Coercing() {
            @Override
            public CodepointValue serialize(Object input) {
                if (input instanceof Integer) {
                    return CodepointValue.of((Integer) input);
                } else if (input instanceof String) {
                    return CodepointValue.of((Integer) input);
                } else {
                    return null;
                }
            }

            @Override
            public Object parseValue(Object input) {
                return serialize(input);
            }

            @Override
            public Object parseLiteral(Object input) {
                if (input instanceof IntValue) {
                    return CodepointValue.of(((IntValue)input).getValue().intValue());
                }
                else if(input instanceof StringValue) {
                    return CodepointValue.of(((StringValue)input).getValue());
                }
                else {
                    return null;
                }
            }
        });
    }
}