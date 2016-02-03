package hr.fer.zemris.optjava.gp.tree.terminal;

import hr.fer.zemris.optjava.gp.Eater;
import hr.fer.zemris.optjava.gp.tree.Node;

public class Left extends Terminal{

	public Left() {
		super("left");
	}
	
	@Override
	public void execute(Eater eater, Node root) {
		if (!eater.isDone()) eater.left();
	}

}
