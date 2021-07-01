package com.typehead.suggestion.service;

import java.util.List;

public interface SuggestionService {

	void addWord(String wordToAdd);

	List<String> findSuggestion(String baseChars);

	boolean removeWord(String wordToRemove);

}
