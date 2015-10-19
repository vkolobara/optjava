package hr.fer.zemris.optjava.dz3.decoder;

public interface IDecoder<T> {

	public double[] decode(T code);
	public void decode(T code, double[] field);
	
}
