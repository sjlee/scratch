package org.sjlee.data;

import java.util.LinkedList;
import java.util.Queue;

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
	}
}
