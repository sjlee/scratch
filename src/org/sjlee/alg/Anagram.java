package org.sjlee.alg;

import java.util.Arrays;


public class Anagram {
	public static void anagram(String s) {
		char[] chars = s.toCharArray();
		doAnagram(chars, 0, chars.length);
	}
	
	private static void doAnagram(char[] chars, int begin, int end) {
		if (end - begin == 1) {
			return; // nothing to do
		}
		for (int i = begin; i < end; i++) {
			doAnagram(chars, begin+1, end); // anagram (n-1)
			if (end - begin == 2) { // we're at the end of the recursion
				print(chars);
			}
			rotate(chars, begin, end);
		}
	}
	
	// rotate to the left
	private static void rotate(char[] chars, int begin, int end) {
		if (end - begin <= 1) {
			return; // nothing to rotate
		}
		char temp = chars[begin];
		for (int i = begin+1; i < end; i++) {
			chars[i-1] = chars[i];
		}
		chars[end-1] = temp;
	}
	
	private static void print(char[] chars) {
		System.out.println(String.valueOf(chars));
	}
	
	public static boolean isAnagram(String a, String b) {
		// some optimization
		if (a == null || b == null || a.equals("") || b.equals("") || a.length() != b.length()) {
			return false;
		}

		a = a.toLowerCase();
		b = b.toLowerCase();
		char[] ca = a.toCharArray();
		char[] cb = b.toCharArray();
		Arrays.sort(ca);
		Arrays.sort(cb);
		for (int i = 0; i < ca.length; i++) {
			if (ca[i] != cb[i]) {
				return false;
			}
		}
		return true;
	}
	
	public static void main(String[] args) {
		System.out.println(isAnagram("admirer", "married"));
		anagram("cats");
	}
}
