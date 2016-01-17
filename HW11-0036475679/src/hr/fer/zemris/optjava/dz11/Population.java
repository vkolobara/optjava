package hr.fer.zemris.optjava.dz11;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import hr.fer.zemris.generic.ga.IntegerArraySolution;

public class Population {

	public List<IntegerArraySolution> population;
	private int size;
	
	public Population(int size) {
		this.size = size;
		population = new LinkedList<>();
	}
	
	public boolean isFull() {
		return population.size() == size;
	}
	
	public void addSolution(IntegerArraySolution sol) {
		if (!isFull()) population.add(sol);
	}
	
	public IntegerArraySolution getBest() {
		IntegerArraySolution best = Collections.max(population);
		return best;
	}
	
	public List<IntegerArraySolution> getPopulation() {
		return new LinkedList<>(population);
	}

	public int getSize() {
		return size;
	}

}
