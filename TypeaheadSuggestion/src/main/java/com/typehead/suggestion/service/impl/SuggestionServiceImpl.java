package com.typehead.suggestion.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.typehead.suggestion.model.TrieDataNode;
import com.typehead.suggestion.service.SuggestionService;

@Service("suggestionService")
public class SuggestionServiceImpl implements SuggestionService {
	
	private Logger logger = LoggerFactory.getLogger(SuggestionServiceImpl.class);
	private TrieDataNode root;

	public SuggestionServiceImpl() {
		root = new TrieDataNode('-');
	}

	/**
	 * This Function will used to add word in the Trie Data structure
	 */
	@Override
	public void addWord(String wordToAdd) {
		logger.debug("Add Word in Trie Initiated");
		TrieDataNode currentNode = root;
		for (char letter : wordToAdd.toCharArray()) {
			if (currentNode.getChildren().containsKey(letter)) {
				currentNode = currentNode.getChildren().get(letter);
			} else {
				TrieDataNode node = new TrieDataNode(letter);
				currentNode.getChildren().put(letter, node);
				currentNode = node;
			}
		}
		currentNode.setEndOfWord(true);
	}

	@Override
	public List<String> findSuggestion(String baseChars) {
		logger.debug("FindSuggestion Initiated for :: {} ", baseChars);
		StringBuilder stringBuilder = new StringBuilder();
		TrieDataNode current = root;
		for (char letter : baseChars.toCharArray()) {
			if (!current.getChildren().containsKey(Character.toUpperCase(letter))
					&& !current.getChildren().containsKey(Character.toLowerCase(letter))) {
				return Collections.emptyList();
			}
			if (current.getChildren().get(Character.toUpperCase(letter)) != null) {
				current = current.getChildren().get(Character.toUpperCase(letter));
				stringBuilder.append(Character.toUpperCase(letter));
			} else {
				current = current.getChildren().get(Character.toLowerCase(letter));
				stringBuilder.append(Character.toLowerCase(letter));
			}
		}
		if (current.getChildren().entrySet().isEmpty()) {
			return Collections.singletonList(baseChars);
		}
		return getAllWords(current, stringBuilder.toString());
	}

	/**
	 * This Function will used to find the all words.
	 * @param current
	 * @param baseChars
	 * @return  List<String>
	 */
	private List<String> getAllWords(TrieDataNode current, String baseChars) {
		List<String> results = new ArrayList<>();
		for (Entry<Character, TrieDataNode> map : current.getChildren().entrySet()) {
			if (map.getValue().isEndOfWord()) {
				results.add(baseChars + map.getValue().getLetter());
			}
			results.addAll(getAllWords(map.getValue(), baseChars + map.getValue().getLetter()));
		}
		Collections.sort(results);
		return results;
	}

	/**
	 * This Function will used to remove word from Trie
	 */
	@Override
	public boolean removeWord(String wordToRemove) {
		return remove(root, wordToRemove, 0);
	}

	/**
	 * This Function will remove the value from Trie data structure
	 * @param current
	 * @param word
	 * @param index
	 * @return
	 */
	private boolean remove(TrieDataNode current, String word, int index) {

		if (index == word.length()) {
			if (!current.isEndOfWord()) {
				return false;
			}
			current.setEndOfWord(false);
			return current.getChildren().isEmpty();
		}
		char ch = word.charAt(index);
		TrieDataNode node = current.getChildren().get(ch);
		if (node == null) {
			return false;
		}

		boolean deleteCurrentNode = remove(node, word, index + 1);

		if (deleteCurrentNode) {
			current.getChildren().remove(ch);
			return current.getChildren().isEmpty();
		}
		return false;
	}

}
