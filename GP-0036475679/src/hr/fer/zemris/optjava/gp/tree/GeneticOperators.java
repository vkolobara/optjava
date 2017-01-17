package hr.fer.zemris.optjava.gp.tree;

import java.util.List;
import java.util.Random;

import hr.fer.zemris.optjava.gp.Eater;

public class GeneticOperators {

	private final static int MAX_NODES = 200;
	private final static int MAX_DEPTH = 20;
	private final static int MAX_TRIES = 20;
	private final static double MUT_RATE = 0.6;
	private final static double CROSS_RATE = 0.85;
	private final static double REPROD_RATE = 0.01;
	
	

	private static Random rand = new Random();

	public static Tree crossover(Tree p1, Tree p2) {
		
		for (int i=0; i<MAX_TRIES; i++) {
			int size1 = p1.size();
			int size2 = p2.size();
			if (rand.nextDouble() <= REPROD_RATE) return p1.duplicate();
			if (rand.nextDouble() <= CROSS_RATE) {
				Tree dup = p1.duplicate();
				Node node = p2.getAtIndex(rand.nextInt(size2)).duplicate();
				int index = rand.nextInt(size1);
				int depth = dup.getAtIndex(index).getDepth();
				
				node.setDepth(depth);
				if (node.getMostDeep() >= MAX_DEPTH) continue;
				
				dup.setAtIndex(index, node);
				if (dup.size() > MAX_NODES) continue;
				return dup;
			}
		}

		return p2.duplicate();
	}

	public static Tree mutate(Tree child) {
		if (rand.nextDouble() <= MUT_RATE) {
			int size = child.size();
			int index = rand.nextInt(size);
			Tree mut = child.duplicate();
			int d = mut.getAtIndex(index).getDepth();
			int depth = rand.nextInt(MAX_DEPTH - d) + d;
			mut.setAtIndex(index, mut.growBuild(depth, d));
			
			if (mut.size() <= MAX_NODES) {
				return mut;
			}
		}

		return child;
	}

	public static Eater tournamentSelection(Population pop, int n) {

		Population tournament = new Population(n);
		List<Eater> population = pop.getPopulation();
		int size = population.size();

		while (!tournament.isFull()) {
			tournament.add(population.get(rand.nextInt(size)));
		}

		return tournament.getBest();
	}

}
