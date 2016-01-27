package hr.fer.zemris.optjava.gp.tree;

import java.io.IOException;
import java.util.Arrays;

import hr.fer.zemris.optjava.gp.AntMap;
import hr.fer.zemris.optjava.gp.tree.function.IfFoodAhead;
import hr.fer.zemris.optjava.gp.tree.function.Prog2;
import hr.fer.zemris.optjava.gp.tree.function.Prog3;
import hr.fer.zemris.optjava.gp.tree.terminal.Left;
import hr.fer.zemris.optjava.gp.tree.terminal.Move;
import hr.fer.zemris.optjava.gp.tree.terminal.Right;

public class Test1 {

	public static void main(String[] args) throws IOException {

				
		for (int i=0; i<100; i++) {
			Tree tree = new Tree();
			tree.addPrimitive(new IfFoodAhead());
			tree.addPrimitive(new Prog2());
			tree.addPrimitive(new Prog3());
			tree.addPrimitive(new Left());
			tree.addPrimitive(new Move());
			tree.addPrimitive(new Right());
			tree.buildTreeRampedHalfAndHalf(6, 0);
			
			System.out.println(tree);
		}
		 
		
		AntMap map = new AntMap("trail.txt");
		boolean[][] f = map.getFoodMap();
		
		for (int i=0; i<map.getHeight(); i++) {
			System.out.println(Arrays.toString(f[i]));
		}
		
	}
	
}
