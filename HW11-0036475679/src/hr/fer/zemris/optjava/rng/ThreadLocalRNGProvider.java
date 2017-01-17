package hr.fer.zemris.optjava.rng;

public class ThreadLocalRNGProvider implements IRNGProvider {

	private ThreadLocal<IRNG> threadLocal = null;
	
	@Override
	public IRNG getRNG() {
		if (threadLocal == null) {
			IRNG rng = new RNGRandomImpl();
			threadLocal = new ThreadLocal<>();
			threadLocal.set(rng);
		}
		return threadLocal.get();
	}

}
