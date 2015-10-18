package hr.fer.zemris.optjava.dz2.algorithms;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealVector;

import hr.fer.zemris.optjava.function.IFunction;
import hr.fer.zemris.optjava.function.IHFunction;

public class NumOptAlgorithms {

	public final static double EPS = 10E-2;
	
	public static RealVector gradientDescent(IFunction function, int maxIters, RealVector x0, boolean printSteps){
		
		int numOfVariables = function.getNumOfVariables();
		RealVector solution = x0;
		
		int t=0;
		
		do {
			if (printSteps) {
				System.out.println(solution);
			}
			
			RealVector grad = function.calculateGradient(solution);
			int i=0;
			for ( ; i<numOfVariables; ++i) {
				if (Math.abs(grad.getEntry(i)) >= EPS) break; 
			}
			//ako je došao do kraja i nijedan nije bio veći od EPS
			//nađeno je rješenje
			if (i == numOfVariables) break;
			
			RealVector d = grad.mapMultiply(-1).unitVector();

			double lambda = bisection(function, solution, d);
			solution = solution.add(d.mapMultiply(lambda));
	
			t++;
		} while (t < maxIters);
		
		
		return solution;
		
	}
	
	public static RealVector newtonOpt(IHFunction function, int maxIters, RealVector x0, boolean printSteps) {
		int numOfVariables = function.getNumOfVariables();
		RealVector solution = x0;
		
		int t=0;
		
		do {
			if (printSteps) {
				System.out.println(solution);
			}

			RealVector grad = function.calculateGradient(solution);
			int i=0;
			for ( ; i<numOfVariables; ++i) {
				if (Math.abs(grad.getEntry(i)) >= EPS) break; 
			}
			//ako je došao do kraja i nijedan nije bio veći od EPS
			//nađeno je rješenje
			if (i == numOfVariables) break;
			
			RealVector d = MatrixUtils.inverse(function.calculateHesse(solution)).preMultiply(grad).mapMultiply(-1);
			
			double lambda = bisection(function, solution, d);
			solution = solution.add(d.mapMultiply(lambda));
			t++;
		} while (t < maxIters);
		
		return solution;
	}
	
	private static double bisection(IFunction function, RealVector x, RealVector d){
		
		double lowerBound = 0;
		double upperBound = 1; 
		
		double dTheta = -1;
		do {
			dTheta = function.calculateGradient(x.add(d.mapMultiply(upperBound))).dotProduct(d);
			upperBound *= 2;
		} while (dTheta < 0);
		
		double lambda;
		int t=0;
		do {
			lambda = (lowerBound + upperBound) / 2;	
			double deriv = function.calculateGradient(x.add(d.mapMultiply(lambda))).dotProduct(d);
			if (Math.abs(deriv) < EPS) {
				break;
			} else if (deriv < 0) {
				lowerBound = lambda;
			} else {
				upperBound = lambda;
			}
			
		} while (t++ < 100);
		
		return lambda;
		
	}
	
}
