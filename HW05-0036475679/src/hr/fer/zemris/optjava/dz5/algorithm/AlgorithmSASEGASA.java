package hr.fer.zemris.optjava.dz5.algorithm;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import hr.fer.zemris.optjava.dz5.population.Population;
import hr.fer.zemris.optjava.dz5.population.SASEGASAPopulation;
import hr.fer.zemris.optjava.dz5.solution.PermutationSolution;
import hr.fer.zemris.optjava.dz5.solution.Solution;

/**
 * Implementacija SASEGASA za Quadratic Assignment Problem.
 * 
 * @author Vinko
 *
 */
public class AlgorithmSASEGASA implements Algorithm<Solution> {

	private int solNumber;
	private int popNumber;
	private int n;

	/**
	 * 
	 * @param solNumber
	 *            broj jedinki
	 * @param popNumber
	 *            broj podpopulacija
	 */
	public AlgorithmSASEGASA(int solNumber, int popNumber, int n) {
		if (solNumber < popNumber) {
			throw new IllegalArgumentException("Broj rješenja mora biti veći ili jednak broju populacija!");
		}

		this.solNumber = solNumber;
		this.popNumber = popNumber;
		this.n = n;
	}

	@Override
	public Solution run() {

		SASEGASAPopulation pop = new SASEGASAPopulation(popNumber);

		int size = solNumber / popNumber;
		int rem = solNumber % popNumber;
		while (!pop.isFull()) {
			pop.addPopulation(generateRandomPop(size));
		}

		if (rem != 0) {
			pop.remove(pop.getSize()-1);
			pop.addPopulation(generateRandomPop(rem));
		}

		while (pop.getSize() > 1) {
			try {
				parallelize(pop);
				pop.mergePopulations();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			

		}

		return pop.populations.get(0).getBest();

	}

	/**
	 * Izvodi paralelno algoritam Offspring selection za svaku podpopulaciju
	 * @param pop vršna populacija
	 * @throws InterruptedException 
	 */
	private void parallelize(SASEGASAPopulation pop) throws InterruptedException {

		ExecutorService executor = Executors.newFixedThreadPool(pop.getSize());
	    for (final Population<PermutationSolution> popul : pop.populations) {
	      Runnable worker = new Runnable() {
			@Override
			public void run() {
				AlgorithmOS alg = new AlgorithmOS(popul, 0.4, 5, 0.5);
				alg.run();
			}
		};
	      executor.execute(worker);
	    }
	    executor.shutdown();
	    executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		
	}

	/**
	 * Generira nasumičnu populaciju veličine <i>size</i>
	 * @param size veličina populacije koja se generira
	 * @return novonastalu populaciju
	 */
	private Population<PermutationSolution> generateRandomPop(int size) {
		Population<PermutationSolution> pop = new Population<>(size);

		while (!pop.isFull()) {
			PermutationSolution sol = new PermutationSolution(n);
			sol.randomize();
			pop.add(sol);
		}

		return pop;

	}

}
