package com.typehead.suggestion.model;

import java.util.HashMap;
import java.util.Map;

/**
 * This class will be used for Trie Data Structure
 * @author Nitish Kumar @ Accolite Digital India Pvt Ltd.
 *
 */
public class TrieDataNode {

	private char letter;
	private Map<Character, TrieDataNode> children;
	private boolean isEndOfWord;

	public TrieDataNode() {
		super();
	}

	/**
	 * @param letter
	 */
	public TrieDataNode(char letter) {
		super();
		this.letter = letter;
		this.children = new HashMap<>();
	}

	/**
	 * @return the letter
	 */
	public char getLetter() {
		return letter;
	}

	/**
	 * @param letter the letter to set
	 */
	public void setLetter(char letter) {
		this.letter = letter;
	}

	/**
	 * @return the children
	 */
	public Map<Character, TrieDataNode> getChildren() {
		return children;
	}

	/**
	 * @param children the children to set
	 */
	public void setChildren(Map<Character, TrieDataNode> children) {
		this.children = children;
	}

	/**
	 * @return the isEndOfWord
	 */
	public boolean isEndOfWord() {
		return isEndOfWord;
	}

	/**
	 * @param isEndOfWord the isEndOfWord to set
	 */
	public void setEndOfWord(boolean isEndOfWord) {
		this.isEndOfWord = isEndOfWord;
	}

}
