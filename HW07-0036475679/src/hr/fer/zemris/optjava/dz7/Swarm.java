package hr.fer.zemris.optjava.dz7;

import java.util.LinkedList;
import java.util.List;

public class Swarm {

	public List<Particle> population;
	private int size;
	private int neighborSize;

	public Swarm(int size) {
		population = new LinkedList<>();
		this.size = size;
	}

	public void initializePopulation(int neighborSize, double c1, double c2, int d, double[] xMax, double[] xMin,
			double[] vMax, double[] vMin, double wMin, int wTMax) {
		this.neighborSize = neighborSize;
		
		for (int i = 0; i < size; i++) {
			Particle p = new Particle(d, c1, c2);
			p.initializeRandom(xMax, xMin, vMax, vMin);
			p.bestSeen = p;
			population.add(p);
		}
		
		
	}

	public boolean isFull() {
		return population.size() == size;
	}

	public Swarm neighbors(int n) {
		Swarm subPop = new Swarm(neighborSize);
		int i = n;
		while (!subPop.isFull()) {
			subPop.population.add(population.get((++i % this.size)));
		}
		return subPop;
	}

	public Particle getBest() {
		double bestFit = Double.MAX_VALUE;
		Particle best = null;
		for (Particle p : population) {
			if (p.fitness < bestFit) {
				best = p;
				bestFit = p.fitness;
			}
		}
		return best;
	}
}
