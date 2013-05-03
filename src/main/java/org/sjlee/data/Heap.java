package org.sjlee.data;

import java.io.IOException;

public class Heap {
	private Node[] heapArray;
	private int maxSize; // size of array
	private int currentSize; // number of nodes in array

	public Heap(int mx) {
		maxSize = mx;
		currentSize = 0;
		heapArray = new Node[maxSize]; // create array
	}

	public boolean isEmpty() {
		return currentSize == 0;
	}

	public boolean insert(int key) {
		if (currentSize == maxSize) {
			return false;
		}
		Node newNode = new Node(key);
		heapArray[currentSize] = newNode;
		trickleUp(currentSize++);
		return true;
	}

	public void trickleUp(int index) {
		int parent = (index - 1) / 2;
		Node bottom = heapArray[index];

		while (index > 0 && heapArray[parent].getValue() < bottom.getValue()) {
			heapArray[index] = heapArray[parent]; // move the parent down
			index = parent;
			parent = (parent - 1) / 2;
		}
		heapArray[index] = bottom;
	}

	public Node remove() { // delete item with max key
	    // (assumes non-empty list)
		Node root = heapArray[0];
		heapArray[0] = heapArray[--currentSize];
		trickleDown(0);
		return root;
	} // end remove()

	public void trickleDown(int index) {
		int largerChild;
		Node top = heapArray[index]; // save root
		while (index < currentSize / 2) { // while node has at
			// least one child,
			int leftChild = 2 * index + 1;
			int rightChild = leftChild + 1;
			// find larger child
			if (rightChild < currentSize && // (rightChild exists?)
					heapArray[leftChild].getValue() < heapArray[rightChild]
							.getValue()) {
				largerChild = rightChild;
			} else {
				largerChild = leftChild;
			}
			// top >= largerChild?
			if (top.getValue() >= heapArray[largerChild].getValue()) {
				break;
			}
			// shift child up
			heapArray[index] = heapArray[largerChild];
			index = largerChild; // go down
		}
		heapArray[index] = top; // root to index
	}

	public boolean change(int index, int newValue) {
		if (index < 0 || index >= currentSize) {
			return false;
		}
		int oldValue = heapArray[index].getValue(); // remember old
		heapArray[index].setValue(newValue); // change to new

		if (oldValue < newValue) {
			trickleUp(index);
		} else {
			trickleDown(index);
		}
		return true;
	}

	public void displayHeap() {
		System.out.print("heapArray: "); // array format
		for (int m = 0; m < currentSize; m++) {
			if (heapArray[m] != null) {
				System.out.print(heapArray[m].getValue() + " ");
			} else {
				System.out.print("-- ");
			}
		}
		System.out.println('\n');
		int nBlanks = 32;
		int itemsPerRow = 1;
		int column = 0;
		int j = 0; // current item

		while (currentSize > 0) { // for each heap item
			if (column == 0) { // first item in row?
				for (int k = 0; k < nBlanks; k++) {
					// preceding blanks
					System.out.print(' ');
				}
			}
			// display item
			System.out.print(heapArray[j].getValue());

			if (++j == currentSize) {// done?
				break;
			}

			if (++column == itemsPerRow) { // end of row?
				nBlanks /= 2; // half the blanks
				itemsPerRow *= 2; // twice the items
				column = 0; // start over on
				System.out.println(); // new row
			} else {
				// next item on row
				for (int k = 0; k < nBlanks * 2 - 2; k++) {
					System.out.print(' '); // interim blanks
				}
			}
		}
		System.out.print('\n');
	}

	public static void main(String[] args) throws IOException {
		int value, value2;
		Heap h = new Heap(31); // make a Heap; max size 31
		boolean success;

		h.insert(70); // insert 10 items
//		h.displayHeap();
		h.insert(40);
//		h.displayHeap();
		h.insert(50);
//		h.displayHeap();
		h.insert(20);
//		h.displayHeap();
		h.insert(60);
//		h.displayHeap();
		h.insert(100);
//		h.displayHeap();
		h.insert(80);
//		h.displayHeap();
		h.insert(30);
//		h.displayHeap();
		h.insert(10);
//		h.displayHeap();
		h.insert(90);
//		h.displayHeap();
		value = 100;
		success = h.insert(value);
		h.displayHeap();
		if (!success) {
			System.out.println("Can't insert; heap full");
		}
		if (!h.isEmpty()) {
			h.remove();
		} else {
			System.out.println("Can't remove; heap empty");
		}
		value = 80;
		value2 = 999;
		success = h.change(value, value2);
		if (!success) {
			System.out.println("Invalid index");
		}
		// remove all of them one at a time
		System.out.println("Removing all...");
		for (int i = 0; i < 10; i++) {
			Node n = h.remove();
			System.out.println(n.getValue());
		}
	}

	private class Node {
		private int value;

		public Node(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}

	}

}
