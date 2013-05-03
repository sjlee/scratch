package org.sjlee.data;

public class BinarySearchTree<T extends Comparable<? super T>> {

	private Node<T> root;

	public BinarySearchTree() {
		root = null;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof BinarySearchTree)) {
			return false;
		}
		BinarySearchTree<T> that = (BinarySearchTree<T>)obj;
		return nodeEqualsWithNull(root, that.root);
	}
	
	
	private static boolean nodeEqualsWithNull(Node<?> n1, Node<?> n2) {
		return (n1 == null && n2 == null) || n1.equals(n2);
	}
	
	public static <T extends Comparable<? super T>> BinarySearchTree<T> createTestTree() {
		return new BinarySearchTree<T>();
	}
	
	public boolean contains(T value) {
		if (root == null) {
			return false;
		}
		return root.contains(value);
	}
	
	public boolean insert(T value) {
		if (root == null) {
			root = new Node<T>(value);
			return true;
		}
		return root.insert(value);
	}
	
	public boolean remove(T value) {
		if (root == null) {
			return false;
		}
		if (root.element.compareTo(value) == 0) {
			// use a dummy node to handle swap
			Node<T> fauxNode = new Node<T>(value);
			fauxNode.left = root;
			boolean result = root.remove(value, fauxNode);
			root = fauxNode.left;
			return result;
		} else {
			return root.remove(value, null);
		}
	}
	
	public T findOrderedValue(int n) {
		TraversalResult<T> result = new TraversalResult<T>();
		if (root == null) {
			return null;
		}
		root.traverse(n, result);
		if (result.count == n && result.value != null) {
			return result.value;
		}
		return null;
	}
	
	private static class TraversalResult<T> {
		int count;
		T value;
	}
	
	public boolean isBinarySearchTree() {
		if (root == null) { // if it is empty, it's "trivially" a BST
			return true;
		}
		return root.isBinarySearchTree();
	}
	
	public void printInOrder() {
		if (root != null) {
			root.printInOrder();
		}
	}

	private static class Node<T extends Comparable<? super T>> {
		T element;
		Node<T> left;
		Node<T> right;

		Node(T theElement) {
			element = theElement;
			left = right = null;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (!(obj instanceof Node)) {
				return false;
			}
			Node<T> that = (Node<T>)obj;
			return elementEqualsWithNull(that.element) && 
					nodeEqualsWithNull(left, that.left) && nodeEqualsWithNull(right, that.right);
		}
		
		private boolean elementEqualsWithNull(T that) {
			if (element == null) {
				return that == null;
			}
			return element.equals(that);
		}

		boolean contains(T value) {
			if (element.compareTo(value) == 0) {
				return true; // match
			}
			if (element.compareTo(value) > 0) { // left
				// search the left subtree
				return (left == null) ? false : left.contains(value);
			} else { // right
				// search the right subtree
				return (right == null) ? false : right.contains(value);
			}
		}
		
		boolean insert(T value) {
			if (element.compareTo(value) == 0) {
				return false; // already exists
			}
			if (element.compareTo(value) > 0) { // left
				if (left == null) {
					left = new Node<T>(value);
					return true;
				} else {
					return left.insert(value);
				}
			} else { // right
				if (right == null) {
					right = new Node<T>(value);
					return true;
				} else {
					return right.insert(value);
				}
			}
		}
		
		boolean remove(T value, Node<T> parent) {
			if (element.compareTo(value) > 0) { // left
				return (left == null) ? false : left.remove(value, this);
			} else if (element.compareTo(value) < 0) { // right
				return (right == null) ? false : right.remove(value, this);
			} else { // found the node
				if (left != null && right != null) { // both children exist
					element = findInOrderSuccessor().element;
					// remove the redundant node
					right.remove(value, this);
				} else if (this == parent.left) { // I am left child
					parent.left = (left == null) ? right : left;
				} else if (this == parent.right) { // I am right child
					parent.right = (left == null) ? right : left;
				}
				return true;
			}
		}
		
		private Node<T> findInOrderSuccessor() {
			if (right == null) {
				return null; // shouldn't be called if right is null
			}
			return right.findMinNode();
		}
		
		private Node<T> findMinNode() {
			return (left == null) ? this : left.findMinNode();
		}
		
		private Node<T> findMaxNode() {
			return (right == null) ? this : right.findMaxNode();
		}
		
		// DFS traversal
		void traverse(int n, TraversalResult<T> result) {
			if (result.count == n) { // it's already solved; break
				return;
			}
			if (left != null) {
				left.traverse(n, result);
			}
			if (result.count == n) { // it's solved by one of the recursive calls; break
				return;
			}
			result.count++; // increment for this result
			if (result.count == n) { // I am it
				result.value = element;
				return;
			}
			if (right != null) {
				right.traverse(n, result);
			}
		}
		
		void printInOrder() {
			if (left != null) {
				left.printInOrder();
			}
			System.out.println(element);
			if (right != null) {
				right.printInOrder();
			}
		}
		
		boolean isBinarySearchTree() {
			if (left != null && 
					(element.compareTo(left.element) <= 0 || !left.isBinarySearchTree())) {
				return false;
			}
			if (right != null &&
					(element.compareTo(right.element) >= 0 || !right.isBinarySearchTree())) {
				return false;
			}
			return true;
		}
	}

	private static class Test implements Comparable<Test> {
		private String value;

		public Test(String value) {
			this.value = value;
		}

		public String toString() {
			return value;
		}

		@Override
		public int compareTo(Test o) {
			return this.value.compareTo(o.toString());
		}
	}

	private static class Test1 extends Test {
		public Test1(String value) {
			super(value);
		}
	}

	public static void main(String[] args) {
		BinarySearchTree<Test> tree = BinarySearchTree.createTestTree();
		int size = 20;

		for (int i = 1; i <= size; i++) {
			tree.insert(new Test1(String.valueOf(i)));
		}

		if (!tree.isBinarySearchTree()) {
			System.err.println("it's not a binary search tree?");
		}
		tree.insert(new Test1("100"));
		if (!tree.isBinarySearchTree()) {
			System.err.println("it's not a binary search tree?");
		}
		tree.remove(new Test1("10"));
		if (!tree.isBinarySearchTree()) {
			System.err.println("it's not a binary search tree?");
		}
		tree.remove(new Test1(String.valueOf(15)));
		if (!tree.isBinarySearchTree()) {
			System.err.println("it's not a binary search tree?");
		}
		tree.remove(new Test1(String.valueOf(20)));
		if (!tree.isBinarySearchTree()) {
			System.err.println("it's not a binary search tree?");
		}
		tree.printInOrder();
		System.out.println("Contains (10) : " + tree.contains(new Test1("10")));
		System.out.println("Contains (11) : "
				+ tree.contains(new Test1(String.valueOf(11))));
		// see if we can print them in order
		for (int i = 1; i <= size-2; i++) {
			Test value = tree.findOrderedValue(i);
			System.out.println(value.value);
		}
	}

}
