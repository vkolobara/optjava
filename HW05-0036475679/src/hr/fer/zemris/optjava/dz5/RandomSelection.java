package hr.fer.zemris.optjava.dz5;

import java.util.Random;


/**
 * Implementacija selekcije koja predstavlja nasumičnu selekciju.
 * @author Vinko
 *
 */
public class RandomSelection implements Selection<Solution> {

	/**
	 * Populacija za selekciju
	 */
	private Population<Solution> population;
	private Random rand;

	public RandomSelection(Population<Solution> population) {
		this.population = new Population<>(population.getSize());
		this.population.population.addAll(population.getPopulation());
		rand = new Random();
	}
	
	@Override
	public Solution select(Object... args) {
		return population.getPopulation().get(rand.nextInt(population.getSize()));
	}

}
