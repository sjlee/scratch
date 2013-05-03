package org.sjlee.alg;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ReservoirSampling {
	public static <T> List<T> sample(List<T> list, int k) {
		List<T> samples = new ArrayList<T>(k);
		int count = 0;
		Random rnd = new Random();
		for (T item: list) {
			count++;
			if (count <= k) { // fill it first
				samples.add(item);
			} else {
				int n = rnd.nextInt(count);
				if (n < k) {
					samples.set(n, item); // probability of an i-th item being placed in the samples is k/i
				}
			}
		}
		return samples;
	}
	
	public static void main(String[] args) {
		int size = 100;
		int sample = 10;
		int max = 100;
		Random rnd = new Random();
		List<Integer> list = new ArrayList<Integer>(size);
		for (int i = 0; i < size; i++) {
			list.add(rnd.nextInt(max));
		}
		System.out.println(list);
		List<Integer> samples = sample(list, sample);
		System.out.println(samples);
	}
}
