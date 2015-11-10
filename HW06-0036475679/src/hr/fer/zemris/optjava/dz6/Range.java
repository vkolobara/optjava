package hr.fer.zemris.optjava.dz6;

/**
 * Created by Vinko on 09.11.2015.
 */
public class Range {

    private double lower;
    private double upper;

    public Range(double lower, double upper) {
        this.lower = lower;
        this.upper = upper;
    }

    public boolean isInRange(double val) {
        return val <= upper && val >= lower;
    }
    
    @Override
    public String toString() {
    	return "(" + lower + ", " + upper + ")";
    }
}
