package org.sjlee.data;


public class BinarySearchTree<T extends Comparable<? super T>> {

	private Node<T> root;

	public BinarySearchTree() {
		root = null;
	}

	public static <T extends Comparable<? super T>> BinarySearchTree<T> createTestTree() {
		return new BinarySearchTree<T>();
	}
	
	public boolean contains(T value) {
		if (root == null) {
			return false;
		}
		return contains(root, value);
	}
	
	private boolean contains(Node<T> node, T value) {
		T element = node.element;
		if (element.compareTo(value) == 0) {
			return true; // match
		}
		if (element.compareTo(value) > 0) { // left
			// search the left subtree
			return (node.left == null) ? false : contains(node.left, value);
		} else { // right
			// search the right subtree
			return (node.right == null) ? false : contains(node.right, value);
		}
	}
	
	public boolean insert(T value) {
		if (root == null) {
			root = new Node<T>(value);
			return true;
		}
		return insert(root, value);
	}
	
	private boolean insert(Node<T> node, T value) {
		T element = node.element;
		if (element.compareTo(value) == 0) {
			return false; // already exists
		}
		if (element.compareTo(value) > 0) { // left
			if (node.left == null) {
				node.left = new Node<T>(value);
				return true;
			} else {
				return insert(node.left, value);
			}
		} else { // right
			if (node.right == null) {
				node.right = new Node<T>(value);
				return true;
			} else {
				return insert(node.right, value);
			}
		}
	}
	
	public boolean remove(T value) {
		if (root == null) {
			return false;
		}
		if (root.element.compareTo(value) == 0) {
			// use a dummy node to handle swap
			Node<T> fauxNode = new Node<T>(value);
			fauxNode.left = root;
			boolean result = remove(root, value, fauxNode);
			root = fauxNode.left;
			return result;
		} else {
			return remove(root, value, null);
		}
	}
	
	private boolean remove(Node<T> node, T value, Node<T> parent) {
		T element = node.element;
		if (element.compareTo(value) > 0) { // left
			return (node.left == null) ? false : remove(node.left, value, node);
		} else if (element.compareTo(value) < 0) { // right
			return (node.right == null) ? false : remove(node.right, value, node);
		} else { // found the node
			if (node.left != null && node.right != null) { // both children exist
				node.element = findInOrderSuccessor(node).element;
				// remove the redundant node
				remove(node.right, value, node);
			} else if (node == parent.left) { // I am left child
				parent.left = (node.left == null) ? node.right : node.left;
			} else if (node == parent.right) { // I am right child
				parent.right = (node.left == null) ? node.right : node.left;
			}
			return true;
		}
	}
		
	private Node<T> findInOrderSuccessor(Node<T> node) {
		return findMinNode(node.right);
	}
	
	private Node<T> findMinNode(Node<T> node) {
		if (node == null) {
			return null;
		}
		return (node.left == null) ? node : findMinNode(node.left);
	}
	
	private Node<T> findMaxNode(Node<T> node) {
		if (node == null) {
			return null;
		}
		return (node.right == null) ? node : findMaxNode(node.right);
	}
	
	private void printInOrder(Node<T> entry) {
		if (entry != null) {
			printInOrder(entry.left);
			System.out.println(entry.element);
			printInOrder(entry.right);
		}
	}

	public void printInOrder() {
		printInOrder(root);
	}

	private static class Node<T extends Comparable<? super T>> {
		T element;
		Node<T> left;
		Node<T> right;

		Node(T theElement) {
			element = theElement;
			left = right = null;
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

		for (int i = 0; i <= size; i++) {
			tree.insert(new Test1(String.valueOf(i)));
		}

		tree.insert(new Test1("100"));
		tree.remove(new Test1("10"));
		tree.remove(new Test1(String.valueOf(15)));
		tree.remove(new Test1(String.valueOf(20)));
		tree.printInOrder();
		System.out.println("Contains (10) : " + tree.contains(new Test1("10")));
		System.out.println("Contains (11) : "
				+ tree.contains(new Test1(String.valueOf(11))));
	}

}
