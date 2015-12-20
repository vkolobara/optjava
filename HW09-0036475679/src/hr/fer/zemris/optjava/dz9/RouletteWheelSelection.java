package hr.fer.zemris.optjava.dz9;
import java.util.List;
import java.util.Random;

public class RouletteWheelSelection {

	private Random rand;
	
	public RouletteWheelSelection() {
		rand = new Random();
	}
	
	public DoubleArraySolution[] select(Population pop) {

		double sum = 0;
		
		for (DoubleArraySolution sol : pop.population) {
			sum+=sol.fitness;
		}
		
		List<DoubleArraySolution> sorted = pop.getSorted();

		Range[] ranges = new Range[pop.getSize()];
	
		double currLower = 0;

		for (int j=0; j<ranges.length; j++) {
			double fit = sorted.get(j).fitness;
			ranges[j] = new Range(currLower, currLower + 1.0 * fit / sum);
			currLower += 1.0 * fit / sum;
		}
				
		double p = rand.nextDouble();

		DoubleArraySolution parent1 = sorted.get(getParent(ranges, p));

		p = rand.nextDouble();
		DoubleArraySolution parent2 = sorted.get(getParent(ranges, p));

		
		return new DoubleArraySolution[] { parent1, parent2 };
	}
	
	private int getParent(Range[] ranges, double p) {
		int i = 0;
		for (Range range : ranges) {
			if (range.isInRange(p)) {
				break;
			}
			i++;
		}
		return i;
	}
	
}
