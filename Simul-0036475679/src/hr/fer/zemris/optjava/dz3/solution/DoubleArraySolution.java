package hr.fer.zemris.optjava.dz3.solution;

import java.util.Random;

public class DoubleArraySolution extends SingleObjectiveSolution {

	private double[] values;
	
	public DoubleArraySolution(int size) {
		values = new double[size];
	}
	
	public DoubleArraySolution newLikeThis() {
		return new DoubleArraySolution(values.length);
	}
	
	public DoubleArraySolution duplicate() {
		DoubleArraySolution dup = new DoubleArraySolution(values.length);
		System.arraycopy(values, 0, dup.values, 0, values.length);
		return dup;
	}
	
	public void randomize(Random rand, double[] first, double[] second) {
	}
	
}
