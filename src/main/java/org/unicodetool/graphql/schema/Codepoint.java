package org.unicodetool.graphql.schema;

import lombok.AllArgsConstructor;

/**
 * Common behaviour between all codepoint types. Corresponds to the Codepoint Interface in the schema.
 */
@AllArgsConstructor
public abstract class Codepoint {
    private CodepointValue value;
    private String name;

    private Properties properties;

    public CodepointValue getValue() {
        return value;
    }

    public Integer getDecimalValue() {
        return value.inDecimalFormat();
    }

    public String getName() {
        return name;
    }

    public Properties getProperties() {
        return properties;
    }

}
