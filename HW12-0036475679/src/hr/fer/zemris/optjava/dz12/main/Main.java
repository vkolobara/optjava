package hr.fer.zemris.optjava.dz12.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;

import hr.fer.zemris.optjava.dz12.algorithm.CLBAlgorithm;
import hr.fer.zemris.optjava.dz12.algorithm.Solution;
import hr.fer.zemris.optjava.dz12.parser.Node;
import hr.fer.zemris.optjava.dz12.parser.Parser;
import hr.fer.zemris.optjava.rng.EVOThread;

/**
 * Kod unosa funkcije obavezno NOT staviti u zagradu. Primjer:
 * "((NOT S) AND A) OR (B AND S)"
 * 
 * @author Vinko
 *
 */

public class Main {

	public static void main(String[] args) {

		int n = 2;
		int numOfClb = 6;
		// MUX 2
		String expression = "((NOT S) AND A) OR (B AND S)";
		// MUX 4
		// String expression = "(A AND (NOT S0) AND NOT(S1)) OR (B AND S0 AND
		// (NOT S1)) OR (C AND (NOT S0) AND S1) OR (D AND S0 AND S1)";
		Parser parser = new Parser(expression, numOfClb);
		Node root = parser.parseAndCalculateTruth();

		int max_iter = 1000;
		int pop_size = 200;

		System.out.println(Arrays.toString(parser.getTruthTable()));
		System.out.println(parser.getNazivLokacijaMapa());
		List<Solution> best = new ArrayList<>();

		LinkedBlockingQueue<Solution> queue[] = new LinkedBlockingQueue[4];

		ExecutorService executor = Executors.newFixedThreadPool(4, new ThreadFactory() {

			@Override
			public Thread newThread(Runnable r) {
				return new EVOThread(r);
			}
		});
		
		for (int i = 0; i < 4; i++) {
			queue[i] = new LinkedBlockingQueue<Solution>();
		}
		for (int i = 0; i < 4; i++) {
			final int j = i;
			executor.execute(new EVOThread(new Runnable() {
				@Override
				public void run() {
					CLBAlgorithm alg = new CLBAlgorithm(n, numOfClb, parser.getNumOfVariables(), pop_size, max_iter,
							parser.getTruthTable(), queue, j);
					best.add(alg.run());
				}
			}));

		}

		executor.shutdown();

		while (!executor.isTerminated())
			;
		System.out.println();
		Solution sol = Collections.max(best);
		System.out.println(Arrays.toString(sol.data));
		System.out.println(sol.fitness);
		System.out.println("CLB" + (sol.index + 1));

	}

}
