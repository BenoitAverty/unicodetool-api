package org.unicodetool.graphql.schema;

import com.coxautodev.graphql.tools.GraphQLRootResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.unicodetool.application.CodepointQueryHandler;
import org.unicodetool.application.exceptions.CodepointNotFound;

import java.util.Arrays;
import java.util.List;

@Component
public class Query implements GraphQLRootResolver {

    private final CodepointQueryHandler codepointQueryHandler;

    @Autowired
    public Query(CodepointQueryHandler cqh) {
        this.codepointQueryHandler = cqh;
    }

    public Codepoint codepoint(CodepointValue codepointValue) {
        return codepointQueryHandler
                .findCodepoint(codepointValue)
                .orElseThrow(CodepointNotFound::new);
    }
}
