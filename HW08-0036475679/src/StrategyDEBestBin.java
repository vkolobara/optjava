import java.util.HashSet;
import java.util.Set;

public class StrategyDEBestBin extends StrategyDEBin {

	private int n;
	
	public StrategyDEBestBin(int n) {
		super();
		this.n = n;
	}
	
	@Override
	public DoubleArraySolution mutation(double F, int i, Population pop) {
		
		int numOfX = 2*n;
		DoubleArraySolution v = pop.getBest();
		DoubleArraySolution[] x = new DoubleArraySolution[numOfX];
		
		Set<Integer> usedIndexes = new HashSet<>();
		usedIndexes.add(i);
		
		
		int j, size=pop.population.size();
		for (int k=0; k<numOfX; k++) {
			while (!usedIndexes.add(j=rand.nextInt(size)));
			x[k] = pop.population.get(j);
		}
				
		for (j=0; j<v.solution.length; j++) {
			for (int k=0; k<numOfX; k+=2) {
				v.solution[j] += F * (x[k].solution[j] - x[k+1].solution[j]);
			}
		}
		
		
		return v;
	}

}
