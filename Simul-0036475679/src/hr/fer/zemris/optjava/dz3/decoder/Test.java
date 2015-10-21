package hr.fer.zemris.optjava.dz3.decoder;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

import hr.fer.zemris.optjava.dz3.SimulatedAnnealing;
import hr.fer.zemris.optjava.dz3.function.IFunction;
import hr.fer.zemris.optjava.dz3.neighborhood.DoubleArrayUnifNeighborhood;
import hr.fer.zemris.optjava.dz3.schedule.GeometricTempSchedule;
import hr.fer.zemris.optjava.dz3.solution.BitvectorSolution;
import hr.fer.zemris.optjava.dz3.solution.DoubleArraySolution;

public class Test {

	public static void main(String[] args) throws IOException {
		
		RealMatrix mat = readFile("zad-prijenosna.txt");
		
		IFunction function = new IFunction() {
			
			private RealMatrix eq = mat;
			private int size = eq.getRowDimension();
			
			@Override
			public double valueAt(double[] point) {
				double val = 0;
				
				for (int i=0; i<size; i++) {
					RealVector row = eq.getRowVector(i);
					val += Math.pow(calcFValue(row, point), 2);
				}

				return val;			}
		};
		double[] deltas = new double[]{
				0.2, 0.2, 0.2, 0.2, 0.2, 0.2
		};
		double[] values = new double[]{
				-5, 2, 4, 9, 11, 17
		};
		Random rand = new Random();
		
		PassThroughDecoder decoder = new PassThroughDecoder();
		DoubleArrayUnifNeighborhood neighborhood = new DoubleArrayUnifNeighborhood(deltas);
		
		DoubleArraySolution startWith = new DoubleArraySolution(6);
		startWith.randomize(rand, values, deltas);
		
		GeometricTempSchedule schedule = new GeometricTempSchedule(0.8, 200, 100000, 1000);
		boolean minimize = true;
		
		SimulatedAnnealing<DoubleArraySolution> sim = new SimulatedAnnealing<DoubleArraySolution>(decoder, neighborhood, startWith, function, schedule, minimize);
		System.out.println(sim.run());
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
		
		val = a*x1 + b*x1*x1*x1*x2 + c*Math.exp(d*x3) * (1+Math.cos(e*x4)) + f*x4*x5*x5 - y;
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
