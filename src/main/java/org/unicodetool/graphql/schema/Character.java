package org.unicodetool.graphql.schema;

/**
 * Codepoint that encodes an assigned character
 */
public class Character extends Codepoint {
    public Character(CodepointValue value, String name, Properties props) {
        super(value, name, props);
    }

    public String character() {
        return new String(new int[] { this.getDecimalValue() }, 0, 1);
    }

    @Override
    public Codepoint withValue(CodepointValue value) {
        return new Character(value, this.getName(), this.getProperties());
    }
}
