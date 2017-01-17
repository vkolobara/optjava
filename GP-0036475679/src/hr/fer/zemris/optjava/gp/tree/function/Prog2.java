package hr.fer.zemris.optjava.gp.tree.function;

import hr.fer.zemris.optjava.gp.Eater;
import hr.fer.zemris.optjava.gp.tree.Node;

public class Prog2 extends Function {

	public Prog2() {
		super("Prog2", 2);
	}
	
	@Override
	public void execute(Eater eater, Node root) {
		if (eater.isDone()) return;
		root.getChildAt(0).execute(eater);
		if (eater.isDone()) return;
		root.getChildAt(1).execute(eater);		
	}

}
