package hr.fer.zemris.optjava.dz5.population;

import hr.fer.zemris.optjava.dz5.Solution;
import hr.fer.zemris.optjava.dz5.population.Population;

public class RAPGAPopulation extends Population<Solution> {

	public RAPGAPopulation(int size) {
		super(size);
	}
	
	public boolean add(Solution solution) {
		return this.population.add(solution);
	}

}
