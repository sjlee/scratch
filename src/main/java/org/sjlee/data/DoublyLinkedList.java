package org.sjlee.data;

public class DoublyLinkedList<T> {
	private DoublyLinkedNode<T> head;
	private DoublyLinkedNode<T> tail;
	
	public void add(T value) {
		DoublyLinkedNode<T> node = new DoublyLinkedNode<T>(value);
		if (head == null) {
			head = node;
		} else {
			tail.next = node;
			node.prev = tail;
		}
		tail = node;
	}
	
	public DoublyLinkedNode<T> getHead() {
		return head;
	}
	
	public DoublyLinkedNode<T> getTail() {
		return tail;
	}
	
	@Override
	public String toString() {
		if (head != null) {
			StringBuilder sb = new StringBuilder();
			sb.append('[');
			DoublyLinkedNode<T> node = head;
			sb.append(node.value);
			while ((node = node.next) != null) {
				sb.append(", ");
				sb.append(node.value);
			}
			sb.append(']');
			return sb.toString();
		} else {
			return "[]";
		}
	}
}

class DoublyLinkedNode<T> {
	T value;
	DoublyLinkedNode<T> prev;
	DoublyLinkedNode<T> next;
	
	DoublyLinkedNode(T value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return value == null ? "null" : value.toString();
	}
}
