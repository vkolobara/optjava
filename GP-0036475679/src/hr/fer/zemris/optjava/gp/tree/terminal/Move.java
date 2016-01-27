package hr.fer.zemris.optjava.gp.tree.terminal;

import hr.fer.zemris.optjava.gp.Eater;
import hr.fer.zemris.optjava.gp.tree.Node;

public class Move extends Terminal{

	public Move() {
		super("move");
	}
	
	@Override
	public void execute(Eater eater, Node root) {
		eater.move();
	}

}
