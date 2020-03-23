package org.ex.infinite.config;

import org.ex.infinite.services.channels.ChannelManager;
import org.ex.infinite.services.channels.ChannelManager.MessageValue;
import org.ex.infinite.services.map.Map;
import org.ex.infinite.utility.ExpiringMessageQueue.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import graphql.GraphQL;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import graphql.schema.idl.TypeRuntimeWiring;

@Configuration
public class GraphQLConfig {
	
	@Autowired private Map map;
	@Autowired private ChannelManager channels;

    public String display(DataFetchingEnvironment env) {
        return map.getArea(env.getArgument("x"),
        		env.getArgument("y"), 
        		env.getArgument("radius"),
        		env.getArgument("zoom"));
    }

    public Iterable<Message<MessageValue>> getMessages(DataFetchingEnvironment env) {
    	return () -> channels.getMessages(
    			env.getArgument("channel"),
    			env.getArgument("effectiveTs"));
    }
    
    public Boolean sendMessage(DataFetchingEnvironment env) {
    	channels.addMessage(
    			env.getArgument("channel"),
    			env.getArgument("userId"),
    			env.getArgument("name"),
    			env.getArgument("message"));
    	return null;
    }
    
    
	@Bean
    public GraphQL graphql() {
		var wiring = RuntimeWiring.newRuntimeWiring().type(TypeRuntimeWiring.newTypeWiring("Query")
	            .dataFetcher("display", this::display)
	            .dataFetcher("getMessages", this::getMessages)
	            .dataFetcher("sendMessage", this::sendMessage)
        ).build();
		
		TypeDefinitionRegistry schema = new SchemaParser().parse(
			this.getClass().getClassLoader().getResourceAsStream("schema.graphqls")
		);
        
        GraphQLSchema graphQLSchema =  new SchemaGenerator().makeExecutableSchema(schema, wiring);
		return GraphQL.newGraphQL(graphQLSchema).build();
    }
}