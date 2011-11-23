package org.sjlee.alg;

import java.util.Arrays;


public class Anagram {
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
	}
}
