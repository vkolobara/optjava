package hr.fer.zemris.optjava.dz12.parser;

public class Function1ArgNode extends Node {
	private Operator1Arg op;

	public Function1ArgNode(Operator1Arg op) {
		this.op = op;
	}

	@Override
	public boolean getValue() {
		return op.apply(left.getValue());
	}
}
