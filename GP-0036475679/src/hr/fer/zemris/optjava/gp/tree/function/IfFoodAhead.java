package hr.fer.zemris.optjava.gp.tree.function;

import hr.fer.zemris.optjava.gp.Eater;
import hr.fer.zemris.optjava.gp.tree.Node;

public class IfFoodAhead extends Function{

	public IfFoodAhead() {
		super("IfFoodAhead", 2);
	}
	
	@Override
	public void execute(Eater eater, Node root) {
		if (eater.isDone()) return;
		if (eater.isFoodAhead()) {
			root.getChildAt(0).execute(eater);
		} else {
			root.getChildAt(1).execute(eater);
		}
	}

}
