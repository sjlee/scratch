package org.sjlee.alg;

public interface KeyCounter<K> {
	void increment(K key);
	int getCount(K key);
}
