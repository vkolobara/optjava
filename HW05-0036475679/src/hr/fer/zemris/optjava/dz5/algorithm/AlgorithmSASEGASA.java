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

	private double succRatio;
	private double compFactor;
	private double maxSelPress;

	/**
	 * 
	 * @param solNumber
	 *            broj rješenja
	 * @param popNumber
	 *            broj podpopulacija
	 * @param n
	 *            duljina rješenja
	 * @param succRatio
	 *            faktor uspješnosti
	 * @param compFactor
	 *            faktor usporedbe
	 * @param maxSelPress
	 *            maksimalni selekcijski pritisak
	 */
	public AlgorithmSASEGASA(int solNumber, int popNumber, int n, double succRatio, double compFactor,
			double maxSelPress) {
		if (solNumber < popNumber) {
			throw new IllegalArgumentException("Broj rješenja mora biti veći ili jednak broju populacija!");
		}

		this.solNumber = solNumber;
		this.popNumber = popNumber;
		this.n = n;
		this.succRatio = succRatio;
		this.compFactor = compFactor;
		this.maxSelPress = maxSelPress;
	}

	@Override
	public Solution run(Population<Solution> population) {

		SASEGASAPopulation pop = (SASEGASAPopulation) population;

		while (pop.getSize() > 1) {
			try {
				parallelize(pop);
				pop.mergePopulations();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		AlgorithmOS alg = new AlgorithmOS(succRatio, maxSelPress, compFactor);

		return alg.run(pop.populations.get(0));

	}

	/**
	 * Izvodi paralelno algoritam Offspring selection za svaku podpopulaciju
	 * 
	 * @param pop
	 *            vršna populacija
	 * @throws InterruptedException
	 */
	private void parallelize(SASEGASAPopulation pop) throws InterruptedException {

		ExecutorService executor = Executors.newFixedThreadPool(pop.getSize());
		for (final Population<PermutationSolution> popul : pop.populations) {
			Runnable worker = new Runnable() {
				@Override
				public void run() {
					AlgorithmOS alg = new AlgorithmOS(succRatio, maxSelPress, compFactor);
					alg.run(popul);
				}
			};
			executor.execute(worker);
		}
		executor.shutdown();
		executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

	}

}
