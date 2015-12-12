

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ANNTrainer {

	public static void main(String[] args) throws IOException {
		IReadOnlyDataset dataset = loadData("A.dat", 8, 600);
		TDNN tdnn = new TDNN(new int[] {8,  10,  1}, 
							 new ITransferFunction[] { new SigmoidTransferFunction(), new SigmoidTransferFunction() }
							 , dataset);
		int pop_size = 20;
		int sol_size = tdnn.getWeightsCount();
		int max_iter = 200;
		double merr = 0.01;
		double f = 0.2;
		double cr = 0.75;
		StrategyDEBin strategy = new StrategyDETargetToBestBin(2);
		IEvaluator evaluator = new EvaluatorTDNN(tdnn);
		
		System.out.println(dataset.numberOfSamples() + " " + tdnn.getWeightsCount());
		
		AlgorithmDE alg = new AlgorithmDE(pop_size, sol_size, max_iter, merr, f, cr, strategy, evaluator);
		DoubleArraySolution sol = alg.run();
		System.out.println(sol);
		
		for (int i=0; i<dataset.numberOfSamples(); i++) {
			double[] outputs = new double[1];
			tdnn.calcOutputs(dataset.getInputs().get(i), sol.solution, outputs);
			System.out.println("Očekivano: " + dataset.getOutputs().get(i)[0] + "; Dobiveno: " + outputs[0]);
		}
	}


	private static IReadOnlyDataset loadData(String path, int l, int n) throws IOException {
		List<double[]> inputs = new LinkedList<>(), outputs = new LinkedList<>();
		int numberOfSamples = 0;
		
		double[] rows = normalize(path);

		if (n > rows.length) {
			throw new IllegalArgumentException("Nema toliko mjerenja!");
		}
		
		if (n==-1) n=rows.length;
		
		if (n < 0 ) {
			throw new IllegalArgumentException("n mora biti veći od 0");
		}
		 
		for (int i=0; i<n-l; i++) {
			double[] input = new double[l];
			
			for (int j=0; j<l; j++) {
				input[j] = rows[i+j];
			}
			
			inputs.add(input);
			outputs.add(new double[] { rows[i+l] });
			
			numberOfSamples++;
		}
		

		
		return new IReadOnlyDataset(inputs, outputs, numberOfSamples);
	}
	
	private static double[] normalize(String path) throws IOException {
		
		List<String> rows = Files.readAllLines(Paths.get(path));
		
		double[] normalized = new double[rows.size()];
		
		double min = Double.MAX_VALUE, max = -Double.MAX_VALUE;
		
		for (int i=0; i<normalized.length; i++) {
			normalized[i] = Double.parseDouble(rows.get(i).trim());
			if (normalized[i] < min) min = normalized[i];
			if (normalized[i] > max) max = normalized[i];
		}
		
		for (int i=0; i<normalized.length; i++) {
			normalized[i] = 2.0*(normalized[i] - min) / (max - min) - 1;
		}
		System.out.println(Arrays.toString(normalized));
		return normalized;
	}


	private static IReadOnlyDataset loadData(String path, int n) throws IOException {
		List<double[]> inputs = new LinkedList<>(), outputs = new LinkedList<>();
		int numberOfSamples = 0;
		
		double[] rows = normalize(path);
		
		if (n > rows.length) {
			throw new IllegalArgumentException("Nema toliko mjerenja!");
		}
		
		if (n==-1) n=rows.length;
		
		if (n < 0 ) {
			throw new IllegalArgumentException("n mora biti veći od 0");
		}
		 
		for (int i=0; i<n-1; i++) {
			double[] input = new double[1];
			input[0] = rows[i];
			
			
			inputs.add(input);
			outputs.add(new double[] { rows[i+1] });
			
			numberOfSamples++;
		}
		
		return new IReadOnlyDataset(inputs, outputs, numberOfSamples);
	}
}
