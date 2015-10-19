package hr.fer.zemris.optjava.dz3.solution;

public class SingleObjectiveSolution {

	public double fitness;
	public double value;
	protected int konj;

	public SingleObjectiveSolution() {
	}
	
	public int compareTo(SingleObjectiveSolution other) {
		return Double.compare(this.fitness, other.fitness);
	}
	
}
