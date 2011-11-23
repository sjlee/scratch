package org.sjlee.alg;

import java.math.BigInteger;
import java.util.Random;

public class BigMultiplier {
	// O(n)
	public static BigInteger[] getMultipliers(long[] input) {
		BigInteger[] left = new BigInteger[input.length];
		BigInteger[] right = new BigInteger[input.length];
		for (int i = 0; i < input.length; i++) {
			int j = input.length-1-i;
			if (i == 0) {
				left[i] = BigInteger.ONE;
				right[j] = BigInteger.ONE;
			} else {
				left[i] = left[i-1].multiply(BigInteger.valueOf(input[i-1]));
				right[j] = right[j+1].multiply(BigInteger.valueOf(input[j+1]));
			}
		}
		
		BigInteger[] output = new BigInteger[input.length];
		for (int i = 0; i < input.length; i++) {
			output[i] = left[i].multiply(right[i]);
		}
		return output;
	}
	
	private static void prettyPrintLongArray(long[] arr) {
		StringBuilder sb = new StringBuilder("{");
		for (int i = 0; i < arr.length; i++) {
			sb.append(arr[i]);
			if (i < arr.length-1) {
				sb.append(", ");
			} else {
				sb.append("}");
			}
		}
		System.out.println(sb.toString());
	}
	
	private static void prettyPrintBigIntegerArray(BigInteger[] arr) {
		StringBuilder sb = new StringBuilder("{");
		for (int i = 0; i < arr.length; i++) {
			sb.append(arr[i].toString());
			if (i < arr.length-1) {
				sb.append(", ");
			} else {
				sb.append("}");
			}
		}
		System.out.println(sb.toString());
	}
	
	public static void main(String[] args) {
		int size = 5;
		int max = 5;
		Random rnd = new Random();
		long[] input = new long[size];
		for (int i = 0; i < input.length; i++) {
			input[i] = rnd.nextInt(max) + 1; // 0 is no fun
		}
		prettyPrintLongArray(input);
		long begin = System.nanoTime();
		BigInteger[] output = getMultipliers(input);
		long end = System.nanoTime();
		System.out.println("took " + (end - begin) + " ns.");
		prettyPrintBigIntegerArray(output);
	}
}
