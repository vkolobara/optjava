package hr.fer.zemris.optjava.dz6;

/**
 * Apstraktni razred koji predstavlja jedno rješenje problema.
 * 
 * @author Vinko
 *
 */
public abstract class Solution {

	/**
	 * Dobrota rješenja
	 */
	public double fitness;

	/**
	 * @return duplikat rješenja
	 */
	public abstract Solution duplicate();

	/**
	 * @return rješenje iste "duljine" kao i trenutno
	 */
	public abstract Solution newLikeThis();

	/**
	 * 
	 * @param other
	 * @return usporedbu trenutnog rješenja s nekim drugim (other)
	 */
	public int compareTo(Solution other) {
		return Double.compare(this.fitness, other.fitness);
	}

	/**
	 * Metoda koja implementira način računanja dobrote i postavlja dobrotu
	 * trenutnog rješenja.
	 * 
	 */
	public abstract void setFitness();
	
	/**
	 * Kreira nasumično rješenje.
	 */
	public abstract void randomize();
	
	/**
	 * @return dužinu rješenja
	 */
	public abstract int getSize();
}
