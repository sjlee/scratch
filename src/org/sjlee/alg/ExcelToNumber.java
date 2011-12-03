package org.sjlee.alg;


public class ExcelToNumber {
	// a -> 1, b -> 2, ... aa -> 27, ...
	public static final int UNIT = 26;
	
	public static int excelToNumber(String col) {
		String s = col.toUpperCase();
		int size = s.length();
		int value = 0;
		for (int i = size-1; i >= 0; i--) {
			char c = s.charAt(i);
			value += charToInt(c)*((int)Math.pow(UNIT, (size - 1 - i)));
		}
		return value;
	}
	
	public static int charToInt(char c) {
		return c - 'A' + 1;
	}
	
	public static String numberToExcel(int n) {
		int current = n;
		StringBuilder sb = new StringBuilder();
		while (true) {
			int quotient = current / UNIT;
			int remainder = current % UNIT;
			sb.append(intToChar(remainder));
			if (quotient == 0) {
				break;
			}
			current = quotient;
		}
		return sb.reverse().toString();
	}
	
	/**
	 * Ranges from 1 to 26.
	 */
	public static char intToChar(int i) {
		return (char)(i + 'A' - 1);
	}
	
	public static void main(String[] args) {
		System.out.println(excelToNumber("A"));
		System.out.println(excelToNumber("AA"));
		System.out.println(excelToNumber("AB"));
		System.out.println(excelToNumber("BA"));
		System.out.println(numberToExcel(1));
		System.out.println(numberToExcel(27));
		System.out.println(numberToExcel(28));
		System.out.println(numberToExcel(53));
		// just one more
		String from = "FRE";
		String to = numberToExcel(excelToNumber(from));
		System.out.println(from + " <-> " + to);
		if (!to.equals(from)) {
			System.err.println("wrong!");
		}
	}
}
