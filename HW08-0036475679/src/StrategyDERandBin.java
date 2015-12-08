import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class StrategyDERandBin extends StrategyDE{

	private int n;
	
	public StrategyDERandBin(int n) {
		rand = new Random();
		this.n = n;
	}
	
	@Override
	public DoubleArraySolution mutation(double F, int i, Population pop) {
		int numOfX = 2*n+1;

		DoubleArraySolution[] X = new DoubleArraySolution[numOfX];
		
		Set<Integer> usedIndexes = new HashSet<>();
		usedIndexes.add(i);
		
		int j, size=pop.population.size();
		for (int k=0; k<numOfX; k++) {
			while (!usedIndexes.add(j=rand.nextInt(size)));
			X[k] = pop.population.get(j);
		}
		
		DoubleArraySolution v = X[0].duplicate();
		
		for (j=0; j<v.solution.length; j++) {
			for (int k=1; k<numOfX; k+=2) {
				v.solution[j] += F * (X[k].solution[j] - X[k+1].solution[j]);
			}
		}
		
		return v;
	} 

}
