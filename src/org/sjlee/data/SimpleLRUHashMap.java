package org.sjlee.data;

import java.util.LinkedHashMap;

public class SimpleLRUHashMap<K,V> extends LinkedHashMap<K,V> {
	private static final long serialVersionUID = 7508906746731292235L;
	
	private final int maxSize;
	
	public SimpleLRUHashMap(int s) {
		maxSize = s;
	}

	@Override
	protected boolean removeEldestEntry(java.util.Map.Entry<K, V> eldest) {
		return size() >= maxSize;
	}
}
