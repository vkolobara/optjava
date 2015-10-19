package hr.fer.zemris.optjava.dz3.decoder;

import java.util.Arrays;

import hr.fer.zemris.optjava.dz3.solution.BitvectorSolution;

public class GrayBinaryDecoder extends BitvectorDecoder {

	public GrayBinaryDecoder(double[] mins, double[] maxs, int[] bits, int n) {
		super(mins, maxs, bits, n);
	}

	public GrayBinaryDecoder(double min, double max, int bit, int n) {
		super(min, max, bit, n);
	}

	@Override
	public double[] decode(BitvectorSolution code) {
		double[] decoded = new double[n];
		int currPos = 0;
		for (int i = 0; i < n; i++) {
			decoded[i] = decodeGray(Arrays.copyOfRange(code.bits, currPos, currPos + bits[i]));
			currPos = currPos + bits[i];
		}

		return decoded;
	}

	@Override
	public void decode(BitvectorSolution code, double[] field) {
		field = decode(code);
	}

	private double decodeGray(boolean[] code) {
		boolean[] decoded = new boolean[code.length];

		decoded[0] = code[0];

		for (int i = 1; i < code.length; i++) {
			decoded[i] = code[i] | decoded[i - 1];
		}

		long value = 0;

		for (boolean b : decoded) {
			value = (value << 1) | (b ? 1 : 0);
		}

		return value;
	}

}
