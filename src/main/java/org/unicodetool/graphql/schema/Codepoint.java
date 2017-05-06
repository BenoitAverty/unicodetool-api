package org.unicodetool.graphql.schema;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Codepoint {
    private int value;
    private String name;

    private Properties properties;
}
