package org.unicodetool.graphql.schema;

public interface Codepoint {
    CodepointValue getValue();
    Integer getDecimalValue();
    String getName();

    Properties getProperties();
}
