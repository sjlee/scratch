package org.sjlee.data;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class Trie {
	private final Node root = new Node();
	
	public void add(String word) {
		int size = word.length();
		Node parent = root;
		for (int i = 0; i < size; i++) {
			char c = word.charAt(i);
			Node child = parent.findChild(c);
			if (child == null) { // does not exist
				child = new Node(c);
				parent.addChild(child);
			}
			// if we're at the end, add the EOW marker
			if (i == size-1) {
				child.addChild(Node.END_OF_WORD);
			}
			parent = child; // iterate
		}
	}
	
	public boolean find(String word) {
		int size = word.length();
		Node parent = root;
		for (int i = 0; i < size; i++) {
			char c = word.charAt(i);
			Node child = parent.findChild(c);
			if (child == null) { // does not exist
				return false;
			}
			// if we're at the end, add the EOW marker
			if (i == size-1) {
				Node eow = child.findEndOfWordNode();
				if (eow == null) {
					return false;
				}
			}
			parent = child; // iterate
		}
		// all letters match
		return true;
	}
	
	private static class Node {
		private char c;
		private Set<Node> children;
		private boolean endOfWord;
		
		public static final Node END_OF_WORD = getEndOfWordNode();
		
		public Node() {}
		
		public Node(char c) {
			this.c = c;
		}
		
		public void addChild(Node child) {
			if (children == null) {
				children = new HashSet<Node>();
			}
			children.add(child);
		}
		
		public Node findChild(char c) {
			if (children == null) {
				return null;
			}
			for (Node n: children) {
				if (n.c == c) {
					return n;
				}
			}
			return null;
		}
		
		public Node findEndOfWordNode() {
			if (children == null) {
				return null;
			}
			for (Node n: children) {
				if (n.endOfWord) {
					return n;
				}
			}
			return null;
		}
		
		private static Node getEndOfWordNode() {
			Node n = new Node();
			n.endOfWord = true;
			return n;
		}
		
		@Override
		public boolean equals(Object o) {
			if (this == o) {
				return true;
			}
			if (!(o instanceof Node)) {
				return false;
			}
			Node that = (Node)o;
			return c == that.c && endOfWord == that.endOfWord;
		}
		
		@Override
		public int hashCode() {
			return endOfWord ? Integer.MAX_VALUE : (int)c;
		}
		
		@Override
		public String toString() {
			if (endOfWord) {
				return "END_OF_WORD";
			} else {
				return String.valueOf(c);
			}
		}
	}
	
	public static void main(String[] args) {
		Trie trie = new Trie();
		trie.add("4084061578");
		trie.add("4082396092");
		trie.add("4083765464");
		trie.add("4083761234");
		
		boolean check = trie.find("4083765464");
		if (!check) {
			System.err.println("number is missing!");
		}
	}
}
