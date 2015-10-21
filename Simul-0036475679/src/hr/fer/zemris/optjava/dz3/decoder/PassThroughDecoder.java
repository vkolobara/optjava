package hr.fer.zemris.optjava.dz3.decoder;

import hr.fer.zemris.optjava.dz3.solution.DoubleArraySolution;

public class PassThroughDecoder implements IDecoder<DoubleArraySolution> {

	public PassThroughDecoder() {
		
	}
	
	@Override
	public double[] decode(DoubleArraySolution code) {
		return code.values;
	}

	@Override
	public void decode(DoubleArraySolution code, double[] field) {
		field = decode(code);
	}

}
