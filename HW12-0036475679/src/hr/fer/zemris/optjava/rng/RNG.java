package hr.fer.zemris.optjava.rng;

public class RNG {
	private static IRNGProvider rngProvider;

	static {
		try {
			ClassLoader cl = Class.forName("hr.fer.zemris.optjava.rng.RNG").getClassLoader();
			rngProvider = (IRNGProvider) cl.loadClass("hr.fer.zemris.optjava.rng.ThreadBoundRNGProvider").newInstance();
		} catch (Exception e) {
			System.out.println("GREŠKA");
			e.printStackTrace();
		}

	}

	public static IRNG getRNG() {
		return rngProvider.getRNG();
	}
}