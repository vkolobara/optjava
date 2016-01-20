package hr.fer.zemris.optjava.dz12.algorithm;

import java.util.List;

import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.RNG;

public class GeneticOperators {
	
	private final static double MUT_RATE = 0.03;
	
	public static Solution tournamentSelection(Population population, int n) {
		Population tournament = new Population(n);

		IRNG rng = RNG.getRNG();

		List<Solution> pop = population.getPopulation();
		int size = pop.size();

		while (!tournament.isFull()) {
			tournament.addSolution(pop.get(rng.nextInt(0, size)));
		}

		return tournament.getBest();
	}
	
	public static Solution crossover(Solution p1, Solution p2) {

		Solution cross = p1.duplicate();
		IRNG rng = RNG.getRNG();

		for (int i = 0; i < cross.data.length; i++) {
			if (rng.nextDouble() > 0.5) {
				cross.data[i] = p2.data[i];
			}
		}

		return cross;
	}
	
	public static Solution mutate(Solution sol) {

		IRNG rng = RNG.getRNG();

		Solution mutation = sol.duplicate();
		int size = (int) Math.pow(2, Solution.inputNum) + Solution.inputNum;

		for (int i=0; i<sol.data.length; i++) {
			if (rng.nextDouble() <= MUT_RATE) {
				if (i%size < Solution.inputNum) {
					mutation.data[i] = rng.nextInt(0, Solution.numOfVariables + i/size);
				} else {
					mutation.data[i] = 1 - mutation.data[i];
				}
				
				
			}
		}
		
		

		return mutation;

	}
}
