package hr.fer.zemris.optjava.dz6;

import java.util.Arrays;
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

		
		for (int t=0; t<maxIter; t++) {
			for (Ant ant : pop.population) {
				ant.clear();
			}
			System.out.println(Arrays.deepToString(graph.pheromoneMatrix));

			Ant bestIter = null;
			
			for (Ant ant : pop.getPopulation()) {
				while (!ant.isFinished()) {
					ant.move();
				}
				ant.setFitness();
				
				if (bestSoFar == null || ant.fitness > bestSoFar.fitness) {
					bestSoFar = ant;
					bestIter = ant;
				} else if (bestIter == null || ant.fitness > bestIter.fitness) {
					bestIter = ant;
				}
				
			}			
			graph.evaporate();
			graph.pheromoneTrail(bestIter);
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
