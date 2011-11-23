package org.sjlee.alg;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class BFS {
	public static <T> Node<T> bfs(Node<T> root, T value) {
		Queue<Node<T>> queue = new LinkedList<Node<T>>();
		queue.add(root);
		Map<Node<T>,Boolean> visited = new HashMap<Node<T>,Boolean>();
		visited.put(root, true);
		if (root.getValue().equals(value)) {
			return root;
		}
		while (!queue.isEmpty()) {
			Node<T> node = queue.remove();
			Node<T> neighbor;
			while ((neighbor = getUnvisitedNeighbor(node, visited)) != null) {
				queue.add(neighbor);
				visited.put(neighbor, true);
				if (neighbor.getValue().equals(value)) {
					return neighbor;
				}
			}
		}
		return null;
	}
	
	private static <T> Node<T> getUnvisitedNeighbor(Node<T> node, Map<Node<T>,Boolean> visited) {
		List<Node<T>> neighbors = node.getNeighbors();
		if (neighbors == null) {
			return null; // no neighbors?
		}
		for (Node<T> neighbor: neighbors) {
			if (!visited.containsKey(neighbor)) {
				return neighbor; // return the first unvisited
			}
		}
		return null; // all neighbors are visited
	}
	
	public static void main(String[] args) {
		Node<Integer> n1 = new Node<Integer>(1);
		Node<Integer> n2 = new Node<Integer>(2);
		Node<Integer> n3 = new Node<Integer>(3);
		Node<Integer> n4 = new Node<Integer>(4);
		Node<Integer> n5 = new Node<Integer>(5);
		
		n1.addNeighbor(n4);
		n2.addNeighbor(n4).addNeighbor(n5);
		n3.addNeighbor(n5);
		n4.addNeighbor(n1).addNeighbor(n2).addNeighbor(n5);
		n5.addNeighbor(n2).addNeighbor(n3).addNeighbor(n4);
		
		Node<Integer> n = bfs(n1, 6);
		System.out.println(n);
	}
}
