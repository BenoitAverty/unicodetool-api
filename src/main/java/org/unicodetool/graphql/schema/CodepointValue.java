package org.unicodetool.graphql.schema;

import graphql.language.IntValue;
import graphql.language.StringValue;
import graphql.schema.Coercing;
import graphql.schema.GraphQLScalarType;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode
public class CodepointValue {

    @Getter
    private String value;

    public static CodepointValue of(String value) {
        return new CodepointValue(value);
    }

    public static CodepointValue of(Integer value) {
        return new CodepointValue(String.format("%04X", value & 0xFFFFFF));
    }

    /**
     * @return the codepoint value in the stanbdard unicode format. "U+" prefix followed
     * by a 4 to 6 digits hexadecimal number (e.g. U+0041 or U+1FFCD)
     */
    public String inUnicodeFormat() {
        return value.startsWith("U+") ? value : "U+"+value;
    }

    /**
     * @return the codepoint value as a decimal integer.
     */
    public Integer inDecimalFormat() {
        return Integer.parseInt(value.startsWith("U+") ? value.substring(2) : value, 16);
    }

    /**
     * Returns the GraphQL scalar type corresponding to this class. The scalar type can be build from a string or
     * integer, and can be parsed from a IntValue or StringValue AST node.
     * @see GraphQLScalarType
     */
    public static GraphQLScalarType scalar() {
        final String description = "Numeric value of a codepoint. can be used to express a codepoint in various forms:\n"+
                " - Decimal integer (e.g. 65)\n"+
                " - Unicode representation (e.g. \"U+0041\")\n"+
                " - Unicode representation without suffix (e.g. \"0041\")\n" +
                " - Hexadecimal integer (e.g. \"0x41\")";

        return new GraphQLScalarType("CodepointValue", description, new Coercing() {
            @Override
            public String serialize(Object input) {
                if(input instanceof CodepointValue) {
                    return ((CodepointValue) input).inUnicodeFormat();
                }
                return null;
            }

            @Override
            public Object parseValue(Object input) {
                if (input instanceof Integer) {
                    return CodepointValue.of((Integer) input);
                } else if (input instanceof String) {
                    return CodepointValue.of((String) input);
                } else {
                    return null;
                }
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