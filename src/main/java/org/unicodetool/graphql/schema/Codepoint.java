package org.unicodetool.graphql.schema;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Codepoint {
    private Integer value;
    private String name;

    private Properties properties;
}
