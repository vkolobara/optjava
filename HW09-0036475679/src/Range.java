

public class Range {
	
	private double lower;
	private double upper;
	
	public Range(double lower, double upper) {
		this.lower = lower;
		this.upper = upper;
	}
	
	public boolean isInRange(double value) {
		return value >= lower && value <= upper;
	}
	
	@Override
	public String toString() {
		return "(" + lower + ", " + upper + ")";
	}

}
