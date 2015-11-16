package hr.fer.zemris.optjava.dz7;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

public class Main {

	
	public static void main(String[] args) {
		
		try {
			IReadOnlyDataset dataset = loadData("07-iris-formatirano.data");
			
			FFAN ffan = new FFAN(new int[] {
					4, 3, 3
			}, new ITransferFunction[] {
					new SigmoidTransferFunction(),
					new SigmoidTransferFunction(),
					new SigmoidTransferFunction()
			}, dataset);
			
			List<double[]> outputs = new LinkedList<>();
			double[] weights = new double[27];
			for (int i=0; i<27; i++) {
				weights[i] = -0.2;
			}
			
			dataset.getInputs().forEach(input -> {
				double[] vals = new double[3];
				ffan.calcOutputs(input, weights, vals);
				outputs.add(vals);
			});
			
			System.out.println(ffan.error(outputs));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private static IReadOnlyDataset loadData(String path) throws IOException {		
		List<double[]> inputs=new LinkedList<>(), outputs = new LinkedList<>();
		int numberOfSamples = 0;;
		for (String row : Files.readAllLines(Paths.get(path))) {
			String[] inputOutput = row.replaceAll("[()]", "").split(":");
			String[] inputString = inputOutput[0].split(",");
			String[] outputString = inputOutput[1].split(",");

			double[] inputArray = new double[inputString.length];
			double[] outputArray = new double[outputString.length];
			
			
			for (int i=0; i<inputString.length; i++) {
				inputArray[i] = Double.parseDouble(inputString[i]);
			}
			for (int i=0; i<outputString.length; i++) {
				outputArray[i] = Double.parseDouble(outputString[i]);
			}
		
			inputs.add(inputArray);
			outputs.add(outputArray);
			numberOfSamples++;
		}
		
		return new IReadOnlyDataset(inputs, outputs, numberOfSamples);
	}
}
