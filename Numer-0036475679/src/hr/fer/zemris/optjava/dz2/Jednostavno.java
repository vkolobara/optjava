package hr.fer.zemris.optjava.dz2;

import java.util.Random;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

import hr.fer.zemris.optjava.dz2.algorithms.NumOptAlgorithms;
import hr.fer.zemris.optjava.function.IFunction;
import hr.fer.zemris.optjava.function.IHFunction;

public class Jednostavno {

	
	public static void main(String[] args) {
	
		if (args.length != 2 && args.length !=4) {
			throw new IllegalArgumentException("Potrebna 2 ili 4 argumenta!");
		}
		
		double[] startPoint = null;
		
		if (args.length == 4) {
			 startPoint = new double[2];
			try {
				startPoint[0] = Double.parseDouble(args[2]);
				startPoint[1] = Double.parseDouble(args[3]);
				chooseAlgTask(args[0], Integer.parseInt(args[1]), startPoint);
			} catch (Exception e) {
				throw new IllegalArgumentException("Pogrešni argumenti!");
			}
		} else {
			try {
				chooseAlgTask(args[0], Integer.parseInt(args[1]), startPoint);
			}  catch (Exception e) {
				throw new IllegalArgumentException("Pogrešni argumenti!");
			}
		}
		
	
	}
	
	
	private static void chooseAlgTask(String alg, int maxIter, double[] startPoint) {
		switch(alg) {
		case "1a":
			prviA(startPoint, maxIter);
			break;
		case "2a":
			drugiA(startPoint, maxIter);
			break;
		case "1b": 
			prviB(startPoint, maxIter);
			break;
		case "2b":
			drugiB(startPoint, maxIter);
			break;
		default:
			throw new IllegalArgumentException("Error");
		}
		
	}


	public static void prviA(double[] startPoint, int maxIter) {
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

		if (startPoint != null) {
			vector = startPoint;
		} else {
			for (int i=0; i<numOfVariables; i++) {
				vector[i] = rand.nextDouble() * 10 - 5;
			}
		}
	
		NumOptAlgorithms.gradientDescent(function, maxIter, new ArrayRealVector(vector), true);
	}
	
	public static void prviB(double[] startPoint, int maxIter) {
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

		if (startPoint != null) {
			vector = startPoint;
		} else {
			for (int i=0; i<numOfVariables; i++) {
				vector[i] = rand.nextDouble() * 10 - 5;
			}
		}
	
		
		NumOptAlgorithms.newtonOpt(function, maxIter, new ArrayRealVector(vector), true);
	}
	
	public static void drugiA(double[] startPoint, int maxIter) {
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

		if (startPoint != null) {
			vector = startPoint;
		} else {
			for (int i=0; i<numOfVariables; i++) {
				vector[i] = rand.nextDouble() * 10 - 5;
			}
		}
	
		
		NumOptAlgorithms.gradientDescent(function, maxIter, new ArrayRealVector(vector), true);
	}
	
	public static void drugiB(double[] startPoint, int maxIter) {
		IHFunction function = new IHFunction() {
			
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

			@Override
			public RealMatrix calculateHesse(RealVector x) {
				return new Array2DRowRealMatrix(new double[][] {
					{2, 0},
					{0, 20}
				});
			}
		};
		
		Random rand = new Random();
		int numOfVariables = function.getNumOfVariables();
		
		double[] vector = new double[numOfVariables];

		if (startPoint != null) {
			vector = startPoint;
		} else {
			for (int i=0; i<numOfVariables; i++) {
				vector[i] = rand.nextDouble() * 10 - 5;
			}
		}
	
		NumOptAlgorithms.newtonOpt(function, maxIter, new ArrayRealVector(vector), true);
	}
	
	
	
}
