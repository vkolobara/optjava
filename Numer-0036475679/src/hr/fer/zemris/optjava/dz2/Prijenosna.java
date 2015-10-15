package hr.fer.zemris.optjava.dz2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

import hr.fer.zemris.algorithms.NumOptAlgorithms;
import hr.fer.zemris.function.IFunction;
import hr.fer.zemris.function.IHFunction;

public class Prijenosna {

	public static void main(String[] args) {
		
		try {
			RealMatrix mat = readFile("zad-prijenosna.txt");

			newtonOpt(mat);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static RealMatrix readFile(String path) throws IOException {
		List<String> rows = Files.readAllLines(Paths.get(path));

		Iterator<String> it = rows.iterator();

		while (it.hasNext()) {
			if (it.next().startsWith("#"))
				it.remove();
		}

		double[][] vector = new double[rows.size()][];

		int j = 0;
		for (String row : rows) {
			String[] rowSplit = row.substring(1, row.length() - 1).split(", ");

			double[] red = new double[rowSplit.length];
			int i = 0;
			for (String s : rowSplit) {
				red[i++] = Double.parseDouble(s);
			}
			vector[j++] = red;
		}

		return new Array2DRowRealMatrix(vector);

	}

	private static RealVector gradientDescent(RealMatrix equation) {
		
		IFunction f = gradientFunction(equation);
		Random rand = new Random();
		int numOfVariables = f.getNumOfVariables();

		double[] vector = new double[numOfVariables];

		for (int i = 0; i < numOfVariables; i++) {
			vector[i] = rand.nextDouble() * 10 - 5;
		}

		RealVector sol = NumOptAlgorithms.gradientDescent(f, 100, new ArrayRealVector(vector));
		System.out.println(f.calculateValue(sol));
		return sol;
		
	}
	
	private static RealVector newtonOpt(RealMatrix equation) {
		IHFunction f = newtonFunction(equation);

		Random rand = new Random();
		int numOfVariables = f.getNumOfVariables();

		double[] vector = new double[numOfVariables];

		for (int i = 0; i < numOfVariables; i++) {
			vector[i] = rand.nextDouble() * 20 - 10;
		}
		
		RealVector sol = NumOptAlgorithms.newtonOpt(f, 100000, new ArrayRealVector(vector));
		System.out.println(f.calculateValue(sol));

		return sol;
	}

	private static IFunction gradientFunction(RealMatrix equation) {
		IFunction f = new IFunction() {
			
			private RealMatrix eq = equation;
			private int size = eq.getRowDimension();
			private int numOfVariables = 6;
			
			@Override
			public int getNumOfVariables() {
				return numOfVariables;
			}
			
			@Override
			public double calculateValue(RealVector x) {
				double val = 0;
				
				for (int i=0; i<size; i++) {
					RealVector row = eq.getRowVector(i);
					val += Math.pow(calcFValue(row, x), 2);
				}

				return val;
			}
			

			@Override
			public RealVector calculateGradient(RealVector x) {

				RealVector grad = new ArrayRealVector(numOfVariables);
				for (int i=0; i<size; i++) {
					RealVector row = equation.getRowVector(i);
					
					double sum = calcFValue(row, x);
					for (int j=0; j<numOfVariables; j++) {
						grad.addToEntry(j, 2*sum*gradValue(j, row, x));
					}
				}
				return grad;
			}
		};
		return f;
	}
	
	private static IHFunction newtonFunction(RealMatrix equation) {
		IHFunction f = new IHFunction() {

			private RealMatrix eq = equation;
			private int size = eq.getRowDimension();
			private int numOfVariables = 6;
			
			@Override
			public int getNumOfVariables() {
				return numOfVariables;
			}
			
			@Override
			public double calculateValue(RealVector x) {
				double val = 0;
				
				for (int i=0; i<size; i++) {
					RealVector row = eq.getRowVector(i);
					val += Math.pow(calcFValue(row, x), 2);
				}

				return val;
			}
			

			@Override
			public RealVector calculateGradient(RealVector x) {

				RealVector grad = new ArrayRealVector(numOfVariables);
				for (int i=0; i<size; i++) {
					RealVector row = equation.getRowVector(i);
					
					double sum = calcFValue(row, x);
					for (int j=0; j<numOfVariables; j++) {
						grad.addToEntry(j, 2*sum*gradValue(j, row, x));
					}
					
				}
				return grad;
			}

			@Override
			public RealMatrix calculateHesse(RealVector x) {
								
				RealMatrix hesse = new Array2DRowRealMatrix(numOfVariables, numOfVariables);
				
				for (int i = 0; i < size; i++) {
					RealVector row = eq.getRowVector(i);
					for (int k=0; k<numOfVariables; k++) {
						double val = gradValue(k, row, x);
						for (int j=0; j<numOfVariables; j++) {
							hesse.addToEntry(k, j, 2 * gradValue(j, row, x) * val);
						}
					}
					
				}			
				return hesse;
			}
			
		};
		return f;
	}
	
	/**
	 * 
	 * @param row
	 * @param x
	 * @return vrijednost funkcije za određene parametre (bez kvadrata)
	 */
	private static double calcFValue(RealVector row, RealVector x) {
		double val = 0;
		double a = x.getEntry(0);
		double b = x.getEntry(1);
		double c = x.getEntry(2);
		double d = x.getEntry(3);
		double e = x.getEntry(4);
		double f = x.getEntry(5);
		double x1 = row.getEntry(0);
		double x2 = row.getEntry(1);
		double x3 = row.getEntry(2);
		double x4 = row.getEntry(3);
		double x5 = row.getEntry(4);
		double y = row.getEntry(5);
		
		val = a*x1 + b*x1*x1*x1*x2 + c*Math.exp(d*x3) * (1+Math.cos(e*x4)) + f*x4*x5*x5 - y;
		return val;
	}

	/**
	 * 
	 * @param index
	 * @param row
	 * @param x
	 * @return vraća parcijalnu derivaciju po index-toj varijabli (samo unutrašnji dio)
	 */
	private static double gradValue(int index, RealVector row, RealVector x) {
		double a = x.getEntry(0);
		double b = x.getEntry(1);
		double c = x.getEntry(2);
		double d = x.getEntry(3);
		double e = x.getEntry(4);
		double f = x.getEntry(5);
		double x1 = row.getEntry(0);
		double x2 = row.getEntry(1);
		double x3 = row.getEntry(2);
		double x4 = row.getEntry(3);
		double x5 = row.getEntry(4);
		double y = row.getEntry(5);
		double val = 0;
		
		switch(index) {
		// dy/da
		case 0:
			val = x1;
			break;
		// dy/db
		case 1:
			val = Math.pow(x1, 3)*x2;
			break;
		// dy/dc
		case 2:
			val = Math.exp(d*x3)*(1+Math.cos(e*x4));
			break;
		// dy/dd
		case 3:
			val = c*x3*Math.exp(d*x3)*(1+Math.cos(e*x4));
			break;
		// dy/de
		case 4:
			val = -1*c*x4*Math.exp(d*x3)*Math.sin(e*x4);
			break;
		// dy/df
		case 5:
			val = x4*Math.pow(x5, 2);
			break;
		default:
		}
		return val;
	}

}
