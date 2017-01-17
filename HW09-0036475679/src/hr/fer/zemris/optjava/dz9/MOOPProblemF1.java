package hr.fer.zemris.optjava.dz9;

public class MOOPProblemF1 implements MOOPProblem{
	@Override
	public int getNumberOfObjectives() {
		return 4;
	}

	@Override
	public double[] evaluateSolution(double[] solution) {
		double[] objectives = new double[getNumberOfObjectives()];
		
		
		for (int i=0; i<objectives.length; i++) {
			objectives[i] = solution[i] * solution[i];
		}
		
		return objectives;
	}

}
