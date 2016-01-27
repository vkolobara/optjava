package hr.fer.zemris.optjava.gp.tree;

import hr.fer.zemris.optjava.gp.Eater;

public abstract class Primitive {
	
	protected String name;
	protected int numOfArgs;
	
	public Primitive(String name, int numOfArgs) {
		this.name = name;
		this.numOfArgs = numOfArgs;
	}
	
	public String getName() {
		return name;
	}
	
	public int getNumOfArgs() {
		return numOfArgs;
	}
	
	public abstract void execute(Eater eater, Node root);
	
	@Override
	public String toString() {
		return name;
	}
	
}
