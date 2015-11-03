package hr.fer.zemris.optjava.dz5.algorithm;

import hr.fer.zemris.optjava.dz5.population.Population;
import hr.fer.zemris.optjava.dz5.solution.PermutationSolution;

public class AlgorithmOS implements Algorithm<PermutationSolution>{

	private Population<PermutationSolution> population;
	private double succRatio;
	private double maxSelPress;
	private double compFactor;
	
	public AlgorithmOS(Population<PermutationSolution> population, double succRatio, double maxSelPress, double compFactor) {
		this.population = new Population<>(population.getSize());
		for (PermutationSolution sol : population.population) {
			this.population.add(sol);
		}
		this.succRatio = succRatio;
		this.maxSelPress = maxSelPress;
		this.compFactor = compFactor;
		
	}
	
	@Override
	public PermutationSolution run() {
		
		
		
		return null;
	}

}
