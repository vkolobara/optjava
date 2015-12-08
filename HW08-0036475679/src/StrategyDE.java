import java.util.Random;

public abstract class StrategyDE {

	protected Random rand;
	
	
	
	public abstract DoubleArraySolution mutation(double F, int i, Population pop);

	public DoubleArraySolution crossover(DoubleArraySolution x, DoubleArraySolution v, double cr) {
		
		DoubleArraySolution u = v.newLikeThis();
		
		for (int i=0; i < u.solution.length; i++) {
			u.solution[i] = (rand.nextDouble() <= cr) ? v.solution[i] : x.solution[i];
		}
		
		int j = rand.nextInt(u.solution.length);
		
		u.solution[j] = v.solution[j];
		
		
		return u;
	}

	

	
	
}
