package org.unicodetool.graphql.schema;

import lombok.AllArgsConstructor;

/**
 * Common behaviour between all codepoint types
 */
 //TODO: see if there's a way to put all of this in the interface directly...
@AllArgsConstructor
public class AbstractCodepoint implements Codepoint {
    private CodepointValue value;
    private String name;

    private Properties properties;

    @Override
    public CodepointValue getValue() {
        return value;
    }

    public Integer getDecimalValue() {
        return value.inDecimalFormat();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Properties getProperties() {
        return properties;
    }

}
