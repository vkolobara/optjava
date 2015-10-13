package hr.fer.zemris.trisat;

public class Clause {

	private int[] indexes;
	
	public Clause (int[] indexes) {
		this.indexes = new int[indexes.length];
		System.arraycopy(indexes, 0, this.indexes, 0, indexes.length);
	}
	
	public int getSize() {
		return indexes.length;
	}
	
	public int getLiteral(int index) {
		if (index < 0 || index > getSize()) {
			throw new IndexOutOfBoundsException("Indeks izvan granica.");
		}
	
		return indexes[index];
	}
	
	public boolean isSatisfied(BitVector assignment) {
		
		for (int index : indexes) {
			boolean assignmentValue = assignment.get(Math.abs(index)-1);
			
			assignmentValue = (index<0 ? !assignmentValue : assignmentValue); 
			
			if (assignmentValue == true) {
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		for (int index : indexes) {
			sb.append(index + " ");
		}
		System.out.println(sb);
		return sb.substring(0, sb.length());
	}
	
}
