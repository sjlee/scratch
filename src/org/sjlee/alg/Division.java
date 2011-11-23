package org.sjlee.alg;

public class Division {
	private int numerator;
	private int denominator;
	private int remainder;
	
	public int divide() {
		return divide(numerator, denominator);
	}
	
	// works only for positive integers (natural numbers)
	public int divide(int tempN, int tempD) {
		int quotient = 1;
		if (tempD == tempN) {
			remainder = 0;
			return 1;
		}
		if (tempN < tempD) {
			remainder = tempN;
			return 0;
		}
		
		// tempD is really (denominator)*(tried quotient)
		while (tempD <= tempN) {
			tempD = tempD << 1; // keep multiplying by 2 until it goes over
			quotient = quotient << 1;
		}
		
		// back off by one
		tempD = tempD >> 1;
		quotient = quotient >> 1;
//		System.out.println("quotient = " + quotient + ", tempD = " + tempD);
			
		// compute the remaining sum
		quotient = quotient + divide(tempN - tempD, denominator);
		return quotient;
	}
	
	public static void main(String[] args) {
		Division d = new Division();
		d.numerator = 1223452345;
		d.denominator = 3;
		int quotient = d.divide();
		System.out.println("quotient: " + quotient + ", remainder: " + d.remainder);
	}
}
