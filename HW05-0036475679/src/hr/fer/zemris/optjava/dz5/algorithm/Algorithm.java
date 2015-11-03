package hr.fer.zemris.optjava.dz5.algorithm;

import hr.fer.zemris.optjava.dz5.solution.Solution;

/**
 * Sučelje za algoritme
 * @author Vinko
 *
 * @param <T>
 */
public interface Algorithm<T extends Solution> {
	
	/**
	 * Izvršava algoritam
	 * @return pronađeno rješenje
	 */
	public T run();
	
	
}
