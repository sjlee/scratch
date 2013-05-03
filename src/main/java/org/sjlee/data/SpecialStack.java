package org.sjlee.data;

import java.util.Stack;

public class SpecialStack<T extends Comparable<? super T>> extends Stack<T> {
	private static final long serialVersionUID = -3570787171198667827L;
	
	private final Stack<T> minStack = new Stack<T>();
	
	@Override
	public synchronized T push(T item) {
		T ret = super.push(item);
		if (minStack.isEmpty()) {
			minStack.push(item);
		} else {
			T currentMinimum = getMinimum();
			if (item.compareTo(currentMinimum) <= 0) {
				minStack.push(item);
			}
		}
		return ret;
	}

	@Override
	public synchronized T pop() {
		T ret = super.pop();
		T currentMinimum = getMinimum();
		if (ret.compareTo(currentMinimum) == 0) {
			minStack.pop();
		}
		return ret;
	}
		
	public T getMinimum() {
		return minStack.peek();
	}
	
	public static void main(String[] args) {
		SpecialStack<Integer> stack = new SpecialStack<Integer>();
		stack.push(3);
		stack.push(4);
		stack.push(2);
		stack.push(6);
		stack.push(2);
		stack.push(10);
		stack.push(1);
		stack.push(1);
		
		int min = stack.getMinimum();
		if (min != 1) {
			System.err.println("error");
		}
		stack.pop();
		stack.pop();
		min = stack.getMinimum();
		if (min != 2) {
			System.err.println("error");
		}
	}
}
