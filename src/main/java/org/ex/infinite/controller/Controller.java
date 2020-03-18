package org.ex.infinite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import graphql.ExecutionResult;
import graphql.GraphQL;

@RestController
@CrossOrigin
public class Controller {

	@Autowired GraphQL graphql;
	
	@GetMapping("/")
	public String healthCheck() {
		return "It works! 200 - OK";
	}
	
	@PostMapping("/graphql")
	public ExecutionResult graphql(@RequestBody String body) {
		return graphql.execute(body);
	}
	
	
}
