package hr.fer.zemris.optjava.rng;

public class ThreadBoundRNGProvider implements IRNGProvider {
	
	@Override
	public IRNG getRNG() {
		return ((EVOThread) Thread.currentThread()).getRNG();
	}

}
