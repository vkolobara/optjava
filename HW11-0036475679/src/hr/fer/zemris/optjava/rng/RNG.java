package hr.fer.zemris.optjava.rng;

import java.io.InputStream;
import java.util.Properties;

public class RNG {
	private static IRNGProvider rngProvider;

	static {
		try {
		
		Properties properties = new Properties();
		ClassLoader cl = Class.forName("hr.fer.zemris.optjava.rng.RNG").getClassLoader();
		InputStream is = cl.getResourceAsStream("rng-config.properties");
		properties.load(is);
		String naziv = properties.getProperty("rng-provider");
		rngProvider = (IRNGProvider) cl.loadClass(naziv).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}

	public static IRNG getRNG() {
		return rngProvider.getRNG();
	}
}