package org.sjlee.alg;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class TVShowConflicts {
	public static void reportConflicts(Time[] times) {
		Set<String> inProgress = new HashSet<String>();
		List<Set<String>> conflictSets = new LinkedList<Set<String>>();
		Set<String> conflict = null;
		for (int i = 0; i < times.length; i++) {
			Time t = times[i];
			if (t.start) { // the show has started: add it to in progress
				inProgress.add(t.name);
			} else { // the show has ended: remove it from in progress
				inProgress.remove(t.name);
			}
			if (inProgress.size() > 1) { // we have a conflict; let's record it
				if (conflict == null) {
					conflict = new HashSet<String>();
				}
				conflict.addAll(inProgress);
			} else {
				if (conflict != null && conflict.size() > 1) {
					conflictSets.add(conflict);
					conflict = null; // reset
				}
			}
		}
		for (Set<String> conflictSet: conflictSets) {
			System.out.println(conflictSet);
		}
	}

	private static class Time implements Comparable<Time> {
		int time;
		String name;
		boolean start;
		
		public Time(String name, int time, boolean start) {
			this.name = name;
			this.time = time;
			this.start = start;
		}

		@Override
		public int compareTo(Time o) {
			return time - o.time;
		}
	}
	
	public static void main(String[] args) {
		Time[] times = {
			new Time("A", 1900, true), new Time("A", 2100, false),
			new Time("B", 1930, true), new Time("B", 2400, false),
			new Time("C", 2000, true), new Time("C", 2200, false),
			new Time("D", 2201, true), new Time("D", 2300, false),
			new Time("E", 2230, true), new Time("E", 2330, false)
		};
		Arrays.sort(times);
		reportConflicts(times);
	}
}
