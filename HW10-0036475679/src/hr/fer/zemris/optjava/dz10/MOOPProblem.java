package hr.fer.zemris.optjava.dz10;

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
	
	double[] getMins();
	
	double[] getMaxs();
}
