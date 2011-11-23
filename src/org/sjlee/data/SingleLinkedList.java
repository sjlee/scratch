package org.sjlee.data;

public class SingleLinkedList<T> {
	private Node<T> head = null;
	
	public T getHead() {
		return isEmpty() ? null : head.value;
	}
	
	public T getTail() {
		return isEmpty() ? null : getTailNode().value;
	}
	
	public void add(T t) {
		Node<T> node = new Node<T>(t);
		if (isEmpty()) { // empty
			head = node;
		} else {
			Node<T> tail = getTailNode();
			tail.next = node;
		}
	}
	
	private Node<T> getTailNode() {
		// always compute it from scratch (O(n))
		if (isEmpty()) {
			return null;
		}
		Node<T> node = head;
		while (node.next != null) {
			node = node.next;
		}
		return node;
	}
	
	public boolean isEmpty() {
		return head == null;
	}
	
	public int size() {
		if (isEmpty()) {
			return 0;
		}
		int i = 0;
		for (Node<T> node = head; node != null; node = node.next, i++) {
		}
		return i;
	}
	
	public void reverse() {
		if (isEmpty()) {
			return;
		}
		
		Node<T> left = head;
		Node<T> right = left.next;
		Node<T> temp = null;
		while (right != null) {
			left.next = temp;
			temp = left;
			left = right;
			right = right.next;
		}
		head = left;
		head.next = temp;
	}
	
	@Override
	public String toString() {
		if (isEmpty()) {
			return "empty";
		}
		StringBuilder sb = new StringBuilder("{");
		for (Node<T> node = head; node != null; node = node.next) {
			sb.append(node.value);
			sb.append(", ");
		}
		// TODO remove the redundant ", "
		sb.append("}");
		return sb.toString();
	}
	
	private static class Node<T> {
		private Node<T> next;
		private T value;
		
		Node(T t) {
			value = t;
		}
	}
	
	public static void main(String[] args) {
		SingleLinkedList<Integer> list = new SingleLinkedList<Integer>();
		list.add(0);
		list.add(1);
		list.add(1);
		list.add(2);
		list.add(3);
		list.add(5);
		list.add(8);
		list.add(13);
		
		System.out.println(list);
		System.out.println(list.size());
		list.reverse();
		System.out.println(list);
	}
}
