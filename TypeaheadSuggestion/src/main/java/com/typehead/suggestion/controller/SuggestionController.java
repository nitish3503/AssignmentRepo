package com.typehead.suggestion.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.typehead.suggestion.response.BaseResponse;
import com.typehead.suggestion.service.SuggestionService;
import com.typehead.suggestion.util.GuardUtil;

/**
 * This class is the rest end point for the services
 * @author Nitish Kumar @ Accolite Digital India Pvt Ltd.
 *
 */
@RestController
@RequestMapping(value = "suggestion/")
public class SuggestionController {
	private Logger logger = LoggerFactory.getLogger(SuggestionController.class);

	@Autowired
	private SuggestionService suggestionService;

	/**
	 * This API will be used to get suggestion of given input
	 * @param input
	 * @return ResponseEntity
	 */
	@GetMapping("v1/{input}")
	public ResponseEntity<BaseResponse> getSuggestion(@PathVariable String input) {
		logger.info("Get Suggestion Initiated for input :: {}", input);
		try {

			// check if input is having some value
			if (GuardUtil.isNullOrEmpty(input)) {
				logger.info("Get Suggestion Initiated for input :: {}", input);
				return new ResponseEntity<BaseResponse>(
						new BaseResponse(400, true, "Input value should not be empty.", null), HttpStatus.BAD_REQUEST);
			}

			// add current word in trie
			suggestionService.addWord(input);

			// search word and return it to user
			return new ResponseEntity<BaseResponse>(
					new BaseResponse(200, true, "Success", suggestionService.findSuggestion(input)), HttpStatus.OK);

		} catch (final Exception e) {
			logger.info("Exception while find Suggestion ", e);
			return new ResponseEntity<BaseResponse>(new BaseResponse(400, false, "Failed", null),
					HttpStatus.BAD_REQUEST);
		}
	}

}
