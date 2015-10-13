package hr.fer.zemris.function;

import org.apache.commons.math3.linear.RealVector;

public interface IFunction {

	public int getNumOfVariables();
	public double calculateValue(RealVector x);
	public RealVector calculateGradient(RealVector x);
	
}
