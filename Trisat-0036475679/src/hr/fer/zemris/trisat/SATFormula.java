package hr.fer.zemris.trisat;

public class SATFormula {
	
	private int numberOfVariables;
	private Clause[] clauses;
	
	public SATFormula(int numberOfVariables, Clause[] clauses) {
		this.numberOfVariables = numberOfVariables;
		this.clauses = new Clause[clauses.length];
		System.arraycopy(clauses, 0, this.clauses, 0, clauses.length);
	}
	
	public int getNumberOfVariables() {
		return numberOfVariables;
	}
	
	public Clause[] getClauses() {
		return clauses;
	}
	
	public boolean isSatisfied(BitVector assignment) {
				
		for (Clause clause : clauses) {
			if (!clause.isSatisfied(assignment)) return false;
		}
		
		return true;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		for (Clause clause : clauses) {
			sb.append("(" + clause + ")");
		}
		
		return sb.toString();
	}
}
