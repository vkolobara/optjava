package hr.fer.zemris.function;

import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

public interface IHFunction extends IFunction{
	
	public RealMatrix calculateHesse(RealVector x);
	

}
