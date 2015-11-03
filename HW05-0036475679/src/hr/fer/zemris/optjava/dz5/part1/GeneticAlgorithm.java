package hr.fer.zemris.optjava.dz5.part1;

import hr.fer.zemris.optjava.dz5.algorithm.AlgorithmRAPGA;
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
		int n = 100;
		int k = 4;
		
		
		AlgorithmRAPGA alg = new AlgorithmRAPGA(minPop, maxPop, maxSelPress, compFactor, n, k);
		Solution sol = alg.run();
		System.out.println(sol + "\n" + sol.fitness);
		
	}
	
}
