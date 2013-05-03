package org.sjlee.alg;

import com.google.common.collect.ConcurrentHashMultiset;
import com.google.common.collect.Multiset;

public class ConcurrentKeyCounterOnMultiSet<K> implements KeyCounter<K> {
	private final Multiset<K> multiset = ConcurrentHashMultiset.create();

	public void increment(K key) {
		multiset.add(key);
	}

	public int getCount(K key) {
		return multiset.count(key);
	}
}
