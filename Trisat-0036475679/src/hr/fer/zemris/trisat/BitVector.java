package hr.fer.zemris.trisat;

import java.util.Random;

public class BitVector {
	
	protected final boolean[] bits;
	
	public BitVector(Random rand, int numberOfBits) {
		bits = new boolean [numberOfBits];
		for(int i=0; i<numberOfBits; i++) {
			bits[i] = rand.nextBoolean();
		}
	}
	
	public BitVector(boolean ... bits) {
		this.bits = new boolean[bits.length];
		System.arraycopy(bits, 0, this.bits, 0, bits.length);
	}
	
	public BitVector(int n) {
		bits = new boolean[n];
	}
	
	public boolean get(int index) {
		return bits[index];
	}
	
	public int getSize() {
		return bits.length;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (boolean bit : bits) {
			sb.append(boolToNum(bit));
		}
		
		return sb.toString();
	}
	
	public MutableBitVector copy() {
		return new MutableBitVector(bits);
	}
	
	/**
	 * 
	 * @param vector
	 * @return argument povećan za 1.
	 */
	public static MutableBitVector increment(BitVector vector) {
		MutableBitVector copy = vector.copy();
		
		int size = copy.getSize();
		
		for (int i=size-1; i>=0; i--) {
			copy.set(i, !copy.get(i));
			if (copy.get(i)) break;
		}
		return copy;
		
	}

	private String boolToNum(boolean bit) {
		return bit ? "1" : "0";
	}

}
