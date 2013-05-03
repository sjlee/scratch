package org.sjlee.alg;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

public class ConcurrentKeyCounter<K> implements KeyCounter<K> {
	private final ConcurrentMap<K,AtomicInteger> counts = new ConcurrentHashMap<>();

	public void increment(K key) {
		AtomicInteger value = counts.get(key);
		if (value == null) {
			value = new AtomicInteger(0);
			AtomicInteger old = counts.putIfAbsent(key, value);
			if (old != null) {
				value = old;
			}
		}
		value.incrementAndGet();
	}

	public int getCount(K key) {
		AtomicInteger value = counts.get(key);
		return value == null ? 0 : value.get();
	}
}
