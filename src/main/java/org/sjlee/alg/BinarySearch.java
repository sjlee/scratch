package org.sjlee.alg;

public class BinarySearch {
	// returns the index of the matching element; -1 if it is not found
	public static int binarySearch(int[] sorted, int value) {
		return binarySearch(sorted, value, 0, sorted.length-1);
	}
	
	private static int binarySearch(int[] sorted, int value, int left, int right) {
		if (left > right) {
			return -1;
		}
		
		int half = (left + right)/2;
		if (value == sorted[half]) {
			return half;
		} else if (value > sorted[half]) {
			return binarySearch(sorted, value, half+1, right);
		} else { // value < sorted[half]
			return binarySearch(sorted, value, left, half-1);
		}
	}
	
	public static void main(String[] args) {
		int[] test = {10, 5, 7, 6, 9, 2, 8, 13, 1, 4};
		Quicksort.quicksort(test); // sort first
		for (int i: test) {
			System.out.println(i);
		}
		int index = binarySearch(test, 6);
		System.out.println(index);
		index = binarySearch(test, 3);
		System.out.println(index);
	}
}
