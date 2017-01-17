package hr.fer.zemris.optjava.gp.tree.terminal;

import hr.fer.zemris.optjava.gp.Eater;
import hr.fer.zemris.optjava.gp.tree.Node;

public class Right extends Terminal{

	public Right() {
		super("right");
	}
	
	@Override
	public void execute(Eater eater, Node root) {
		if (!eater.isDone()) eater.right();
	}

}
