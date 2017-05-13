package org.unicodetool.graphql.schema;

/**
 * Codepoint that encodes an assigned character
 */
public class Character extends Codepoint {
    public Character(CodepointValue value, String name, Properties props) {
        super(value, name, props);
    }

    public String character() {
        return "a";
    }
}
