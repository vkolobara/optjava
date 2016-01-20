package hr.fer.zemris.optjava.dz12.algorithm;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Population {

	public List<Solution> population;
	private int size;
	
	public Population(int size) {
		this.size = size;
		population = new LinkedList<>();
	}
	
	public boolean isFull() {
		return population.size() == size;
	}
	
	public void addSolution(Solution sol) {
		if (!isFull()) population.add(sol);
	}
	
	public Solution getBest() {
		Solution best = Collections.max(population);
		return best;
	}
	
	public List<Solution> getPopulation() {
		return new LinkedList<>(population);
	}

	public int getSize() {
		return size;
	}

}
