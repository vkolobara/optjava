package hr.fer.zemris.optjava.dz10;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Population {

	public List<DoubleArraySolution> population;
	private int size;
	
	public Population(int size) {
		this.size = size;
		population = new LinkedList<>();
	}
	
	public boolean isFull() {
		return population.size() == size;
	}
	
	public void addSolution(DoubleArraySolution sol) {
		if (!isFull()) population.add(sol);
	}
	
	public DoubleArraySolution getBest() {
		DoubleArraySolution best = Collections.max(population);
		return best;
	}
	
	public List<DoubleArraySolution> getPopulation() {
		return new LinkedList<>(population);
	}

	public int getSize() {
		return size;
	}

}
