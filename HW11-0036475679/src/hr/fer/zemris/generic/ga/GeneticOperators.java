package hr.fer.zemris.generic.ga;

import java.util.List;

import hr.fer.zemris.optjava.dz11.Population;
import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.RNG;

public class GeneticOperators {

	private static final double MUT_RATE = 0.05;
	private static final int MIN_MUT = -50;
	private static final int MAX_MUT = 50;

	public static IntegerArraySolution tournamentSelection(Population population, int n) {
		Population tournament = new Population(n);

		IRNG rng = RNG.getRNG();

		List<IntegerArraySolution> pop = population.getPopulation();
		int size = pop.size();

		while (!tournament.isFull()) {
			tournament.addSolution(pop.get(rng.nextInt(0, size)));
		}

		return tournament.getBest();
	}

	public static IntegerArraySolution randomSelection(Population population) {
		return population.population.get(RNG.getRNG().nextInt(0, population.population.size()));
	}

	public static IntegerArraySolution mutate(int[] min, int[] max, IntegerArraySolution sol) {

		IRNG rng = RNG.getRNG();

		IntegerArraySolution mutation = (IntegerArraySolution) sol.duplicate();

		if (rng.nextDouble() <= MUT_RATE) {
			mutation.data[0] += rng.nextInt(MIN_MUT, MAX_MUT);
			if (mutation.data[0] < min[0]) {
				mutation.data[0] = min[0];
			}
			if (mutation.data[0] > max[0]) {
				mutation.data[0] = max[0];
			}

		}

		for (int i = 1; i < mutation.data.length; i++) {
			if (rng.nextDouble() <= MUT_RATE) {
				mutation.data[i] += rng.nextInt(MIN_MUT, MAX_MUT);
				if (mutation.data[i] < min[(i - 1) % 5 + 1]) {
					mutation.data[i] = min[(i - 1) % 5 + 1];
					
				}
				if (mutation.data[i] > max[(i - 1) % 5 + 1]) {
					mutation.data[i] = max[(i - 1) % 5 + 1];
				}
			}
		}

		return mutation;

	}

	public static IntegerArraySolution crossover(IntegerArraySolution p1, IntegerArraySolution p2) {

		IntegerArraySolution cross = (IntegerArraySolution) p1.duplicate();
		IRNG rng = RNG.getRNG();

		for (int i = 0; i < cross.data.length; i++) {
			if (rng.nextDouble() > 0.5) {
				cross.data[i] = p2.data[i];
			}
		}

		return cross;
	}

	public static IntegerArraySolution onePointCrossover(IntegerArraySolution p1, IntegerArraySolution p2) {
		IntegerArraySolution cross = (IntegerArraySolution) p1.duplicate();

		IRNG rng = RNG.getRNG();

		int ind = rng.nextInt(1, cross.data.length - 1);

		for (int i = ind; i < cross.data.length; i++) {
			cross.data[i] = p2.data[i];
		}

		return cross;
	}

}
