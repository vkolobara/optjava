package hr.fer.zemris.optjava.dz9;
import java.util.Random;

public class GeneticOperators {

	private static final double ALPHA = 0.1;
	private static final double MUT_RATE = 0.02;
	
	
	private static Random rand = new Random();
	
	
	public static DoubleArraySolution crossoverBLX(DoubleArraySolution parent1, DoubleArraySolution parent2, double[] max, double[] min) {

		DoubleArraySolution child = parent1.newLikeThis();
		Random rand = new Random();

		for (int i=0; i<child.solution.length; i++) {
			double lower;
			double upper;
			double difference = Math.abs(parent2.solution[i] - parent1.solution[i]);

			if (parent1.solution[i] < parent2.solution[i]) {
				lower = parent1.solution[i] - ALPHA * difference;
				upper = parent2.solution[i] + ALPHA * difference;
			} else {
				lower = parent2.solution[i] - ALPHA * difference;
				upper = parent1.solution[i] + ALPHA * difference;
			}
			
			child.solution[i] = rand.nextDouble() * (upper-lower) + lower;
			if (child.solution[i] > max[i]) child.solution[i] = max[i];
			if (child.solution[i] < min[i]) child.solution[i] = min[i];
		}
		
		return child;
		
	}
	
	public static DoubleArraySolution mutation(DoubleArraySolution child, double s, double[] max, double[] min) {
		DoubleArraySolution mutation = child.duplicate();
		
		for (int i=0; i<mutation.solution.length; i++) {
			if (rand.nextDouble() <= MUT_RATE) {
				mutation.solution[i] += rand.nextGaussian()*s;
				if (mutation.solution[i] > max[i]) mutation.solution[i] = max[i];
				if (mutation.solution[i] < min[i]) mutation.solution[i] = min[i];
			}
		}
		
		return mutation;
	}
	
	
}
