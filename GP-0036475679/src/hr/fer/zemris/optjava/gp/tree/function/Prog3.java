package hr.fer.zemris.optjava.gp.tree.function;

import hr.fer.zemris.optjava.gp.Eater;
import hr.fer.zemris.optjava.gp.tree.Node;

public class Prog3 extends Function {

	public Prog3() {
		super("Prog3", 3);
	}
	
	@Override
	public void execute(Eater eater, Node root) {
		if (eater.isDone()) return;
		root.getChildAt(0).execute(eater);
		if (eater.isDone()) return;
		root.getChildAt(1).execute(eater);	
		if (eater.isDone()) return;
		root.getChildAt(2).execute(eater);		
	}

}
