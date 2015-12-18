import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Set;

public class Test {

	public static void main(String[] args) {
		zad2();
		
	}
	
	private static void ispisiUDatoteku(Population pop) throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter writer = new PrintWriter("dobrote.txt", "UTF-8");
		
		for (DoubleArraySolution sol : pop.population) {
			writer.println("(" + sol.objectives[0] + ", " + sol.objectives[1] + ")");
		}
		
		writer.close();
	}
	
	private static void zad1() {
		double alpha = 2;
		double s = 0.3;
		double share = 2;
		
		int size = 100;
		int max_iter = 1000;
		
		double[] max = new double[] {
				5, 5, 5, 5
		};
		
		double[] min = new double[] {
				-5, -5, -5, -5
		};
		
		int sol_size = 4;
		int obj_size = 4;
		
		MOOPProblem f1 = new MOOPProblemF1(obj_size);
		
		IDistanceCalc d = new IDistanceCalc() {
			
			@Override
			public double calcDistance(double[] sol1, double[] sol2) {
				double sum = 0;
				
				for (int i=0; i<sol1.length; i++) {
					sum += Math.pow(1.0 * (sol1[i] - sol2[i]) / (max[i] - min[i]), 2);
				}
				
				return Math.sqrt(sum);
			}
		};
		
		AlgorithmNSGA alg = new AlgorithmNSGA(max, min, alpha, share, s, size, sol_size, max_iter, f1, d);
		Population sol = alg.run();
		
		List<Set<Integer>> fronte = alg.calculateFronts(sol);
		
		int i=0;
		for (Set<Integer> fronta : fronte) {
			System.out.println("Fronta " + i++ + "; Broj rješenja = " + fronta.size());
		}
		
		try {
			ispisiUDatoteku(sol);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		
	}
	
	private static void zad2() {
		double alpha = 2;
		double s = 0.03;
		double share =  0.1;
		
		int size = 100;
		int max_iter = 500;
		
		double[] max = new double[] {
				1, 5
		};
		
		double[] min = new double[] {
				0.1, 0
		};
		
		int sol_size = 2;
		int obj_size = 2;
		
		MOOPProblem f2 = new MOOPProblemF2(obj_size);
		
		IDistanceCalc d = new IDistanceCalc() {
			
			@Override
			public double calcDistance(double[] sol1, double[] sol2) {
				double sum = 0;
				
				for (int i=0; i<sol1.length; i++) {
					sum += Math.pow(1.0 * (sol1[i] - sol2[i]) / (max[i] - min[i]), 2);
				}
				
				return Math.sqrt(sum);
			}
		};
		
		AlgorithmNSGA alg = new AlgorithmNSGA(max, min, alpha, share, s, size, sol_size, max_iter, f2, d);
		
		Population sol = alg.run();
				
		List<Set<Integer>> fronte = alg.calculateFronts(sol);
		
		int i=0;
		for (Set<Integer> fronta : fronte) {
			System.out.println("Fronta " + i++ + "; Broj rješenja = " + fronta.size());
		}
		
		
		try {
			ispisiUDatoteku(sol);
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
