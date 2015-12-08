

public class SigmoidTransferFunction implements ITransferFunction{

	public SigmoidTransferFunction() {
	}
	
	@Override
	public double calcValue(double x) {
		return 2.0 / (1 + Math.exp(-x)) - 1;
	}
	
}
