package hr.fer.zemris.optjava.dz8;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

public class ANNTrainer {

	public static void main(String[] args) throws IOException {
		
		if (args.length != 5) {
			greska("Nema dovoljan broj argumenata");
		}
		
		try {
			String path = args[0];
			String net = args[1];
			int pop_size = Integer.parseInt(args[2]);
			double merr = Double.parseDouble(args[3]);
			int max_iter = Integer.parseInt(args[4]);
			
			double f = 0.2;
			double cr = 0.75;
			StrategyDEBin strategy = new StrategyDETargetToBestBin(2);

			TDNN tdnn = createNN(net, path);
			
			int sol_size = tdnn.getWeightsCount();

			IEvaluator evaluator;
			
			if (tdnn instanceof ElmanNN) {
				evaluator = new EvaluatorElman((ElmanNN) tdnn);
			} else {
				evaluator = new EvaluatorTDNN(tdnn);
			}
						
			AlgorithmDE alg = new AlgorithmDE(pop_size, sol_size, max_iter, merr, f, cr, strategy, evaluator);
			DoubleArraySolution sol = alg.run();
			
			IReadOnlyDataset dataset = tdnn.getDataset();
			
			for (int i=0; i<dataset.numberOfSamples(); i++) {
				double[] outputs = new double[1];
				tdnn.calcOutputs(dataset.getInputs().get(i), sol.solution, outputs);
				System.out.println("Očekivano: " + dataset.getOutputs().get(i)[0] + "; Dobiveno: " + outputs[0]);
			}
			
			System.out.println(sol + "\n" + "Greška: " + sol.fitness);

		} catch (Exception e) {
			greska(e.getMessage());
		}
		
	}


	private static TDNN createNN(String net, String path) throws IOException {
		String[] split = net.split("-");
		String[] arh = split[1].split("x");
		int[] layers = new int[arh.length];
		ITransferFunction[] layerFunctions = new ITransferFunction[arh.length-1];

		
		for (int i=0; i<arh.length; i++) {
			layers[i] = Integer.parseInt(arh[i]);
			layerFunctions[(i+1)%layerFunctions.length] = new SigmoidTransferFunction();
		}

		if (split[0].equals("tdnn")) {
			IReadOnlyDataset dataset = loadData(path, layers[0], 600);
			if (layers[layers.length-1] != 1) {
				greska("Mora biti samo 1 vrijednost na izlazu!");
			}
			return new TDNN(layers, layerFunctions, dataset);
		} else if (split[0].equals("elman")) {
			IReadOnlyDataset dataset = loadData(path, 600);
			if (layers[0] != 1 || layers[layers.length-1] != 1) {
				greska("Mora biti samo 1 ulaz i izlaz!");
			}
			return new ElmanNN(layers, layerFunctions, dataset);
		} else {
			greska("Nepodržana vrsta mreže!");
		}
		
		return null;
	}


	private static void greska(String string) {
		System.err.println(string);
		System.exit(0);
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
