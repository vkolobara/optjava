package hr.fer.zemris.optjava.dz12.parser;

public class Function2ArgNode extends Node {

	private Operator2Arg op;
	
	public Function2ArgNode(Operator2Arg op) {
		this.op = op;
	}
	
	@Override
	public boolean getValue() {
		return op.apply(left.getValue(), right.getValue());
	}

}
