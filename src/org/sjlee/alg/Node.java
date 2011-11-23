package org.sjlee.alg;

import java.util.ArrayList;
import java.util.List;

public class Node<T> {
	private T value;
	private List<Node<T>> neighbors;
	
	public Node(T value) {
		this.value = value;
	}
	
	public T getValue() {
		return value;
	}
	
	public Node<T> addNeighbor(Node<T> n) {
		if (neighbors == null) {
			neighbors = new ArrayList<Node<T>>();
		}
		neighbors.add(n);
		return this;
	}
	
	public List<Node<T>> getNeighbors() {
		return neighbors;
	}
	
	@Override public String toString() {
		return "Node:" + value;
	}
}
