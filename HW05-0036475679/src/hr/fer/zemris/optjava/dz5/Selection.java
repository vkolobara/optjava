package hr.fer.zemris.optjava.dz5;

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
	 * @return izabrano rješenje
	 */
	public T select(Object... args);

}
