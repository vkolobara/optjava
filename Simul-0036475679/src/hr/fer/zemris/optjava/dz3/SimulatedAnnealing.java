package hr.fer.zemris.optjava.dz3;

import java.util.Random;

import hr.fer.zemris.optjava.dz3.algorithm.IOptAlgorithm;
import hr.fer.zemris.optjava.dz3.decoder.IDecoder;
import hr.fer.zemris.optjava.dz3.function.IFunction;
import hr.fer.zemris.optjava.dz3.neighborhood.INeighborhood;
import hr.fer.zemris.optjava.dz3.schedule.ITempSchedule;

public class SimulatedAnnealing<T> implements IOptAlgorithm<T> {

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
	}

	@Override
	public void run() {

		
	}

}
