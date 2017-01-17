package hr.fer.zemris.optjava.dz8;

public class ElmanNN extends TDNN{

	private int contextSize;
	private double[] contextLayer;
	
	public ElmanNN(int[] layers, ITransferFunction[] layerFunctions, IReadOnlyDataset dataset) {
		super(layers, layerFunctions, dataset);
		contextSize = layers[1];
		weightsCount += contextSize*contextSize;
		contextLayer = new double[contextSize];
	}
	
	public int getContextSize() {
		return contextSize;
	}
	
	public void setContextLayer(double[] weights) {
		System.arraycopy(weights, weights.length-contextSize, contextLayer, 0, contextSize);
	}
	
	@Override
	public void calcOutputs(double[] inputs, double[] weights, double[] outputs) {
		double[] prevNet = new double[inputs.length + contextSize];
		System.arraycopy(inputs, 0, prevNet, 0, inputs.length);
		System.arraycopy(contextLayer, 0, prevNet, inputs.length, contextSize);
		calcNetWeight(weights, outputs, prevNet);
	}
	
	@Override
	protected void calcNetWeight(double[] weights, double[] outputs, double[] prevNet) {
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
			if (i==1) System.arraycopy(net, 0, contextLayer, 0, contextSize);
		}

		System.arraycopy(prevNet, 0, outputs, 0, outputs.length);	}

}
