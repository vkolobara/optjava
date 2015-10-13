package hr.fer.zemris.trisat;

import java.util.Iterator;

public class BitVectorNGenerator implements Iterable<MutableBitVector> {

	private BitVector assignment;
	
	public BitVectorNGenerator(BitVector assignment) {
		this.assignment = assignment;
	}
	
	@Override
	public Iterator<MutableBitVector> iterator() {
		Iterator<MutableBitVector> it = new Iterator<MutableBitVector>() {
			
			private int currIndex = 0;

			@Override
			public boolean hasNext() {
				return currIndex < assignment.getSize();
			}

			@Override
			public MutableBitVector next() {
				if (!hasNext()) {
					throw new IllegalArgumentException("Nema sljedećeg susjeda!");
				}
				
				MutableBitVector next = assignment.copy();
				next.set(currIndex, !assignment.get(currIndex));
				currIndex++;
				
				return next;
			}
			
		};
		return it;
	}
	
	public MutableBitVector[] createNeighborhood() {
		MutableBitVector[] neighborhood = new MutableBitVector[assignment.getSize()];
		
		int i=0;
		for (MutableBitVector neighbor : this) {
			neighborhood[i++] = neighbor;
		}
		
		return neighborhood;
	}

}
