package org.sjlee.alg;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ListIntersection {
	public static <T> Set<T> intersection(List<T> l1, List<T> l2) {
		if (l1 == null || l2 == null || l1.isEmpty() || l2.isEmpty()) {
			return new HashSet<T>(); // empty
		}
		// O(n)
		Set<T> s1 = new HashSet<T>(l1); // hash them
		Set<T> ret = new HashSet<T>();
		for (T item : l2) {
			if (s1.contains(item)) { // O(1)
				ret.add(item);
			}
		}
		return ret;
	}
}
