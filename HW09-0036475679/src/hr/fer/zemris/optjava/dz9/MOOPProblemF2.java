package hr.fer.zemris.optjava.dz9;

public class MOOPProblemF2 implements MOOPProblem{


	

	@Override
	public int getNumberOfObjectives() {
		return 2;
	}

	@Override
	public double[] evaluateSolution(double[] solution) {
		double[] objectives = new double[getNumberOfObjectives()];
		
		objectives[0] = solution[0];
		objectives[1] = (1 + solution[1]) / solution[0];
		
		
		return objectives;
	}

}
