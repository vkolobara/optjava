package hr.fer.zemris.optjava.dz7.part2;

import java.util.LinkedList;
import java.util.List;

public class Population {

	public List<BitvectorSolution> population;
	private int size;
	
	public Population(int size) {
		this.size = size;
		population = new LinkedList<>();
	}
	
	public boolean isFull() {
		return population.size() == size;
	}
	
	public void addSolution(BitvectorSolution sol) {
		population.add(sol);
	}
	
	public BitvectorSolution getBest() {
		double bestFit = Double.MAX_VALUE;
		BitvectorSolution best = null;
		for (BitvectorSolution p : population) {
			if (p.fitness < bestFit) {
				best = p;
				bestFit = p.fitness;
			}
		}
		return best;
	}
	
	public List<BitvectorSolution> getPopulation() {
		return new LinkedList<>(population);
	}
}
