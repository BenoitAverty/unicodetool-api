package org.unicodetool.graphql.schema;

import com.coxautodev.graphql.tools.GraphQLRootResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.unicodetool.application.CharactersQueryHandler;
import org.unicodetool.application.CodepointQueryHandler;
import org.unicodetool.application.CodepointSearchQueryHandler;
import org.unicodetool.application.exceptions.CodepointNotFound;

import java.util.Arrays;
import java.util.List;

@Component
public class Query implements GraphQLRootResolver {

    private final CodepointQueryHandler codepointQueryHandler;
    private final CodepointSearchQueryHandler codepointSearchQueryHandler;
    private final CharactersQueryHandler charactersQueryHandler;

    @Autowired
    public Query(CodepointQueryHandler cqh, CodepointSearchQueryHandler csqh, CharactersQueryHandler charsqh) {
        this.codepointQueryHandler = cqh;
        this.codepointSearchQueryHandler = csqh;
        this.charactersQueryHandler = charsqh;
    }

    public Codepoint codepoint(CodepointValue codepointValue) {
        return codepointQueryHandler
                .findCodepoint(codepointValue)
                .orElseThrow(CodepointNotFound::new);
    }

    public List<Codepoint> codepointSearch(String name) {
        return codepointSearchQueryHandler.findByName(name);
    }

    public List<Codepoint> characters(String input) {
        return charactersQueryHandler.findCodepointForCharacters(input);
    }
}
