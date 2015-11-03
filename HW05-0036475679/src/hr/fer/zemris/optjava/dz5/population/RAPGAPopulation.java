package hr.fer.zemris.optjava.dz5.population;

import hr.fer.zemris.optjava.dz5.population.Population;
import hr.fer.zemris.optjava.dz5.solution.BitVectorSolution;
import hr.fer.zemris.optjava.dz5.solution.Solution;

public class RAPGAPopulation extends Population<BitVectorSolution> {

	public RAPGAPopulation(int size) {
		super(size);
	}
	
	public boolean add(BitVectorSolution solution) {
		return this.population.add(solution);
	}

}
