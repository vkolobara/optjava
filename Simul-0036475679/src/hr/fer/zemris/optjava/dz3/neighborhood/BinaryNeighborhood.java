package hr.fer.zemris.optjava.dz3.neighborhood;

import java.util.Random;

import hr.fer.zemris.optjava.dz3.solution.BitvectorSolution;

public class BinaryNeighborhood implements INeighborhood<BitvectorSolution>{

	protected Random rand;
	public int bits[];
	public int n;

	public BinaryNeighborhood(int bits[], int n) {
		rand = new Random();
		this.n = n;
		this.bits = bits;
		
	}
	
	@Override
	public BitvectorSolution randomNeighbor(BitvectorSolution solution) {
		BitvectorSolution neighbor = solution.duplicate();
		int currIndex = 0;
		for (int i=0; i<n; i++) {
			int ind = rand.nextInt(bits[i]);
			neighbor.bits[currIndex+ind] = !neighbor.bits[currIndex+ind];
			currIndex+=bits[i];
		}
		return neighbor;
	}

}
