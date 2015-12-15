import java.util.List;
import java.util.Random;

public class RouletteWheelSelection {

	private Random rand;
	
	public RouletteWheelSelection() {
		rand = new Random();
	}
	
	public DoubleArraySolution[] select(Population pop) {

		int i=2;
		int sum = 0;
		int size = pop.getSize();
		int[] fit = new int[size];
		for (int j=1; j<=size;j++) {
			fit[size-j] = j+i;
			sum+=j + i;
			i += j;
		}
		
		List<DoubleArraySolution> sorted = pop.getSorted();

		Range[] ranges = new Range[pop.getSize()];
	
		i = 0;
		double currLower = 0;

		i=0;
		for (int j=0; j<ranges.length; j++) {
			ranges[i++] = new Range(currLower, currLower + 1.0 * fit[j] / sum);
			currLower += 1.0 * fit[j] / sum;
		}
		
		i=0;
		
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
