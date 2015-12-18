
public class MOOPProblemF2 implements MOOPProblem{

	
	private int numberOfObjectives;
	
	public MOOPProblemF2(int numberOfObjectives) {
		this.numberOfObjectives = numberOfObjectives;
	}
	

	@Override
	public int getNumberOfObjectives() {
		return numberOfObjectives;
	}

	@Override
	public double[] evaluateSolution(double[] solution) {
		double[] objectives = new double[numberOfObjectives];
		
		objectives[0] = solution[0];
		objectives[1] = (1 + solution[1]) / solution[0];
		
		
		return objectives;
	}

}
