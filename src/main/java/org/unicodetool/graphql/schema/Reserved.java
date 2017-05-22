package org.unicodetool.graphql.schema;

/**
 * Codepoint that is reserved for future use.
 */
public class Reserved extends Codepoint {
    public Reserved(CodepointValue value, String name, Properties properties) {
        super(value, name, properties);
    }

    @Override
    public Codepoint withValue(CodepointValue value) {
        return new Reserved(value, this.getName(), this.getProperties());
    }
}
