package hr.fer.zemris.optjava.dz3.solution;

import java.util.Random;

public class BitvectorSolution extends SingleObjectiveSolution{

	public boolean[] bits;
	
	public BitvectorSolution(int size) {
		bits = new boolean[size];
	}
	
	public BitvectorSolution newLikeThis() {
		return new BitvectorSolution(bits.length);
	}
	
	public BitvectorSolution duplicate() {
		BitvectorSolution dup = new BitvectorSolution(bits.length);
		System.arraycopy(bits, 0, dup.bits, 0, bits.length);
		return dup;
	}
	
	public void randomize(Random rand) {
		for (int i=0; i<bits.length; i++) {
			bits[i] = rand.nextBoolean();
		}
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i=0; i<bits.length; i++) {
			sb.append(bits[i] ? "1" : "0");
		}
		return sb.toString();
	}
	
	
}
