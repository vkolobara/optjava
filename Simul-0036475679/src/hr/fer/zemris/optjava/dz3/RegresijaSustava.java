package hr.fer.zemris.optjava.dz3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

import hr.fer.zemris.optjava.dz3.decoder.GrayBinaryDecoder;
import hr.fer.zemris.optjava.dz3.decoder.IDecoder;
import hr.fer.zemris.optjava.dz3.decoder.PassThroughDecoder;
import hr.fer.zemris.optjava.dz3.function.IFunction;
import hr.fer.zemris.optjava.dz3.neighborhood.BinaryNeighborhood;
import hr.fer.zemris.optjava.dz3.neighborhood.DoubleArrayUnifNeighborhood;
import hr.fer.zemris.optjava.dz3.schedule.GeometricTempSchedule;
import hr.fer.zemris.optjava.dz3.schedule.ITempSchedule;
import hr.fer.zemris.optjava.dz3.solution.BitvectorSolution;
import hr.fer.zemris.optjava.dz3.solution.DoubleArraySolution;

public class RegresijaSustava {

	public static void main(String[] args) throws IOException {

		try {

			RealMatrix mat = readFile(args[0]);

			IFunction function = new IFunction() {

				private RealMatrix eq = mat;
				private int size = eq.getRowDimension();

				@Override
				public double valueAt(double[] point) {
					double val = 0;

					for (int i = 0; i < size; i++) {
						RealVector row = eq.getRowVector(i);
						val += Math.pow(calcFValue(row, point), 2);
					}

					return val;
				}
			};

			if (args[1].equals("decimal")) {

				IDecoder<DoubleArraySolution> decoder = new PassThroughDecoder();
				ITempSchedule schedule = new GeometricTempSchedule(0.95, 10000, 100, 1000);
				boolean minimize = true;
				double[] deltas = new double[] { 0.1, 0.1, 0.1, 0.1, 0.1, 0.1 };
				DoubleArrayUnifNeighborhood neighborhood = new DoubleArrayUnifNeighborhood(deltas);
				DoubleArraySolution startWith = new DoubleArraySolution(6);
				double[] mins = new double[] { -5, -5, -5, -5, -5, -5 };
				double[] maxs = new double[] { 5, 5, 5, 5, 5, 5 };
				startWith.randomize(new Random(), mins, maxs);

				SimulatedAnnealing<DoubleArraySolution> sim = new SimulatedAnnealing<DoubleArraySolution>(decoder,
						neighborhood, startWith, function, schedule, minimize);

				DoubleArraySolution sol = sim.run();
				System.out.println(sol + "; Greška: " + sol.fitness);

			} else if (args[1].startsWith("binary")) {
				int size = Integer.parseInt(args[1].split(":")[1]);
				double[] mins = new double[] { -20, -20, -20, -20, -20, -20 };
				double[] maxs = new double[] { 20, 20, 20, 20, 20, 20 };
				int[] bits = new int[] { size, size, size, size, size, size };
				int n = 6;
				IDecoder<BitvectorSolution> decoder = new GrayBinaryDecoder(mins, maxs, bits, n);
				ITempSchedule schedule = new GeometricTempSchedule(0.95, 10000, 100, 1000);
				boolean minimize = true;

				BinaryNeighborhood neighborhood = new BinaryNeighborhood(bits, n);
				BitvectorSolution startWith = new BitvectorSolution(6*size);
				startWith.randomize(new Random());

				SimulatedAnnealing<BitvectorSolution> sim = new SimulatedAnnealing<BitvectorSolution>(decoder,
						neighborhood, startWith, function, schedule, minimize);
				
				BitvectorSolution sol = sim.run();
				
				System.out.print(sol + "; Dekodirano: " + Arrays.toString(decoder.decode(sol)) + "; Greška: " + sol.fitness);
				
				
			} else {
				throw new Exception();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static double calcFValue(RealVector row, double[] x) {
		double val = 0;
		double a = x[0];
		double b = x[1];
		double c = x[2];
		double d = x[3];
		double e = x[4];
		double f = x[5];
		double x1 = row.getEntry(0);
		double x2 = row.getEntry(1);
		double x3 = row.getEntry(2);
		double x4 = row.getEntry(3);
		double x5 = row.getEntry(4);
		double y = row.getEntry(5);

		val = a * x1 + b * x1 * x1 * x1 * x2 + c * Math.exp(d * x3) * (1 + Math.cos(e * x4)) + f * x4 * x5 * x5 - y;
		return val;
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

}
