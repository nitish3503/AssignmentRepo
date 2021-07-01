package com.typehead.suggestion;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TypeaheadSuggestionApplication {
	
	private static Logger logger = LoggerFactory.getLogger(TypeaheadSuggestionApplication.class);
	
	public static void main(String[] args) {
		logger.info("TypeaheadSuggestionApplication Initiated ");
		SpringApplication.run(TypeaheadSuggestionApplication.class, args);
	}

}
