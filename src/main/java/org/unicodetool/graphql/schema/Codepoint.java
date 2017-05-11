package org.unicodetool.graphql.schema;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Codepoint {
    private CodepointValue value;
    private String name;

    private Properties properties;
}
