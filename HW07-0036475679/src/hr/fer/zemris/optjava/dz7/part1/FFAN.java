package hr.fer.zemris.optjava.dz7.part1;

import java.util.List;

public class FFAN {

	private int weightsCount;
	private int[] layers;
	private ITransferFunction[] layerFunctions;
	private IReadOnlyDataset dataset;

	/**
	 * 
	 * @param layers
	 *            opis slojeva neuronske mreže
	 * @param layerFunctions
	 *            prijenosne funkcije za pojedini sloj
	 * @param dataset
	 *            podatci za učenje
	 */
	public FFAN(int[] layers, ITransferFunction[] layerFunctions, IReadOnlyDataset dataset) {
		this.dataset = dataset;
		this.layers = new int[layers.length];
		System.arraycopy(layers, 0, this.layers, 0, layers.length);
		this.layerFunctions = new ITransferFunction[layerFunctions.length];
		System.arraycopy(layerFunctions, 0, this.layerFunctions, 0, layerFunctions.length);
		for (int i = 1; i < layers.length; i++) {
			weightsCount += layers[i] * layers[i - 1] + layers[i];
		}
	}

	public int getWeightsCount() {
		return weightsCount;
	}

	public void calcOutputs(double[] inputs, double[] weights, double[] outputs) {
		double[] prevNet = new double[inputs.length];
		System.arraycopy(inputs, 0, prevNet, 0, inputs.length);
		
		int weightIndex = 0;
		
		for (int i = 1; i < layers.length; i++) {
			double[] net = new double[layers[i]];

			for (int j=0; j<prevNet.length; j++) {
				for (int k=0; k<net.length; k++) {
					net[k] += prevNet[j] * weights[weightIndex++];
				}
			}
			for (int k=0; k<net.length; k++) {
				net[k] += weights[weightIndex++];
				net[k] = layerFunctions[i-1].calcValue(net[k]);
			}
			
			prevNet = net;
		}

		System.arraycopy(prevNet, 0, outputs, 0, outputs.length);
	}
	
	public IReadOnlyDataset getDataset() {
		return dataset;
	}
	
	/**
	 * 
	 * @param outputs izračunati izlazi
	 * @return vraća srednje kvadratno odstupanje od stvarnih izlaza.
	 */
	public double error(List<double[]> outputs) {
		int n = dataset.numberOfSamples();
		double sum=0;
		
		for (int i=0; i<n; i++) {
			double[] t = dataset.getOutputs().get(i);
			double[] s = outputs.get(i);
			
			for (int j=0; j<t.length; j++) {
				sum += Math.pow(t[j]-s[j], 2);
			}
		}
		
		return sum / n;
	}

}
