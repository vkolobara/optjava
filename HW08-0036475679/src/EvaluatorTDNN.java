import java.util.LinkedList;
import java.util.List;

public class EvaluatorTDNN implements IEvaluator{

	private TDNN tdnn;
	
	public EvaluatorTDNN(TDNN tdnn) {
		this.tdnn = tdnn;
	}
	
	@Override
	public double evaluateFitness(DoubleArraySolution sol) {
		int numOfSamples = tdnn.getDataset().numberOfSamples();
		List<double[]> outputs = new LinkedList<>();
		
		for (int i=0; i<numOfSamples; i++) {
			double[] output = new double[1];
			tdnn.calcOutputs(tdnn.getDataset().getInputs().get(i), sol.solution, output);
			outputs.add(output);
		}
		
		return tdnn.error(outputs);
	}

}
