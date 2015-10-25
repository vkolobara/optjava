package hr.fer.zemris.optjava.dz3.schedule;

public class GeometricTempSchedule implements ITempSchedule {

	private double alpha;
	@SuppressWarnings("unused")
	private double tInitial;
	private double tCurrent;
	private int innerLimit;
	private int outerLimit;
	
	
	
	public GeometricTempSchedule(double alpha, double tInitial, int innerLimit, int outerLimit) {
		this.alpha = alpha;
		this.tInitial = tInitial;
		this.innerLimit = innerLimit;
		this.outerLimit = outerLimit;
		tCurrent = tInitial;
	}

	@Override
	public double getNextTemperature() {
		double ret = tCurrent;
		tCurrent = alpha * tCurrent;
		return ret;
	}

	@Override
	public int getInnerLoopCounter() {
		return innerLimit;
	}

	@Override
	public int getOuterLoopCounter() {
		return outerLimit;
	}

}
