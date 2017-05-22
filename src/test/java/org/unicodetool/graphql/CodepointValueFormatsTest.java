package org.unicodetool.graphql;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.unicodetool.graphql.schema.CodepointValue;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test the different ways to diaply the value of a codepoint
 */
public class CodepointValueFormatsTest {

    @Test
    @DisplayName("Format Codepoint Value as Decimal Integer")
    public void testAsDecimal() {
        final Integer expected = 41;
        final CodepointValue input = CodepointValue.of("0029");

        assertEquals(input.inDecimalFormat(), expected);
    }

    @Test
    @DisplayName("Format Codepoint Value as Decimal Integer")
    public void testAsDecimalPrefixed() {
        final Integer expected = 41;
        final CodepointValue input = CodepointValue.of("U+0029");

        assertEquals(input.inDecimalFormat(), expected);
    }

    @Test
    @DisplayName("Format Codepoint Value as unicode standard")
    public void testAsUnicode() {
        final String expected = "U+0029";
        final CodepointValue input = CodepointValue.of(41);

        assertEquals(input.inUnicodeFormat(), expected);
    }

    @Test
    @DisplayName("Format Codepoint Value as unicode standard with prefixed input")
    public void testAsUnicodePrefixed() {
        final String expected = "U+0029";
        final CodepointValue input = CodepointValue.of("U+0029");

        assertEquals(input.inUnicodeFormat(), expected);
    }
}
