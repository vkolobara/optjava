package hr.fer.zemris.optjava.dz7;

import java.util.LinkedList;
import java.util.List;

public class IReadOnlyDataset {


	private List<double[]> inputs;
	private List<double[]> outputs;
	private int numberOfSamples;
	
	
	public IReadOnlyDataset(List<double[]> inputs, List<double[]> outputs, int numberOfSamples) {
		this.inputs = new LinkedList<>(inputs);
		this.outputs = new LinkedList<>(outputs);
		this.numberOfSamples = numberOfSamples;
	}
	
	public int numberOfSamples() {
		return numberOfSamples;
	}
	
	public List<double[]> getInputs() {
		return new LinkedList<>(inputs);
	}
	
	public List<double[]> getOutputs() {
		return new LinkedList<>(outputs);
	}

	
}
