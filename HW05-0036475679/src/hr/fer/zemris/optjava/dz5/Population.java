package hr.fer.zemris.optjava.dz5;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Predstavlja jednu populaciju rješenja.
 * @author Vinko
 *
 * @param <T>
 */
public class Population<T extends Solution> {
	
	/**
	 * Populacija
	 */
	Set<T> population;
	
	/**
	 * Veličina populacije
	 */
	private int size;
	
	public Population(int size) {
		this.population = new HashSet<>();
		this.size = size;
	}
	
	/**
	 * Dodaje rješenje u populaciju ako je moguće.
	 * Nije moguće u slučaju: 
	 * <ul>
	 * 	<li>Populacija je dosegla traženu veličinu</li>
	 * 	<li>Već postoji takvo rješenje u populaciji</li>
	 * </ul>
	 * @param solution rješenje koje treba dodati
	 * @return true ako je rješenje dodano, false inače.
	 */
	public boolean add(T solution) {
		if (population.size() == size) {
			return false;
		}
		return population.add(solution);	
	}
	
	/**
	 * 
	 * @return true ako je populacija puna, false inače.
	 */
	public boolean isFull() {
		return population.size() == size;
	}
	
	/**
	 * 
	 * @return najbolje rješenje u populaciji
	 */
	public T getBest() {
		
		T best = null;
		double bestFit = -Double.MAX_VALUE;
		
		for (T sol : population) {
			if (sol.fitness > bestFit) {
				best = sol;
				bestFit = sol.fitness;
			}
		}
		return best;
		
	}
	
	public List<T> getPopulation() {
		return new LinkedList<>(population);
	}
	
	public int getSize() {
		return size;
	}
	
	

}
