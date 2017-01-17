package hr.fer.zemris.optjava.gp.tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.optjava.gp.Eater;

public class Population {
	private List<Eater> population;
	private int size;
	
	public Population(int size) {
		this.size = size;
		population = new ArrayList<>();
	}
	
	public void add(Eater eater) {
		if (!isFull()) {
			population.add(eater);
		}
	}
	
	public boolean isFull() {
		return population.size() == size;
	}
	
	public List<Eater> getPopulation() {
		return population;
	}
	
	public Eater getBest() {
		return Collections.max(population);
	}
	

}
