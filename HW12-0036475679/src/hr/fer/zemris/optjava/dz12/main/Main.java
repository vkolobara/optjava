package hr.fer.zemris.optjava.dz12.main;

import java.util.Arrays;

import hr.fer.zemris.optjava.dz12.algorithm.CLBAlgorithm;
import hr.fer.zemris.optjava.dz12.algorithm.Solution;
import hr.fer.zemris.optjava.dz12.parser.Node;
import hr.fer.zemris.optjava.dz12.parser.Parser;

public class Main {

	public static void main(String[] args) {

		int n = 2;
		int numOfClb = 6;
		//MUX 2 
		String expression = "((NOT S) AND A) OR (B AND S)";
		//MUX 4 
//		String expression = "(A AND (NOT S0) AND NOT(S1)) OR (B AND S0 AND (NOT S1)) OR (C AND (NOT S0) AND S1) OR (D AND S0 AND S1)";
		Parser parser = new Parser(expression, numOfClb);
		Node root = parser.parseAndCalculateTruth();

		
		
		int max_iter = 1000;

		Solution.inputNum = n;
		Solution.numOfCLBs = numOfClb;
		Solution.numOfVariables = parser.getNumOfVariables();

		System.out.println(Arrays.toString(parser.getTruthTable()));
		System.out.println(parser.getNazivLokacijaMapa());
		

		

		CLBAlgorithm alg = new CLBAlgorithm(n, numOfClb, parser.getNumOfVariables(), max_iter, parser.getTruthTable(),
				parser.getNazivLokacijaMapa(), root);
		
//
		Solution sol = alg.run();

		
		System.out.println(Arrays.toString(sol.data));
		System.out.println(sol.index);
		System.out.println(sol.fitness);
	}

}
