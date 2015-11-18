package hr.fer.zemris.optjava.dz7.part2;

import java.util.Arrays;
import java.util.Random;

public class BitvectorSolution implements Comparable<BitvectorSolution> {

	public boolean[] solution;
	public double fitness;
	private Random rand;
	private int len;
	
	public BitvectorSolution(int len, int dim) {
		this.solution = new boolean[len*dim];
		this.len = len;
		rand = new Random();
	}
	
	public void randomize() {
		for (int i=0; i<solution.length; i++) {
			solution[i] = rand.nextBoolean();
		}
	}
	
	public BitvectorSolution newLikeThis() {
		return new BitvectorSolution(len, solution.length/len);
	}
	
	public BitvectorSolution duplicate() {
		BitvectorSolution dup = newLikeThis();
		System.arraycopy(solution, 0, dup.solution, 0, solution.length);
		return dup;
	}
	
	public double[] decoded(double max, double min) {
		double[] decoded = new double[solution.length/len];
		
		for (int i=0, j=0; i<solution.length; i+=len, j++) {
			boolean[] binary = toBinary(i, i+len);
			decoded[j] = decode(binary) * (max - min) + min;
		}
		
		return decoded;
		
	}

	private double decode(boolean[] binary) {
		double sol = 0;
		for (int i = binary.length-1; i>=0; i--) {
			sol += (binary[i]?1:0) * Math.pow(2, binary.length-1-i);
		}
		return sol;
	}

	private boolean[] toBinary(int start, int end) {
		boolean[] binary = new boolean[end-start];
		binary[0] = solution[start];
	
		for (int i=1; i<binary.length; i++) {
			binary[i] = binary[i-1] ^ solution[start+i];
		}
		
		return binary;
	}
	
	public void mutate(double p) {
		for (int i=0; i<solution.length; i++) {
			if (rand.nextDouble() <= p) {
				solution[i] = !solution[i];
			}
		}
	}
	
	@Override
	public int hashCode() {
		return Arrays.hashCode(solution);
	}
	
	@Override
	public boolean equals(Object obj) {
		return Arrays.equals(solution, ((BitvectorSolution) obj).solution);
	}
	
	@Override
	public int compareTo(BitvectorSolution o) {
		return Double.compare(fitness, o.fitness);
	}
	
}
