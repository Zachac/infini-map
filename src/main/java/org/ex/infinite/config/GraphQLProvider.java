package org.ex.infinite.config;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import graphql.schema.idl.TypeRuntimeWiring;

@Component
public class GraphQLProvider {
	
	private GraphQL graphQL;

	@Autowired
    GraphQLDataFetchers graphQLDataFetchers;

	@Bean
	public GraphQL graphQL() {
		return graphQL;
	}

	@PostConstruct
	public void init() throws IOException {
		var schema = this.getClass().getClassLoader().getResourceAsStream("schema.graphqls");
		TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(schema);
        RuntimeWiring runtimeWiring = buildWiring();
        SchemaGenerator schemaGenerator = new SchemaGenerator();
        GraphQLSchema graphQLSchema =  schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);
		this.graphQL = GraphQL.newGraphQL(graphQLSchema).build();
	}

    private RuntimeWiring buildWiring() {
        return RuntimeWiring.newRuntimeWiring().type(TypeRuntimeWiring.newTypeWiring("Query")
                        .dataFetcher("display", graphQLDataFetchers.display()))
                .build();
    }
}
