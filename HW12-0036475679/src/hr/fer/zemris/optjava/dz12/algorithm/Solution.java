package hr.fer.zemris.optjava.dz12.algorithm;

import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.RNG;

public class Solution implements Comparable<Solution> {
	public int[] data;
	public double fitness;
	public int index;
	public static int numOfVariables, numOfCLBs, inputNum;

	public Solution() {
		int tableSize = (int) Math.pow(2, inputNum);
		data = new int[numOfCLBs * (tableSize + inputNum)];
	}

	public void randomize() {
		IRNG rng = RNG.getRNG();
		int size = (int) Math.pow(2, inputNum) + inputNum;
		for (int i = 0; i < data.length; i++) {
			if (i%size < inputNum) {
				data[i] = rng.nextInt(0, numOfVariables + i/size);
			} else {
				data[i] = rng.nextBoolean() ? 1 : 0;
			}
		}

	}

	public Solution duplicate() {
		Solution dupl = new Solution();
		System.arraycopy(data, 0, dupl.data, 0, data.length);
		dupl.fitness = fitness;
		dupl.index = index;
		return dupl;
	};

	@Override
	public int compareTo(Solution o) {
		int ret = Double.compare(this.fitness, o.fitness);
		if (ret == 0) ret = -Integer.compare(this.index, o.index);
		return ret;
	}
}
