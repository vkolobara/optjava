package hr.fer.zemris.optjava.dz5.population;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import hr.fer.zemris.optjava.dz5.algorithm.Algorithm;
import hr.fer.zemris.optjava.dz5.solution.PermutationSolution;

public class SASEGASAPopulation {

	private int size;
	public List<Population<PermutationSolution>> populations;

	public SASEGASAPopulation(int size) {
		this.size = size;
		populations = new LinkedList<>();
	}

	public void addPopulation(Population<PermutationSolution> population) {
		populations.add(population);
	}

	public boolean isFull() {
		return size == populations.size();
	}

	public int getSize() {
		return size;
	}

	public void remove(int index) {
		populations.remove(index);
	}
	
	public Population<PermutationSolution> executeAlgorithm(Algorithm<PermutationSolution> alg) {
		alg.run();
		return null;
	}

	public void mergePopulations() {
		if (size == 1) return;
		
		List<PermutationSolution> solutions = new LinkedList<>();

		int totalSize = 0;

		for (Population<PermutationSolution> pop : populations) {
			solutions.addAll(pop.population);
			totalSize += pop.population.size();
		}
		
		Collections.shuffle(solutions);

		populations.clear();
		size--;
		
		int popSize = totalSize / size;
		
		int i=0;
		while (!isFull()) {
			Population<PermutationSolution> pop = new Population<>(popSize);
			while (!pop.isFull() && i < totalSize) {
				pop.add(solutions.get(i++));
			}
			pop.setSize(pop.population.size());
			populations.add(pop);
		}
		

	}

}
