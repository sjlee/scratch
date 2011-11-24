package org.sjlee.alg;

public class FindString {
	public static String findON2(String a, String b) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < a.length(); i++) {
			char achar = a.charAt(i);
			for (int j = 0; j < b.length(); j++) {
				if (b.charAt(j) == achar) {
					sb.append(achar);
				}
			}
		}
		return sb.toString();
	}
	
	public static String findON(String a, String b) {
		boolean[] found = new boolean[256];
		for (int i = 0; i < b.length(); i++) {
			found[b.charAt(i)] = true;
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < a.length(); i++) {
			char c = a.charAt(i);
			if (found[c]) {
				sb.append(c);
			}
		}
		return sb.toString();
	}
	
	public static void main(String[] args) {
		String a = "0123456789abcdefghijklmnop";
		String b = "jwevhdfj";
		
		long begin = System.nanoTime();
		String s = findON2(a, b);
		long end = System.nanoTime();
		System.out.println("result: " + s + ", O(N^2) took " + (end - begin) + " ns.");
		begin = System.nanoTime();
		s = findON(a, b);
		end = System.nanoTime();
		System.out.println("result: " + s + ", O(N) took " + (end - begin) + " ns.");
	}
}
