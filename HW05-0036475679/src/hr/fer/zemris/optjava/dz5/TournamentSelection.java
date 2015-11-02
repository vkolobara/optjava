package hr.fer.zemris.optjava.dz5;

import java.util.List;
import java.util.Random;

/**
 * Implementacija turnirske selekcije. Kao argument select metodi mora biti
 * predan k za k-turnir.
 * 
 * @author Vinko
 */

public class TournamentSelection implements Selection<Solution> {

	/**
	 * Turnirska populacija
	 */
	private Population<Solution> population;
	private Random rand;

	public TournamentSelection(Population<Solution> population) {
		this.population = new Population<>(population.getSize());
		this.population.population.addAll(population.getPopulation());
		rand = new Random();
	}

	/**
	 * Kao argument se očekuje <b>k</b>. Predstavlja broj jedinki između kojih
	 * će se održati turnir.
	 */
	@Override
	public Solution select(Object... args) {
		if (args == null || !(args[0] instanceof Integer)) {
			throw new IllegalArgumentException("Mora biti predan jedan argument: " + "k (Integer)");
		}

		Population<Solution> tournament = new Population<Solution>((int) args[0]);

		List<Solution> pop = population.getPopulation();
		int size = pop.size();

		while (!tournament.isFull()) {
			tournament.add(pop.get(rand.nextInt(size)));
		}

		return tournament.getBest();
	}

}
