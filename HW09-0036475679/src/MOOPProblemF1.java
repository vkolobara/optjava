
public class MOOPProblemF1 implements MOOPProblem{
	
	private int numberOfObjectives;
	
	public MOOPProblemF1(int numberOfObjectives) {
		this.numberOfObjectives = numberOfObjectives;
	}
	

	@Override
	public int getNumberOfObjectives() {
		return numberOfObjectives;
	}

	@Override
	public double[] evaluateSolution(double[] solution) {
		double[] objectives = new double[numberOfObjectives];
		
		for (int i=0; i<numberOfObjectives; i++) {
			objectives[i] = solution[i] * solution[i];
		}
		
		return objectives;
	}

}
