package hr.fer.zemris.optjava.dz5.solution;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import hr.fer.zemris.optjava.dz5.part2.GeneticAlgorithm;

public class PermutationSolution extends Solution {

	public int[] values;
	private int n;

	public PermutationSolution(int n) {
		this.n = n;
		values = new int[n];

	}

	@Override
	public Solution duplicate() {
		PermutationSolution dup = (PermutationSolution) newLikeThis();
		System.arraycopy(values, 0, dup.values, 0, n);
		return dup;
	}

	@Override
	public Solution newLikeThis() {
		return new PermutationSolution(n);
	}

	@Override
	public void setFitness() {
		double sum = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				sum += GeneticAlgorithm.c[i][j] * GeneticAlgorithm.d[values[i]][values[j]];
			}
		}
		this.fitness = sum;

	}

	@Override
	public void randomize() {
		List<Integer> range = IntStream.range(0, n).boxed().collect(Collectors.toList());
		Collections.shuffle(range);
		values = range.stream().mapToInt(i->i).toArray();
	}

	@Override
	public int getSize() {
		return n;
	}
	
	@Override
	public int hashCode() {
		return Arrays.hashCode(values);
	}
	
	@Override
	public boolean equals(Object obj) {
		return Arrays.equals(values, ((PermutationSolution)obj).values);
	}
	
	@Override
	public String toString() {
		return Arrays.toString(values);
	}

}
