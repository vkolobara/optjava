package hr.fer.zemris.optjava.dz5.solution;

import java.util.Arrays;
import java.util.Random;

/**
 * Rješenje predstavljeno bitvektorom.
 * @author Vinko
 *
 */
public class BitVectorSolution extends Solution{
	
	public boolean[] values;
	private int n;
	
	public BitVectorSolution(int size) {
		values = new boolean[size];
		this.n = size;
	}

	@Override
	public Solution duplicate() {
		BitVectorSolution dup = (BitVectorSolution) newLikeThis();
		System.arraycopy(this.values, 0, dup.values, 0, this.values.length);
		return dup;
	}

	@Override
	public Solution newLikeThis() {		
		return new BitVectorSolution(this.values.length);
	}

	@Override
	public void setFitness() {
		int k = k();
		
		if (k <= 0.8*n) {
			fitness = 1.0*k / n;
		} else if (k > 0.9*n) {
			fitness = (2.0*k/n)-1;
		} else {
			fitness = 0.8;
		}	
	}
	
	private int k() {
		int sum = 0;
		for (boolean val : values) {
			if (val) sum++;
		}
		return sum;
	}

	@Override
	public void randomize() {
		Random rand = new Random();
		
		for (int i=0; i<values.length; i++) {
			values[i] = rand.nextBoolean();
		}
		
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(values);
	}
	
	@Override
	public boolean equals(Object obj) {
		return Arrays.equals(values, ((BitVectorSolution)obj).values);
	}
	
	@Override
	public int getSize() {
		return n;
	}
	
	@Override
	public String toString() {
		return Arrays.toString(values);
	}
	
}
