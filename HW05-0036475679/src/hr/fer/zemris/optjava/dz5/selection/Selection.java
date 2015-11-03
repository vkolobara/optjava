package hr.fer.zemris.optjava.dz5.selection;

import hr.fer.zemris.optjava.dz5.solution.Solution;

/**
 * Sučelje koje svaka selekcija treba implementirati.
 * 
 * @author Vinko
 *
 * @param <T>
 */
public interface Selection<T extends Solution> {

	/**
	 * Vrši selekciju nad populacijom koja bi se trebala predati konstruktorom
	 * 
	 * @param args dodatni potrebni argumenti selekcije
	 * @param best ako je true, vraća najbolje rješenje, inače "najgore"
	 * @return izabrano rješenje
	 */
	public Solution select(boolean best, Object... args);

}
