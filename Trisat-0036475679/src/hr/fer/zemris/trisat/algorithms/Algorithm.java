package hr.fer.zemris.trisat.algorithms;

import hr.fer.zemris.trisat.BitVector;

/**
 * Sučelje za pojedini algoritam
 * @author Vinko
 *
 */
public interface Algorithm {
	
	/**
	 * 
	 * @param assignment
	 * @return dobrotu BitVector-a zadanog argumentom
	 */
	public double fitness(BitVector assignment);
	
	/**
	 * Izvršava algoritam
	 * @return najbolje rješenje
	 */
	public BitVector execute();

}
