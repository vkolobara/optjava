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
		int n = 10;
		int k = 4;
		
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
