package hr.fer.zemris.trisat;

public class SATFormulaStats {

	private final double PERCENTAGE_CONSTANT_UP = 0.01;
	private final double PERCENTAGE_CONSTANT_DOWN = 0.1;
	private final int PERCENTAGE_UNIT_AMOUNT = 50;
	
	private SATFormula formula;
	private double[] post;
	private int numberOfSatisfied = 0;
	private boolean satisfied = false;
	private double percentageBonus = 0;

	public SATFormulaStats(SATFormula formula) {
		this.formula = formula;
		post = new double[formula.getClauses().length];
		numberOfSatisfied = 0;
		satisfied = false;
		percentageBonus = 0;
	}

	public void setAssignment(BitVector assignment, boolean updatePercentages) {
		numberOfSatisfied = 0;
		int i = 0;
		
		if (updatePercentages) {
			for (Clause clause : formula.getClauses()) {
				if (clause.isSatisfied(assignment)) {
					numberOfSatisfied++;
					post[i] += (1 - post[i]) * PERCENTAGE_CONSTANT_UP;		
				} else {
					post[i] += (0 - post[i]) * PERCENTAGE_CONSTANT_DOWN;
				}
				i++;
			}
		} else {
			for (Clause clause : formula.getClauses()) {
				numberOfSatisfied += (clause.isSatisfied(assignment) ? 1 : 0);
			}
		}

		satisfied = numberOfSatisfied == i;
		
		i = 0;
		percentageBonus = 0;
		for (Clause clause : formula.getClauses()) {
			percentageBonus += (clause.isSatisfied(assignment) ? 1 : -1) * PERCENTAGE_UNIT_AMOUNT * (1 - post[i]);
			i++;
		}
	}
	

	public int getNumberOfSatisfied() {
		return numberOfSatisfied;
	}

	public boolean isSatisfied() {
		return satisfied;
	}

	public double getPercentageBonus() {
		return percentageBonus;
	}

	public double getPercentage(int index) {
		if (index < 0 || index >= post.length) {
			throw new IndexOutOfBoundsException("Indeks izvan granica.");
		}
		return post[index];
	}

}
