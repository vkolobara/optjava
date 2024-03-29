package hr.fer.zemris.optjava.dz9;

public interface MOOPProblem {
	/**
	 * 
	 * @return broj definiranih kriterija
	 */
	int getNumberOfObjectives();
	
	/**
	 * 
	 * @param solution trenutno rješenje
	 * @return vrijednosti kriterija
	 */
	double[] evaluateSolution(double[] solution);
}
