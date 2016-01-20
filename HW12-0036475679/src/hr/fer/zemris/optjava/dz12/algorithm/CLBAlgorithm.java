package hr.fer.zemris.optjava.dz12.algorithm;

import java.util.Collections;
import java.util.Map;

import hr.fer.zemris.optjava.dz12.clb.CLB;
import hr.fer.zemris.optjava.dz12.parser.Node;

public class CLBAlgorithm {

	private int n, numOfClb, numOfVariables, max_iter;
	private boolean[] truthTable;
	private Map<String, Integer> nazivLokacija;
	private Node root;

	public CLBAlgorithm(int n, int numOfClb, int numOfVariables, int max_iter, boolean[] truthTable,
			Map<String, Integer> nazivLokacija, Node root) {
		this.n = n;
		this.numOfClb = numOfClb;
		this.numOfVariables = numOfVariables;
		this.max_iter = max_iter;
		this.truthTable = truthTable;
		this.nazivLokacija = nazivLokacija;
		this.root = root;
	}
	
	public Solution run() {
		Population pop = new Population(200);
		while (!pop.isFull()) {
			Solution sol = new Solution();
			sol.randomize();
			pop.addSolution(sol);
		}
		
		int t=0;
		
		while (t < max_iter) {
			pop.population.forEach(sol -> evaluateSolution(sol));
			if (Math.abs(pop.getBest().fitness) < 10E-5) break;
			System.out.println("Generacija " + t + " ;Fitness = " + pop.getBest().fitness);
			Population newPop = new Population(200);
			while (!newPop.isFull()) {
				Solution p1 = GeneticOperators.tournamentSelection(pop, 4);
				Solution p2 = GeneticOperators.tournamentSelection(pop, 4);
				
				Solution child = GeneticOperators.crossover(p1, p2);
				child = GeneticOperators.mutate(child);
				newPop.addSolution(child);
			}
			
			pop = newPop;
			t++;
		}
		
		return pop.getBest();
		
	}
	
	public void evaluateSolution(Solution sol) {
		
	
		double min_diff = Double.MAX_VALUE;
		int index = -1;
		
		int tableSize = (int) Math.pow(2, n);
		
		int step = tableSize + n;
		boolean[][] values = new boolean[truthTable.length][numOfVariables + numOfClb];
		for (int i=0; i<numOfClb*step; i+=step) {
			double err = 0;
			for (int j = 0; j < truthTable.length; j++) {
				String s = Integer.toBinaryString(j);
				while (s.length() < numOfVariables) {
					s = '0' + s;
				}

				int k = 0;
				for (char c : s.toCharArray()) {
					values[j][k++] =  c == '0' ? false : true; 
				}
				
				boolean[] input = new boolean[n];
				boolean[] table = new boolean[tableSize];
				
				int x;
				for (x = 0; x<input.length; x++) {
					input[x] = values[j][sol.data[i+x]];
				}
				
				for ( ; x < step; x++) {
					table[x-n] = sol.data[i+x] == 1 ? true : false;
				}
				
				boolean output = CLB.calculate(input, table);
				values[j][i/step+numOfVariables] = output;
				
				if (output != truthTable[j]) err++;
				
			}

			if (err < min_diff) {
				min_diff = err;
				index = i/step;
			}
						
		}
		
		sol.fitness = -min_diff;
		sol.index = index;
		
	
	}

}
