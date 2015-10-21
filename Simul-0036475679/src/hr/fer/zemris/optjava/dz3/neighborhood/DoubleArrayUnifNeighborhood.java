package hr.fer.zemris.optjava.dz3.neighborhood;

import java.util.Random;

import hr.fer.zemris.optjava.dz3.solution.DoubleArraySolution;

public class DoubleArrayUnifNeighborhood implements INeighborhood<DoubleArraySolution>{

	private double[] deltas;
	protected Random rand;
	
	public DoubleArrayUnifNeighborhood(double[] deltas) {
		this.deltas = new double[deltas.length];
		System.arraycopy(deltas, 0, this.deltas, 0, deltas.length);
		rand = new Random();
	}
	
	@Override
	public DoubleArraySolution randomNeighbor(DoubleArraySolution solution) {
		DoubleArraySolution neighbor = solution.newLikeThis();
		neighbor.randomize(rand, solution.values, deltas);
		return neighbor;
	}



}
