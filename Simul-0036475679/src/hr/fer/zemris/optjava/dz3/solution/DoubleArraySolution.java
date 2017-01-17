package hr.fer.zemris.optjava.dz3.solution;

import java.util.Random;

public class DoubleArraySolution extends SingleObjectiveSolution {

	public double[] values;
	
	public DoubleArraySolution(int size) {
		values = new double[size];
	}
	
	public DoubleArraySolution newLikeThis() {
		return new DoubleArraySolution(values.length);
	}
	
	public DoubleArraySolution duplicate() {
		DoubleArraySolution dup = newLikeThis();
		System.arraycopy(values, 0, dup.values, 0, values.length);
		return dup;
	}
	
	public void randomize(Random rand, double[] mins, double[] maxs) {
		
		for (int i=0; i<this.values.length; i++) {
			this.values[i] = rand.nextDouble() * (maxs[i]-mins[i]) + mins[i];
		}
		
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("(");
		for (double val : values) {
			sb.append(val + ", ");
		}
		sb.delete(sb.length()-2, sb.length());
		sb.append(")");
		return sb.toString();
	}
	
}
