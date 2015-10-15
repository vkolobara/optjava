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

public class Sustav {

	public static void main(String[] args) {

		try {
			RealMatrix mat = readFile("zad-sustav.txt");

			gradientDescent(mat);
//			newtonOpt(mat);
			
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
		int numOfVariables = equation.getColumnDimension()-1;

		double[] vector = new double[numOfVariables];

		for (int i = 0; i < numOfVariables; i++) {
			vector[i] = rand.nextDouble() * 20 - 10;
		}

		RealVector sol = NumOptAlgorithms.gradientDescent(f, 10000, new ArrayRealVector(vector));
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
		
		RealVector sol = NumOptAlgorithms.newtonOpt(f, 10000, new ArrayRealVector(vector));
		System.out.println(f.calculateValue(sol));

		return sol;
	}
	
	
	
	private static IHFunction newtonFunction(RealMatrix equation) {
		IHFunction f = new IHFunction() {

			private RealMatrix eq = equation;
			private int size = eq.getRowDimension();
			private int numOfVariables = eq.getColumnDimension() - 1;

			@Override
			public int getNumOfVariables() {
				return numOfVariables;
			}

			@Override
			public double calculateValue(RealVector x) {
				double val = 0;
				RealVector sol = x.copy();
				sol = sol.append(-1);

				for (int i = 0; i < size; i++) {
					RealVector row = eq.getRowVector(i);
					val += Math.pow(row.dotProduct(sol), 2);
				}

				return val;
			}

			@Override
			public RealVector calculateGradient(RealVector x) {
				RealVector sol = x.copy();
				sol = sol.append(-1);

				RealVector grad = new ArrayRealVector(numOfVariables);

				for (int i = 0; i < size; i++) {
					RealVector row = eq.getRowVector(i);

					double sum = row.dotProduct(sol);
					for (int k = 0; k < numOfVariables; k++) {
						grad.setEntry(k, grad.getEntry(k) + 2 * sum * row.getEntry(k));
					}

				}

				return grad;
			}

			@Override
			public RealMatrix calculateHesse(RealVector x) {
								
				RealMatrix hesse = new Array2DRowRealMatrix(numOfVariables, numOfVariables);
				
				for (int i = 0; i < size; i++) {
					RealVector col = eq.getColumnVector(i);
					
					for (int j=0; j<size; j++) {
						double sum = 0;
						for (int k=0; k<size; k++) {
							sum += 2 * col.getEntry(k) * eq.getRowVector(k).getEntry(j);
						}
						hesse.setEntry(i, j, sum);
					}
					

				}				
				return hesse;
			}
			
		};
		return f;
	}

	private static IFunction gradientFunction(RealMatrix equation) {
		IFunction f = new IFunction() {

			private RealMatrix eq = equation;
			private int size = eq.getRowDimension();
			private int numOfVariables = eq.getColumnDimension() - 1;

			@Override
			public int getNumOfVariables() {
				return numOfVariables;
			}

			@Override
			public double calculateValue(RealVector x) {
				double val = 0;
				RealVector sol = x.copy();
				sol = sol.append(-1);

				for (int i = 0; i < size; i++) {
					RealVector row = eq.getRowVector(i);
					val += Math.pow(row.dotProduct(sol), 2);
				}

				return val;
			}

			@Override
			public RealVector calculateGradient(RealVector x) {
				RealVector sol = x.copy();
				sol = sol.append(-1);

				RealVector grad = new ArrayRealVector(numOfVariables);

				for (int i = 0; i < size; i++) {
					RealVector row = eq.getRowVector(i);

					double sum = row.dotProduct(sol);

					for (int k = 0; k < numOfVariables; k++) {
						grad.setEntry(k, grad.getEntry(k) + 2 * sum * row.getEntry(k));
					}

				}

				return grad;
			}
		};
		return f;
	}

}
