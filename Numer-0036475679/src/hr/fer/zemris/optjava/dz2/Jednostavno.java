package hr.fer.zemris.optjava.dz2;

import java.util.Random;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

import hr.fer.zemris.algorithms.NumOptAlgorithms;
import hr.fer.zemris.function.IFunction;
import hr.fer.zemris.function.IHFunction;

public class Jednostavno {

	
	public static void main(String[] args) {
		prviA();
		System.out.println();
		prviB();
		System.out.println();
		drugiA();
		System.out.println();
	}
	
	
	public static void prviA() {
		IFunction function = new IFunction() {
			
			@Override
			public int getNumOfVariables() {
				return 2;
			}
			
			@Override
			public double calculateValue(RealVector x) {
				return Math.pow(x.getEntry(0), 2) + Math.pow(x.getEntry(1) - 1, 2);
			}
			
			@Override
			public RealVector calculateGradient(RealVector x) {
				double[] vector = new double[]{
						2 * x.getEntry(0),
						2 * (x.getEntry(1) - 1)
				};
				return new ArrayRealVector(vector);
			}
		};
		
		Random rand = new Random();
		int numOfVariables = function.getNumOfVariables();
		
		double[] vector = new double[numOfVariables];
				
		for (int i=0; i<numOfVariables; i++) {
			vector[i] = rand.nextDouble() * 10 + -5;
		}
		
		NumOptAlgorithms.gradientDescent(function, 100, new ArrayRealVector(vector));
	}
	
	public static void prviB() {
		IHFunction function = new IHFunction() {
			
			@Override
			public int getNumOfVariables() {
				return 2;
			}
			
			@Override
			public double calculateValue(RealVector x) {
				return Math.pow(x.getEntry(0), 2) + Math.pow(x.getEntry(1) - 1, 2);
			}
			
			@Override
			public RealVector calculateGradient(RealVector x) {
				double[] vector = new double[]{
						2 * x.getEntry(0),
						2 * (x.getEntry(1) - 1)
				};
				return new ArrayRealVector(vector);
			}

			@Override
			public RealMatrix calculateHesse(RealVector x) {
				return new Array2DRowRealMatrix(new double[][] {
					{2, 0},
					{0, 2}
				});
			}
		};
		
		Random rand = new Random();
		int numOfVariables = function.getNumOfVariables();
		
		double[] vector = new double[numOfVariables];
				
		for (int i=0; i<numOfVariables; i++) {
			vector[i] = rand.nextDouble() * 10 + -5;
		}
		
		NumOptAlgorithms.gradientDescent(function, 100, new ArrayRealVector(vector));
	}
	
	public static void drugiA() {
		IFunction function = new IFunction() {
			
			@Override
			public int getNumOfVariables() {
				return 2;
			}
			
			@Override
			public double calculateValue(RealVector x) {
				return Math.pow(x.getEntry(0) - 1, 2) + 10 * Math.pow(x.getEntry(1) - 2, 2);
			}
			
			@Override
			public RealVector calculateGradient(RealVector x) {
				return new ArrayRealVector(new double[] {
						2 * (x.getEntry(0) - 1),
						20 * (x.getEntry(1) - 2)
				});
			}
		};
		
		Random rand = new Random();
		int numOfVariables = function.getNumOfVariables();
		
		double[] vector = new double[numOfVariables];
				
		for (int i=0; i<numOfVariables; i++) {
			vector[i] = rand.nextDouble() * 10 + -5;
		}
		
		NumOptAlgorithms.gradientDescent(function, 100, new ArrayRealVector(vector));
	}
	
	
	
}
