package hr.fer.zemris.optjava.dz12.parser;

public class VariableNode extends Node {
	
	private String name;
	
	public VariableNode(String name) {
		this.name = name;
	}

	@Override
	public boolean getValue() {
		return Node.values.get(Parser.nazivLokacijaMapa.get(name));
	}
	
}
