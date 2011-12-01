package org.sjlee.alg;

public class FlipBits {
	public static int flipBits(int b) {
		return ~b;
	}
	
	public static void main(String[] args) {
		int result = flipBits(7);
		System.out.println(result);
	}
}
