package org.unicodetool.graphql;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import graphql.ExecutionResult;
import graphql.GraphQL;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
public class GraphQLController {

    private final GraphQL graphQL;
    private final ObjectMapper mapper;

    @Autowired
    public GraphQLController(GraphQL graphQL, ObjectMapper mapper) {
        this.graphQL = graphQL;
        this.mapper = mapper;
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<ExecutionResult> graphql(@RequestBody GraphQLRequest input) {
        try {
            if(log.isDebugEnabled()) {
                log.debug("Received GraphQL Query : " + mapper.writeValueAsString(input));
            }
        }
        catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        ExecutionResult result = graphQL.execute(input.query);

        try {
            if(log.isDebugEnabled()) {
                log.debug("Query result : " + mapper.writeValueAsString(result));
            }
        }
        catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.ok(result);
    }
}
