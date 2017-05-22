package org.unicodetool.graphql.schema;

/**
 * Represent a surrogate codepoint used in surrogate pairs for UTF-16
 */
public class Surrogate extends Codepoint {
    public Surrogate(CodepointValue value, String name, Properties properties) {
        super(value, name, properties);
    }

    @Override
    public Codepoint withValue(CodepointValue value) {
        return new Surrogate(value, this.getName(), this.getProperties());
    }
}
