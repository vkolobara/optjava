package hr.fer.zemris.optjava.dz5.part1;

import hr.fer.zemris.optjava.dz5.Solution;
import hr.fer.zemris.optjava.dz5.algorithm.AlgorithmRAPGA;

public class GeneticAlgorithm {

	public static void main(String[] args) {
		
		int minPop = 60;
		int maxPop = 100;
		double maxSelPress = 2;
		double compFactor = 1;
		int n = 10;
		int k = 3;
		
		
		AlgorithmRAPGA alg = new AlgorithmRAPGA(minPop, maxPop, maxSelPress, compFactor, n, k);
		Solution sol = alg.run();
		System.out.println(sol + "\n" + sol.fitness);
		
	}
	
}
