package hr.fer.zemris.optjava.dz3;

import java.util.Random;

import hr.fer.zemris.optjava.dz3.algorithm.IOptAlgorithm;
import hr.fer.zemris.optjava.dz3.decoder.IDecoder;
import hr.fer.zemris.optjava.dz3.function.IFunction;
import hr.fer.zemris.optjava.dz3.neighborhood.INeighborhood;
import hr.fer.zemris.optjava.dz3.schedule.ITempSchedule;
import hr.fer.zemris.optjava.dz3.solution.BitvectorSolution;
import hr.fer.zemris.optjava.dz3.solution.DoubleArraySolution;
import hr.fer.zemris.optjava.dz3.solution.SingleObjectiveSolution;

public class SimulatedAnnealing<T> implements IOptAlgorithm<T> {

	private final static double A1 = 0.6;
	private final static double X = 0.8;
	
	private IDecoder<T> decoder;
	private INeighborhood<T> neighborhood;
	private T startWith;
	private IFunction function;
	private boolean minimize;
	private Random rand;
	private ITempSchedule schedule;

	public SimulatedAnnealing(IDecoder<T> decoder, INeighborhood<T> neighborhood, T startWith, IFunction function,
			ITempSchedule schedule, boolean minimize) {

		this.decoder = decoder;
		this.neighborhood = neighborhood;
		this.startWith = startWith;
		this.function = function;
		this.minimize = minimize;
		this.schedule = schedule;
		rand = new Random();
	}

	@Override
	public T run() {
		
		T solution = startWith;
		int k=0;
		int outerLimit = schedule.getOuterLoopCounter();
		int innerLimit = schedule.getInnerLoopCounter();
		
		do {
			int m=0;

			do {
				T neighbor = neighborhood.randomNeighbor(solution);
				double dE = function.valueAt(decoder.decode(neighbor)) - function.valueAt(decoder.decode(solution));
								
				if (!minimize) dE = -dE;

				if (dE < 0) {
					solution = neighbor;
				} else if (rand.nextDouble() <= A1 * Math.pow(X, k)){
					solution = neighbor;
				}
			} while (m++<innerLimit);
		} while(k++<outerLimit);
		((SingleObjectiveSolution) solution).fitness = function.valueAt(decoder.decode(solution));
		return solution;
	}

}
