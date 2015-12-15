
public class Test {

	public static void main(String[] args) {
		double alpha = 2;
		double s = 0.3;
		double share = 0.1;
		
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
		System.out.println(alg.run());
		
	}
}
