package hr.fer.zemris.optjava.dz9;



import java.util.Arrays;
import java.util.Random;

public class DoubleArraySolution implements Comparable<DoubleArraySolution> {

	public double[] solution;
	public double[] objectives;
	public double fitness;
	private Random rand;
	
	public DoubleArraySolution(int size) {
		this.solution = new double[size];
		rand = new Random();
	}
	
	public void randomize(double[] max, double[] min) {
		for (int i=0; i<solution.length; i++) {
			solution[i] = rand.nextDouble() * (max[i] - min[i]) + min[i];
		}
	}
	
	public DoubleArraySolution newLikeThis() {
		return new DoubleArraySolution(solution.length);
	}
	
	public DoubleArraySolution duplicate() {
		DoubleArraySolution dup = newLikeThis();
		System.arraycopy(solution, 0, dup.solution, 0, solution.length);
		dup.fitness = fitness;
		return dup;
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(solution);
	}
	
	@Override
	public boolean equals(Object obj) {
		return Arrays.equals(solution, ((DoubleArraySolution) obj).solution);
	}
	
	@Override
	public int compareTo(DoubleArraySolution o) {
		return Double.compare(fitness, o.fitness);
	}
	
	@Override
	public String toString() {
		return Arrays.toString(solution);
	}
	
}
