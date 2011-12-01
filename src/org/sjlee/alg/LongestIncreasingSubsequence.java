package org.sjlee.alg;

public class LongestIncreasingSubsequence {
	private int longest;
	private int finalBegin;
	private int finalEnd;
	
	public void findLongestIncreasingSubsequence(int[] seq) {
		int begin = 0;
		int end = 0;
		for (int i = 1; i < seq.length; i++) {
			if (seq[i] > seq[i-1]) {
				end = i;
			} else { // sequence is broken; record the value and start again
				int length = end - begin - 1;
				if (length > longest) {
					longest = length;
					finalBegin = begin;
					finalEnd = end;
				}
				begin = i;
				end = i;
			}
		}
		// handle the last possibility
		int length = end - begin;
		if (length > longest) {
			longest = length;
			finalBegin = begin;
			finalEnd = end;
		}
		System.out.println("longest increasing subsequence: from " + finalBegin + " to " + finalEnd);
	}
	
	public static void main(String[] args) {
		int[] seq = {1, 3, 2, 4, 6, 8, 8, 7, 3, 5, 7, 9, 11, 13};
		LongestIncreasingSubsequence s = new LongestIncreasingSubsequence();
		s.findLongestIncreasingSubsequence(seq);
	}
}
