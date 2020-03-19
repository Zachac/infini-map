package org.ex.infinite.config;

import org.ex.infinite.map.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import graphql.schema.DataFetcher;

@Component
public class GraphQLDataFetchers {

	@Autowired private Map map;
	
    public DataFetcher<String> display() {
        return dataFetchingEnvironment -> {
            int x = dataFetchingEnvironment.getArgument("x");
            int y = dataFetchingEnvironment.getArgument("y");
            int radius = dataFetchingEnvironment.getArgument("radius");
            return map.getArea(x, y, radius);
        };
    }
}