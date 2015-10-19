package hr.fer.zemris.optjava.dz3.decoder;

import java.util.stream.IntStream;

import hr.fer.zemris.optjava.dz3.solution.BitvectorSolution;

public abstract class BitvectorDecoder implements IDecoder<BitvectorSolution>{

	protected double[] mins;
	protected double[] maxs;
	protected int[] bits;
	protected int n;
	protected int totalBits;
	
	public BitvectorDecoder(double[] mins, double[] maxs, int[] bits, int n) {
		this.mins = new double[mins.length];
		System.arraycopy(mins, 0, this.mins, 0, mins.length);
		this.maxs = new double[maxs.length];
		System.arraycopy(maxs, 0, this.maxs, 0, maxs.length);
		this.bits = new int[bits.length];
		System.arraycopy(bits, 0, this.bits, 0, bits.length);
		this.n = n;
		totalBits = IntStream.of(bits).sum();
	}
	
	public BitvectorDecoder(double min, double max, int bit, int n) {
		this.mins = new double[]{min};
		this.maxs = new double[]{max};
		this.bits = new int[]{bit};
		this.n = n;
		totalBits = bit;
	}
	
	public int getTotalBits() {
		return totalBits;
	}
	
	public int getDimensions() {
		return n;
	}
	
}
