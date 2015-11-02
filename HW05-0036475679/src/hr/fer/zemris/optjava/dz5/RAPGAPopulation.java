package hr.fer.zemris.optjava.dz5;

import hr.fer.zemris.optjava.dz5.Population;
import hr.fer.zemris.optjava.dz5.Solution;

public class RAPGAPopulation extends Population<Solution> {

	public RAPGAPopulation(int size) {
		super(size);
	}
	
	public boolean add(Solution solution) {
		return this.population.add(solution);
	}

}
