package hr.fer.zemris.optjava.dz5.part1;

import hr.fer.zemris.optjava.dz5.algorithm.AlgorithmRAPGA;
import hr.fer.zemris.optjava.dz5.population.Population;
import hr.fer.zemris.optjava.dz5.solution.BitVectorSolution;
import hr.fer.zemris.optjava.dz5.solution.Solution;

/**
 * Rješava MaxOnes problem.
 * Argument komandne linije je <b>n</b>, duljina vektora.
 * @author Vinko
 *
 */
public class GeneticAlgorithm {

	public static void main(String[] args) {
		
		int minPop = 50;
		int maxPop = 200;
		double maxSelPress = 10;
		double compFactor = 0;
		int n = 1000;
		int k = 4;
		
		if (args.length != 1) {
			throw new IllegalArgumentException("Potreban 1 argument: n!");
		}
		
		try {
			n = Integer.parseInt(args[0]);
		} catch (Exception e) {
			throw new IllegalArgumentException("Argument mora biti cijeli broj!");
		}
		 
		AlgorithmRAPGA alg = new AlgorithmRAPGA(minPop, maxPop, maxSelPress, compFactor, n, k);
		Solution sol = alg.run(generateRandomPop(minPop, n));
		System.out.println(sol + "\n" + sol.fitness);
		
	}
	

	private static Population<Solution> generateRandomPop(int minPop, int n) {
		Population<Solution> pop = new Population<>(minPop);
		while (!pop.isFull()) {
			BitVectorSolution sol = new BitVectorSolution(n);
			sol.randomize();
			sol.setFitness();
			pop.add(sol);
		}
		return pop;
	}
	
}
