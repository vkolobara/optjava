package hr.fer.zemris.optjava.dz5.selection;

import java.util.Random;

import hr.fer.zemris.optjava.dz5.population.Population;
import hr.fer.zemris.optjava.dz5.solution.Solution;


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

	public RandomSelection(Population<? extends Solution> population) {
		this.population = new Population<>(population.getSize());
		this.population.population.addAll(population.getPopulation());
		rand = new Random();
	}
	
	@Override
	public Solution select(boolean best, Object... args) {
		return population.getPopulation().get(rand.nextInt(population.population.size()));
	}

}
