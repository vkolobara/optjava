package hr.fer.zemris.optjava.dz10;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Set;

public class MOOP {

	public static void main(String[] args) {
		
		if (args.length != 3) {
			greska("Nedovoljno argumenata!");
		}
		try {
			double s = 0.1;

			int size = 200;
			int max_iter = 200;

			double[] max;
			double[] min;
			int sol_size;

			MOOPProblem f;

			if (args[0].equals("1")) {
				max = new double[] { 5, 5, 5, 5 };
				min = new double[] { -5, -5, -5, -5 };
				sol_size = 4;
				f = new MOOPProblemF1();
			} else {
				max = new double[] { 1, 5 };
				min = new double[] { 0.1, 0 };
				sol_size = 2;
				f = new MOOPProblemF2();
			}

			size = Integer.parseInt(args[1]);
			
			max_iter = Integer.parseInt(args[2]);
			
			AlgorithmNSGAII alg = new AlgorithmNSGAII(max, min, s, size, sol_size, max_iter, f);
			Population sol = alg.run();

			List<Set<Integer>> fronte = alg.calculateFronts(sol);

			int i = 0;
			for (Set<Integer> fronta : fronte) {
				System.out.println("Fronta " + i++ + "; Broj rješenja = " + fronta.size());
			}
			ispisiUDatoteku(sol);
		} catch (Exception e) {
//			greska(e.getMessage());
			e.printStackTrace();
		}
		
		
	}

	private static void greska(String string) {
		System.err.println(string);
		System.exit(0);
	}

	private static void ispisiUDatoteku(Population pop) throws FileNotFoundException, UnsupportedEncodingException {
		
		System.out.println("Započinjem ispis u datoteke!");
		PrintWriter izlaz_dec = new PrintWriter("izlaz-dec.txt", "UTF-8");
		PrintWriter izlaz_obj = new PrintWriter("izlaz-obj.txt", "UTF-8");

		for (DoubleArraySolution sol : pop.population) {
			printArray(izlaz_dec, sol.solution);
			printArray(izlaz_obj, sol.objectives);
		}
		izlaz_dec.close();
		izlaz_obj.close();
		System.out.println("Datoteke stvorene.");
	}

	private static void printArray(PrintWriter izlaz, double[] sol) {
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		for (double val : sol) {
			sb.append(val + ",");
		}
		sb.deleteCharAt(sb.length()-1);
		sb.append(")");
		
		izlaz.println(sb.toString());
	}

}
