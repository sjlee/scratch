package org.sjlee.alg;

public class ReverseWords {
	public static String reverseWords(String s) {
		// String.split()
		String[] tokens = s.split(" +");
		StringBuilder sb = new StringBuilder();
		for (int i = tokens.length-1; i >= 0; i--) {
			sb.append(tokens[i]);
			if (i > 0) {
				sb.append(" ");
			}
		}
		return sb.toString();
	}
	
	public static void main(String[] args) {
		String s = "Mary had a little  lamb.";
		System.out.println(s);
		System.out.println(reverseWords(s));
	}
}
