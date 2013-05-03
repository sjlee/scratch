package org.sjlee.data;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

public class Bidi {
	private final Map<Long,Long> map = new HashMap<Long,Long>();
	private final Map<Long,SortedSet<Long>> reverseMap = new HashMap<Long,SortedSet<Long>>();
	
	public void insertOrReplace(long index, long value) {
		Long oldValue = map.put(index, value);
		SortedSet<Long> indices = reverseMap.get(value);
		if (indices == null) {
			indices = new TreeSet<Long>();
			reverseMap.put(value, indices);
		}
		indices.add(index);
		if (oldValue != null) {
			reverseMap.get(oldValue).remove(index); // clean up the old mapping
		}
	}
	
	public long findFirst(long value) {
		SortedSet<Long> indices = reverseMap.get(value);
		if (indices == null || indices.isEmpty()) {
			return -1;
		}
		return indices.first();
	}
}
