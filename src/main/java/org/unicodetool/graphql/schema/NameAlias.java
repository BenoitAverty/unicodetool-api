package org.unicodetool.graphql.schema;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Codepoint name Alias
 */
@Getter
@AllArgsConstructor
public class NameAlias {
    private String alias;
    private String type;
}
