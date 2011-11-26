package org.sjlee.data;

import java.util.Deque;
import java.util.EmptyStackException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class DirectedGraph {
	private Vertex[] vertices;
	private int[][] edges;
	
	public DirectedGraph(String[] keys, int[][] edges) {
		int size = keys.length;
		if (edges.length != size) {
			throw new IllegalArgumentException("edges are not the right dimension!");
		}
		for (int i = 0; i < edges.length; i++) {
			if (edges[i].length != size) {
				throw new IllegalArgumentException("edges are not the right dimension!");
			}
		}
		
		vertices = new Vertex[keys.length];
		for (int i = 0; i < vertices.length; i++) {
			vertices[i] = new Vertex(keys[i], i);
		}
		this.edges = edges; // not very safe
	}
	
	public void traverseBF() {
		Queue<Vertex> queue = new LinkedList<Vertex>();
		Vertex current = vertices[0];
		int index;
		do {
			index = current.getIndex();
			System.out.println("vertex " + current + " visited.");
			current.setVisited();
			// enqueue the neighbors
			for (int i = 0; i < vertices.length; i++) {
				if (edges[index][i] != 0) {
					Vertex neighbor = vertices[i];
					if (!neighbor.isVisited()) {
						queue.add(neighbor);
					}
				}
			}
		} while ((current = queue.poll()) != null); // dequeue and loop
		System.out.println("breadth-first traversal complete.");
	}
	
	public void traverseDF() {
		Stack<Vertex> stack = new Stack<Vertex>();
		Vertex current = vertices[0];
		int index;
		do {
			index = current.getIndex();
			System.out.println("vertex " + current + " visited.");
			current.setVisited();
			// push the neighbors
			for (int i = 0; i < vertices.length; i++) {
				if (edges[index][i] != 0) {
					Vertex neighbor = vertices[i];
					if (!neighbor.isVisited()) {
						stack.push(neighbor);
					}
				}
			}
			// iterate
			try {
				current = stack.pop(); // pop a vertex; if it is empty we'll handle it as an exception
			} catch (EmptyStackException e) {
				break;
			}
		} while (true);
		System.out.println("depth-first traversal complete.");
	}
	
	public DoublyLinkedList<Vertex> findPathDF(int startIndex, int endIndex) {
		Stack<Vertex> stack = new Stack<Vertex>(); // stack for DFS
		DoublyLinkedList<Vertex> path = new DoublyLinkedList<Vertex>(); // DLL for the path
		Vertex current = vertices[startIndex];
		int index;
		do {
			index = current.getIndex();
			addToPath(current, path);
			current.setVisited();
			// see if we reached the end
			if (current.getIndex() == endIndex) {
				// we're done
				break;
			}
			// push the neighbors
			for (int i = 0; i < vertices.length; i++) {
				if (edges[index][i] != 0) {
					Vertex neighbor = vertices[i];
					if (!neighbor.isVisited()) {
						stack.push(neighbor);
					}
				}
			}
			// iterate
			try {
				current = stack.pop(); // completed the traversal
			} catch (EmptyStackException e) {
				current = null;
				break;
			}
		} while (true);
		// final operations
		if (current == null) {
			return null; // no path found
		}
		editPath(path, startIndex);
		return path;
	}
	
	private void addToPath(Vertex v, DoublyLinkedList<Vertex> path) {
		path.add(v);
	}
	
	private void editPath(DoublyLinkedList<Vertex> path, int startIndex) {
		DoublyLinkedNode<Vertex> current = path.getTail();
		DoublyLinkedNode<Vertex> previous = current.prev;
		while (previous != null && previous.value.index != startIndex) {
			while (previous != null && edges[previous.value.index][current.value.index] != 0) {
				current = previous;
				previous = current.prev;
			}
			// there is no edge between the current and the previous: cycle
			if (previous != null && previous.value.index != startIndex) {
				while (previous != null && edges[previous.value.index][current.value.index] == 0) {
					previous = previous.prev;
				}
				// fix
				if (previous != null) {
					previous.next = current;
					current.prev = previous;
				}
			}
		}
	}
	
	private static class Score {
		public static final int NO_PREDECESSOR = -1;
		int score = Integer.MAX_VALUE;
		int predecessor = NO_PREDECESSOR; // initial invalid value
		boolean completed;
		
		static Score[] getScoreArray(int size, int start) {
			Score[] scores = new Score[size];
			for (int i = 0; i < size; i++) {
				scores[i] = new Score();
				if (i == start) {
					scores[i].score = 0;
				}
			}
			return scores;
		}
		
		@Override
		public String toString() {
			return score == Integer.MAX_VALUE ? "unset" : score + ": (from " + predecessor + ")";
		}
	}

	// Dijkstra's method
	public Queue<Vertex> findShortestPath(int startIndex, int endIndex) {
		Queue<Integer> unvisited = new LinkedList<Integer>();
		// initialize the scores
		Score[] scores = Score.getScoreArray(vertices.length, startIndex);
		
		Integer i = startIndex;
		// examine the immediate neighbors of the vertex
		do {
			for (int j = 0; j < vertices.length; j++) {
				int weight = edges[i][j];
				if (weight != 0) {
					int score = weight + scores[i].score; // compute the core for the neighbor
					Score neighbor = scores[j];
					// reset the score if it is lower
					if (score < neighbor.score) {
						neighbor.score = score;
						neighbor.predecessor = i;
					}
					if (!neighbor.completed && !unvisited.contains(j)) {
						// add to the set only if it is not completed
						unvisited.add(j);
					}
				}
			}
			// mark the parent as completed
			scores[i].completed = true;
		} while ((i = unvisited.poll()) != null);
		// we should have all scores filled in: find the shortest path for end
		Deque<Vertex> path = new LinkedList<Vertex>();
		for (int k = endIndex; k != Score.NO_PREDECESSOR; k = scores[k].predecessor) {
			path.addFirst(vertices[k]);
		}
		return path;
	}
	
	public void resetVisited() {
		for (Vertex v: vertices) {
			v.setVisited(false);
		}
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < vertices.length; i++) {
			sb.append(vertices[i].key).append(": ");
			for (int j = 0; j < vertices.length; j++) {
				sb.append(edges[i][j]);
				if (j != vertices.length-1) {
					sb.append(", ");
				}
			}
			sb.append('\n');
		}
		return sb.toString();
	}
	
	private static class Vertex {
		private String key;
		private int index;
		private boolean visited;
		
		Vertex(String key, int index) {
			this.key = key;
			this.index = index;
		}
		
		void setVisited() {
			setVisited(true);
		}
		
		void setVisited(boolean visited) {
			this.visited = visited;
		}
		
		boolean isVisited() {
			return visited;
		}
		
		int getIndex() {
			return index;
		}
		
		@Override
		public String toString() {
			return key;
		}
	}
	
	public static void main(String[] args) {
		String[] keys = 
				{"sleepytown", "much snoring", "great snoring", "little snoring", "snoring on sea", "snoring on the hill", "lesser snoring"};
		int[][] edges = {
				{0, 	50, 	0, 		0, 		0, 		0, 		0},
				{50, 	0, 		150, 	0, 		0, 		0, 		0},
				{0, 	150, 	0, 		180, 	200, 	150, 	0},
				{0, 	80, 	0, 		0, 		0, 		0, 		0},
				{0, 	0, 		200, 	0, 		0, 		0, 		0},
				{0, 	0, 		0, 		0, 		0, 		0, 		50},
				{0, 	0, 		180, 	0, 		0, 		0, 		0}
		};
		
		DirectedGraph graph = new DirectedGraph(keys, edges);
		System.out.println(graph);
		graph.traverseBF();
		graph.resetVisited();
		graph.traverseDF();
		graph.resetVisited();
		DoublyLinkedList<Vertex> path = graph.findPathDF(1, 3);
		System.out.println(path);
		
		// test Dijkstra's algorithm
		String[] keys2 = {"A", "B", "C", "D", "E", "F", "G"};
		int[][] edges2 = {
				{0, 10, 9, 25, 0, 0, 0},
				{0, 0, 0, 0, 14, 0, 0},
				{0, 0, 0, 0, 12, 0, 0},
				{0, 0, 0, 0, 0, 0, 14},
				{0, 0, 0, 0, 0, 8, 0},
				{0, 0, 0, 0, 0, 0, 6},
				{0, 0, 0, 0, 0, 0, 0}
		};
		
		DirectedGraph graph2 = new DirectedGraph(keys2, edges2);
		Queue<Vertex> path2 = graph2.findShortestPath(0, 6);
		System.out.println(path2);
	}
}
