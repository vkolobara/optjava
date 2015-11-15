package hr.fer.zemris.optjava.dz6;

import java.util.Random;

public class AntAlgorithm {

	private int maxIter;
	private Graph graph;
	private int size;
	
	public AntAlgorithm(int maxIter, Graph graph, int size) {
		this.maxIter = maxIter;
		this.size = size;
		this.graph = graph;
	}
	
	
	public Ant run() {
		
		Ant bestSoFar = null;
		Population<Ant> pop = fillPopulation(size, graph);

		int counter = 0;
		int switchPoint = 2*maxIter/5;
		
		for (int t=0; t<maxIter; t++) {
			for (Ant ant : pop.population) {
				ant.clear();
			}
			Ant bestIter = null;
			
			for (Ant ant : pop.getPopulation()) {
				while (!ant.isFinished()) {
					ant.move();
				}
				ant.setFitness();
				counter++;
				if (bestSoFar == null || ant.fitness > bestSoFar.fitness) {
					counter = 0;
					bestSoFar = ant;
					bestIter = ant;
				} else if (bestIter == null || ant.fitness > bestIter.fitness) {
					bestIter = ant;
				}
				
				if (counter == 100) {
					counter = 0;
					graph.resetPheromones();
				}
				
			}			
			graph.evaporate();
			if (t < switchPoint) {
				graph.pheromoneTrail(bestIter);
			} else {
				graph.pheromoneTrail(bestSoFar);
			}
			
		}
		
				
		return bestSoFar;
	}
	
	private static Population<Ant> fillPopulation(int size, Graph graph) {
		Population<Ant> ret = new Population<>(size);
		int nodeSize = graph.getNodes().size();
		Random rand = new Random();
		
		while (!ret.isFull()) {
			ret.add(new Ant(graph, graph.getNodes().get(rand.nextInt(nodeSize))));
		}

		return ret;
	}
	
}
