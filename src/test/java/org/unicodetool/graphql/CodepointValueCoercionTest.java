package org.unicodetool.graphql;

import graphql.language.IntValue;
import graphql.language.ObjectValue;
import graphql.language.StringValue;
import graphql.schema.GraphQLScalarType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.unicodetool.graphql.schema.CodepointValue;

import java.math.BigInteger;

/**
 * Test the coercion of the custom graphQL scalar type {@link CodepointValue}
 */
public class CodepointValueCoercionTest {
    private GraphQLScalarType codepointValueScalarType = CodepointValue.scalar();

    @Nested
    @DisplayName("Parsing from the graphql AST")
    public class ParseLiteralTest {
        @DisplayName("With IntValue")
        @Test
        public void testIntInput() {
            final IntValue input = new IntValue(BigInteger.valueOf(65));
            final String expected = "0041";

            final CodepointValue actual = (CodepointValue) codepointValueScalarType.getCoercing().parseLiteral(input);

            Assertions.assertEquals(expected, actual.getValue());
        }

        @DisplayName("With StringValue")
        @Test
        public void testStringInput() {
            final StringValue input = new StringValue("A string");
            final String expected = "A string";

            final CodepointValue actual = (CodepointValue) codepointValueScalarType.getCoercing().parseLiteral(input);

            Assertions.assertEquals(expected, actual.getValue());
        }

        @DisplayName("With invalid input")
        @Test
        public void testInvalidInput() {
            final ObjectValue input = new ObjectValue();

            final CodepointValue actual = (CodepointValue) codepointValueScalarType.getCoercing().parseLiteral(input);

            Assertions.assertNull(actual);
        }
    }

    @DisplayName("Serializing a CodepointValue")
    @Test
    public void serializeTest() {

        final CodepointValue input = CodepointValue.of("0041");
        final String expected = "U+0041";

        final String actual = (String)codepointValueScalarType.getCoercing().serialize(input);

        Assertions.assertEquals(expected, actual);
    }



    @Nested
    @DisplayName("Converting from variable/data")
    public class ParseValueTest {
        @DisplayName("With Integer input")
        @Test
        public void testIntInput() {
            final Integer input = 65;
            final String expected = "0041";

            final CodepointValue actual = (CodepointValue) codepointValueScalarType.getCoercing().parseValue(input);

            Assertions.assertEquals(expected, actual.getValue());
        }

        @DisplayName("With String input")
        @Test
        public void testStringInput() {
            final String input = "A string";
            final String expected = "A string";

            final CodepointValue actual = (CodepointValue) codepointValueScalarType.getCoercing().parseValue(input);

            Assertions.assertEquals(expected, actual.getValue());
        }

        @DisplayName("With invalid input")
        @Test
        public void testInvalidInput() {
            final Object input = new Object();

            final CodepointValue actual = (CodepointValue) codepointValueScalarType.getCoercing().parseValue(input);

            Assertions.assertNull(actual);
        }
    }
}
