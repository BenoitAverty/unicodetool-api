package org.unicodetool.graphql.schema;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Common behaviour between all codepoint types. Corresponds to the Codepoint Interface in the schema.
 */
@AllArgsConstructor
@Getter
public abstract class Codepoint {
    private CodepointValue value;
    private String name;
    private Properties properties;

    public Integer getDecimalValue() {
        return value.inDecimalFormat();
    }

    public abstract Codepoint withValue(CodepointValue value);
}
