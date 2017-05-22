package org.unicodetool.graphql.schema;

/**
 * Codepoint that encodes a noncharacter.
 */
public class NonCharacter extends Codepoint {
    public NonCharacter(CodepointValue value, String name, Properties properties) {
        super(value, name, properties);
    }

    @Override
    public Codepoint withValue(CodepointValue value) {
        return new NonCharacter(value, this.getName(), this.getProperties());
    }
}
