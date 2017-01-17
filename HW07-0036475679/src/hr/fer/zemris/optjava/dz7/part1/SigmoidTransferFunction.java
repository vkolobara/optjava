package hr.fer.zemris.optjava.dz7.part1;

public class SigmoidTransferFunction implements ITransferFunction{

	public SigmoidTransferFunction() {
	}
	
	@Override
	public double calcValue(double x) {
		return 1 / (1 + Math.exp(-x));
	}
	
}
