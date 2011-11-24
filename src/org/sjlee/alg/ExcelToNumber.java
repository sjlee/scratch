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
			value += getUnitValue(c)*((int)Math.pow(UNIT, (size - 1 - i)));
		}
		return value;
	}
	
	public static int getUnitValue(char c) {
		return c - 'A' + 1;
	}
	
	public static void main(String[] args) {
		System.out.println(excelToNumber("A"));
		System.out.println(excelToNumber("AA"));
		System.out.println(excelToNumber("AB"));
		System.out.println(excelToNumber("BA"));
	}
}
