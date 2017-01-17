package hr.fer.zemris.optjava.dz5.algorithm;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import hr.fer.zemris.optjava.dz5.GeneticOperators;
import hr.fer.zemris.optjava.dz5.population.Population;
import hr.fer.zemris.optjava.dz5.selection.RandomSelection;
import hr.fer.zemris.optjava.dz5.selection.TournamentSelection;
import hr.fer.zemris.optjava.dz5.solution.PermutationSolution;

public class AlgorithmOS implements Algorithm<PermutationSolution> {

	private double succRatio;
	private double maxSelPress;
	private double compFactor;

	public AlgorithmOS(double succRatio, double maxSelPress, double compFactor) {

		this.succRatio = succRatio;
		this.maxSelPress = maxSelPress;
		this.compFactor = compFactor;

	}

	@Override
	public PermutationSolution run(Population<PermutationSolution> population) {

		Population<PermutationSolution> pop = population;
		int velPop = pop.getSize();
		double succNum = velPop * succRatio;
		int t = 0;
		while (true) {

			TournamentSelection tournSel = new TournamentSelection(pop);
			RandomSelection randomSel = new RandomSelection(pop);
			Set<PermutationSolution> pool = new HashSet<>();
			int limit = (int) (maxSelPress * velPop);

			Population<PermutationSolution> newPop = new Population<>(velPop);

			int counter = 0;
			
			int i;
			for (i=0; counter < succNum && i < limit; i++) {
				PermutationSolution r1 = (PermutationSolution) tournSel.select(false, 3);
				PermutationSolution r2 = (PermutationSolution) randomSel.select(false);
				PermutationSolution child = GeneticOperators.permCrossover(r1, r2);
				child = GeneticOperators.mutate(child);
				child.setFitness();

				if (successfull(child, r1, r2)) {
					if (newPop.add(child)) {
						counter++;
					} else {
						pool.add(child);
					}
				} else {
					pool.add(child);
				}			
			}
						
			int j = 0;
			int poolSize = pool.size();
			List<PermutationSolution> poolList = new LinkedList<>(pool);
			while (!newPop.isFull() && j < poolSize) {
				newPop.add(poolList.get(j++));
			}

			if (!newPop.isFull())
				break;

			pop = newPop;

			if (i==limit) break;
			
			if (compFactor < 1) {
				compFactor = Math.max(Math.log(1.0 * t / (1000 * velPop)), 0);
			}

		}

		return pop.getWorst();

	}

	private boolean successfull(PermutationSolution child, PermutationSolution r1, PermutationSolution r2) {
		double smallerFit = Math.max(r1.fitness, r2.fitness);
		double biggerFit = Math.min(r1.fitness, r2.fitness);
		return child.fitness < smallerFit + compFactor * (biggerFit - smallerFit);
	}

}
