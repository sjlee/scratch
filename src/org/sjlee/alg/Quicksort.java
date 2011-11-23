package org.sjlee.alg;

public class Quicksort {
	public static void quicksort(int[] array) {
		quicksort(array, 0, array.length-1);
	}
	
	private static void quicksort(int[] array, int left, int right) {
		int index = partition(array, left, right);
		if (left < index - 1) {
			quicksort(array, left, index - 1);
		}
		if (index < right) {
			quicksort(array, index, right);
		}
	}
	
	private static int partition(int[] array, int left, int right) {
		int i = left;
		int j = right;
		int tmp;
		int pivot = array[(left + right)/2];
		while (i <= j) {
			while (array[i] < pivot) {
				i++;
			}
			while (array[j] > pivot) {
				j--;
			}
			if (i <= j) {
				tmp = array[i];
				array[i] = array[j];
				array[j] = tmp;
				i++;
				j--;
			}
		}
		return i;
	}
	
	public static void main(String[] args) {
		int[] test = {10, 5, 7, 6, 9, 2, 6, 13, 1, 4};
		quicksort(test);
		for (int i: test) {
			System.out.println(i);
		}
	}
}
