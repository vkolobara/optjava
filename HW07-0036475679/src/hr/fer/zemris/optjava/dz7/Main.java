package hr.fer.zemris.optjava.dz7;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import hr.fer.zemris.optjava.dz7.part1.FFAN;
import hr.fer.zemris.optjava.dz7.part1.IReadOnlyDataset;
import hr.fer.zemris.optjava.dz7.part1.ITransferFunction;
import hr.fer.zemris.optjava.dz7.part1.PSOAlgorithm;
import hr.fer.zemris.optjava.dz7.part1.Particle;
import hr.fer.zemris.optjava.dz7.part1.SigmoidTransferFunction;
import hr.fer.zemris.optjava.dz7.part2.DoubleArraySolution;
import hr.fer.zemris.optjava.dz7.part2.ClonAlg;

public class Main {

	public static void main(String[] args) {

		try {
			IReadOnlyDataset dataset = loadData("07-iris-formatirano.data");

			FFAN ffan = new FFAN(new int[] { 4, 5, 3, 3 }, new ITransferFunction[] { new SigmoidTransferFunction(),
					new SigmoidTransferFunction(), new SigmoidTransferFunction() }, dataset);
			
			runClonAlg(ffan);

//			runPSO(ffan, 20);

			

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static void runPSO(FFAN ffan, int neighborSize) {
		double xMaxVal = 1;
		double xMinVal = -1;
		double vMaxVal = 2*Math.abs(xMaxVal - xMinVal);
		double vMinVal = -vMaxVal;
		int popSize = 60;
		int d = ffan.getWeightsCount();
		double c1 = 2;
		double c2 = 2;
		double wMax = 0.9;
		double wMin = 0.4;
		double merr = 0.03;
		int maxIter = 500;
		int wTMax = 500;
		IReadOnlyDataset dataset = ffan.getDataset();

		double[] xMax = new double[d];
		double[] xMin = new double[d];
		double[] vMax = new double[d];
		double[] vMin = new double[d];

		for (int i = 0; i < d; i++) {
			xMax[i] = xMaxVal;
			xMin[i] = xMinVal;
			vMax[i] = vMaxVal;
			vMin[i] = vMinVal;
		}

		PSOAlgorithm alg = new PSOAlgorithm(ffan, popSize, neighborSize, d, c1, c2, xMax, xMin, vMax, vMin, wMax,
				wMin, wTMax, merr, maxIter);

		Particle solution = alg.run();
		
		List<double[]> outputs = new LinkedList<>();
		for (double[] inputs : dataset.getInputs()) {
			double[] output = new double[3];
			ffan.calcOutputs(inputs, solution.x, output);
			outputs.add(output);
		}
		
		int size = dataset.numberOfSamples();
		for (int i = 0; i < size; i++) {
			System.out.println("Ulaz: " + Arrays.toString(dataset.getInputs().get(i)));
			System.out.println("Očekivani izlaz: " + Arrays.toString(dataset.getOutputs().get(i)));
			System.out.println("Dobiveni izlaz: " + pretvoriUBin(outputs.get(i)));
			System.out.println();
		}
		System.out.println(Arrays.toString(solution.x));
		System.out.println("Srednje kvadratno odstupanje: " + ffan.error(outputs));
	}

	private static String pretvoriUBin(double[] ds) {
		return "[" + (ds[0] >= 0.5 ? 1.0 : 0.0) + ", " + (ds[1] >= 0.5 ? 1.0 : 0.0) + ", " + (ds[2] >= 0.5 ? 1.0 : 0.0) + "]";
	}
	
	private static void runClonAlg(FFAN ffan) {
		
		double max = 100;
		double min = -100;
		int dim = ffan.getWeightsCount();
		int maxIter = 500;
		int N = 100;
		int d = 10;
		int n = 20;
		double beta = 1;
		double ro = 2.5;
		double sigma = 3;
		
		ClonAlg alg = new ClonAlg(max, min, dim, maxIter, N, d, n, beta, ro, ffan, sigma);
		DoubleArraySolution solution = alg.run();
		IReadOnlyDataset dataset = ffan.getDataset();
		
		List<double[]> outputs = new LinkedList<>();
		for (double[] inputs : dataset.getInputs()) {
			double[] output = new double[3];
			ffan.calcOutputs(inputs, solution.solution, output);
			outputs.add(output);
		}
		
		int size = dataset.numberOfSamples();
		for (int i = 0; i < size; i++) {
			System.out.println("Ulaz: " + Arrays.toString(dataset.getInputs().get(i)));
			System.out.println("Očekivani izlaz: " + Arrays.toString(dataset.getOutputs().get(i)));
			System.out.println("Dobiveni izlaz: " + pretvoriUBin(outputs.get(i)));
			System.out.println();
		}
		System.out.println(Arrays.toString(solution.solution));
		System.out.println("Srednje kvadratno odstupanje: " + ffan.error(outputs));
		
	}

	private static IReadOnlyDataset loadData(String path) throws IOException {
		List<double[]> inputs = new LinkedList<>(), outputs = new LinkedList<>();
		int numberOfSamples = 0;
		for (String row : Files.readAllLines(Paths.get(path))) {
			String[] inputOutput = row.replaceAll("[()]", "").split(":");
			String[] inputString = inputOutput[0].split(",");
			String[] outputString = inputOutput[1].split(",");

			double[] inputArray = new double[inputString.length];
			double[] outputArray = new double[outputString.length];

			for (int i = 0; i < inputString.length; i++) {
				inputArray[i] = Double.parseDouble(inputString[i]);
			}
			for (int i = 0; i < outputString.length; i++) {
				outputArray[i] = Double.parseDouble(outputString[i]);
			}

			inputs.add(inputArray);
			outputs.add(outputArray);
			numberOfSamples++;
		}

		return new IReadOnlyDataset(inputs, outputs, numberOfSamples);
	}
}
