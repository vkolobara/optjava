package hr.fer.zemris.optjava.dz10;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class AlgorithmNSGAII {

	private double[] max, min;
	private double s;
	private int size, sol_size, max_iter;
	private MOOPProblem problem;

	public AlgorithmNSGAII(double[] max, double[] min, double s, int size, int sol_size,
			int max_iter, MOOPProblem problem) {
		super();
		this.max = max;
		this.min = min;
		this.s = s;
		this.size = size;
		this.sol_size = sol_size;
		this.max_iter = max_iter;
		this.problem = problem;
	}

	public Population run() {
		Population pop = new Population(size);
		CrowdedTournamentSelection selection = new CrowdedTournamentSelection();

		while (!pop.isFull()) {
			DoubleArraySolution sol = new DoubleArraySolution(sol_size);
			sol.randomize(max, min);
			pop.addSolution(sol);
		}

		for (int t = 1; t <= max_iter; ++t) {
			evaluateFitness(pop);

			System.out.println("Generacija: " + t);
			Population children = new Population(size);
			while (!children.isFull()) {
				DoubleArraySolution[] parents = selection.select(pop);
				DoubleArraySolution child = GeneticOperators.crossoverBLX(parents[0], parents[1], max, min);
				child = GeneticOperators.mutation(child, s, max, min);
				children.addSolution(child);
			}
			Population union = new Population(2*size);
			union.population.addAll(pop.population);
			union.population.addAll(children.population);
			evaluateFitness(union);
			List<Set<Integer>> fronte = calculateFronts(union);
			
			Population newPop = new Population(size);
			
			int i = 0;
			while (!newPop.isFull()) {
				if (fronte.get(i).size() + newPop.population.size() < size) {
					fronte.get(i).forEach(index -> newPop.addSolution(union.population.get(index)));
					i++;
				} else {
					List<DoubleArraySolution> lista = new ArrayList<>();
					fronte.get(i).forEach(index -> lista.add(union.population.get(index)));
					Collections.sort(lista);
					int j=0;
					while (!newPop.isFull()) {
						newPop.addSolution(lista.get(j++));
					}
				}
			}
			
			pop = newPop;

		}
		return pop;
	}

	private void evaluateFitness(Population pop) {
		pop.population.forEach(sol -> sol.objectives = problem.evaluateSolution(sol.solution));
		calculateFronts(pop);

		for (int i = 0; i < sol_size; i++) {
			final int ind = i;
			Collections.sort(pop.population, new Comparator<DoubleArraySolution>() {

				@Override
				public int compare(DoubleArraySolution o1, DoubleArraySolution o2) {
					return Double.compare(o1.objectives[ind], o2.objectives[ind]);
				}
			});
			double max = problem.getMaxs()[i];
			double min = problem.getMins()[i];
			double diff = max - min;
			pop.population.get(0).d = pop.population.get(size-1).d = 10*diff;
			
			for (int j=1; j<size-1; j++) {
				pop.population.get(j).d += (pop.population.get(j+1).objectives[i] - pop.population.get(j-1).objectives[i]) / diff;
			}
		}

	}


	public List<Set<Integer>> calculateFronts(Population pop) {

		List<Set<Integer>> fronte = new ArrayList<>();
		List<Set<Integer>> S = new ArrayList<>();

		fronte.add(new HashSet<>());

		int size = pop.getSize();
		int k = 0;
		int[] n = new int[size];
		for (int i = 0; i < size; i++) {

			Set<Integer> s = new HashSet<>();
			DoubleArraySolution solI = pop.population.get(i);

			for (int j = 0; j < size; j++) {
				DoubleArraySolution solJ = pop.population.get(j);

				if (isDominated(solJ, solI)) {
					s.add(j);
				} else if (isDominated(solI, solJ)) {
					n[i]++;
				}
			}

			S.add(s);

			if (n[i] == 0) {
				fronte.get(0).add(i);
				k = 1;
			}

			pop.population.get(i).r = n[i];
		}

		while (!fronte.get(k - 1).isEmpty()) {
			Set<Integer> Q = new HashSet<>();

			for (int i : fronte.get(k - 1)) {
				for (int j : S.get(i)) {
					n[j]--;
					if (n[j] == 0) {
						Q.add(j);
					}
				}
			}

			k++;
			fronte.add(Q);
		}

		fronte.remove(fronte.size() - 1);
		return fronte;

	}

	private boolean isDominated(DoubleArraySolution solution, DoubleArraySolution dominator) {

		boolean dominated = false;

		for (int i = 0; i < solution.objectives.length; i++) {
			if (solution.objectives[i] < dominator.objectives[i]) {
				dominated = false;
				break;
			} else if (dominator.objectives[i] < solution.objectives[i]) {
				dominated = true;
			}
		}

		return dominated;
	}

}
