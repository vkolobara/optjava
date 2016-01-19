package hr.fer.zemris.optjava.dz12.parser;

public class VariableNode extends Node {
	
	private String name;
	private boolean value;
	
	
	public VariableNode(String name) {
		this.name = name;
	}

	@Override
	public boolean getValue() {
		return Parser.values[Parser.nazivLokacijaMapa.get(name)];
	}
	
	public String getName() {
		return name;
	}

}
