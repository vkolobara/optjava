import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AlgorithmNSGA {

	private double[] max, min;
	private double alpha, share, s;
	private int size, sol_size, max_iter;
	private MOOPProblem problem;
	private IDistanceCalc d;
	
	
	
	public AlgorithmNSGA(double[] max, double[] min, double alpha, double share, double s, int size, int sol_size,
			int max_iter, MOOPProblem problem, IDistanceCalc d) {
		super();
		this.max = max;
		this.min = min;
		this.alpha = alpha;
		this.share = share;
		this.s = s;
		this.size = size;
		this.sol_size = sol_size;
		this.max_iter = max_iter;
		this.problem = problem;
		this.d = d;
	}

	public DoubleArraySolution run() {
		Population pop = new Population(size);
		RouletteWheelSelection selection = new RouletteWheelSelection();
		
		while (!pop.isFull()) {
			DoubleArraySolution sol = new DoubleArraySolution(sol_size);
			sol.randomize(max, min);
			pop.addSolution(sol);
		}
		
				
		for (int t=1; t<=max_iter; ++t) {
			evaluateFitness(pop);	

			System.out.println("Generacija " + t + " - Fitness : " + pop.getBest().fitness);
			
			Population newPop = new Population(size);
			
			while (!newPop.isFull()) {
				DoubleArraySolution[] parents = selection.select(pop);
				DoubleArraySolution child = GeneticOperators.crossover(parents[0], parents[1], max, min);
				child = GeneticOperators.mutation(child, s, max, min);
				newPop.addSolution(child);
			}

			pop = newPop;
		}
		
		return pop.getBest();
	}
	
	private void evaluateFitness(Population pop) {
		pop.population.forEach(sol -> sol.objectives = problem.evaluateSolution(sol.solution));
		
		List<Set<Integer>> fronte = calculateFronts(pop);

		double fMin = pop.getSize();
		
		for (Set<Integer> fronta : fronte) {
			fMin*=0.9;
			double newFMin = fMin;
			for (int q : fronta) {
				double nc = 0;
				DoubleArraySolution qSol = pop.population.get(q);
				qSol.fitness = fMin;
				
				for (int k : fronta) {
					nc += sh(qSol, pop.population.get(k));
				}
				
				qSol.fitness/=nc;
				
				if (newFMin > qSol.fitness) {
					newFMin = qSol.fitness;
				}
			}
			fMin = newFMin;
		}
		
		
	}

	private double sh(DoubleArraySolution sol1, DoubleArraySolution sol2) {
		double distance = d.calcDistance(sol1.solution, sol2.solution);
		if (distance >= share) return 0;
		else return (1 - Math.pow(distance / share, alpha));
	}

	private List<Set<Integer>> calculateFronts(Population pop) {
		
		List<Set<Integer>> fronte = new ArrayList<>();
		List<Set<Integer>> S = new ArrayList<>();
		
		fronte.add(new HashSet<>());
		
		int size = pop.getSize();
		int k = 1;
		int[] n = new int[size];
		for (int i=0; i<size; i++) {

			Set<Integer> s = new HashSet<>();
			DoubleArraySolution solI = pop.population.get(i);

			for (int j=0; j<size; j++) {
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
				k=1;
			}
		}
		
		
		while (!fronte.get(k-1).isEmpty()) {
			Set<Integer> Q = new HashSet<>();
			
			for (int i : fronte.get(k-1)) {
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

		return fronte;
		
	}
	
	private boolean isDominated(DoubleArraySolution solution, DoubleArraySolution dominator) {
		
		boolean dominated = false;
		
		for (int i=0; i<sol_size; i++) {
			if(solution.objectives[i] < dominator.objectives[i]) {
				dominated = false;
				break;
			} else if (solution.objectives[i] > dominator.objectives[i]) {
				dominated = true;
			}
		}
		 
		return dominated;
	}
	
}
