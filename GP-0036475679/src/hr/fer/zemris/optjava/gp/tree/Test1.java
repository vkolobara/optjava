package hr.fer.zemris.optjava.gp.tree;

import java.io.IOException;
import java.util.Arrays;

import hr.fer.zemris.optjava.gp.AntMap;
import hr.fer.zemris.optjava.gp.Eater;
import hr.fer.zemris.optjava.gp.tree.function.IfFoodAhead;
import hr.fer.zemris.optjava.gp.tree.function.Prog2;
import hr.fer.zemris.optjava.gp.tree.function.Prog3;
import hr.fer.zemris.optjava.gp.tree.terminal.Left;
import hr.fer.zemris.optjava.gp.tree.terminal.Move;
import hr.fer.zemris.optjava.gp.tree.terminal.Right;
import hr.fer.zemris.optjava.gui.EaterGUI;

public class Test1 {

	public static void main(String[] args) throws IOException {
		double p = 0.9;
		int n = 7;
		int pop_size = 500;
		int max_iter = 100;
		AntMap map = new AntMap("trail.txt");
		Algorithm alg = new Algorithm(p, n, pop_size, max_iter, map);
		Eater e = alg.run();
		
	}
	
}
