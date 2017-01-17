package hr.fer.zemris.generic.ga;

import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.RNG;

public class IntegerArraySolution extends GASolution<int[]>{

	public IntegerArraySolution(int N) {
		data = new int[1+5*N];
	}
	
	/**
	 * 
	 * @param min [pozadinska boja, xkoord, ykoord, width, heigth, boja]
	 * @param max [pozadinska boja, xkoord, ykoord, width, heigth, boja]
	 */
	public void randomize(int[] min, int[] max) {
		IRNG rng = RNG.getRNG();
		
		data[0] = rng.nextInt(min[0], max[0]);
		
		for (int i=1; i<data.length; i++) {
			data[i] = rng.nextInt(min[(i-1)%5 + 1], max[(i-1)%5 + 1]);
		}
		
	}

	
	@Override
	public GASolution<int[]> duplicate() {
		IntegerArraySolution dupl = new IntegerArraySolution((data.length-1)/5);
		System.arraycopy(data, 0, dupl.data, 0, data.length);
		dupl.fitness = fitness;
		return dupl;
	}

}
