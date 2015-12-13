package hr.fer.zemris.optjava.dz8;
import java.util.LinkedList;
import java.util.List;

public class EvaluatorElman implements IEvaluator{
	
	private ElmanNN elman;
	
	public EvaluatorElman(ElmanNN elman) {
		this.elman = elman;
	}

	@Override
	public double evaluateFitness(DoubleArraySolution sol) {
		int numOfSamples = elman.getDataset().numberOfSamples();
		List<double[]> outputs = new LinkedList<>();
		
		elman.setContextLayer(sol.solution);
		
		for (int i=0; i<numOfSamples; i++) {
			double[] output = new double[1];
			elman.calcOutputs(elman.getDataset().getInputs().get(i), sol.solution, output);
			outputs.add(output);
		}
		
		return elman.error(outputs);
	}

}
