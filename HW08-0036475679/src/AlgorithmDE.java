public class AlgorithmDE {


	private int pop_size;
	private int sol_size;
	private int max_iter;
	private double merr;
	private double F;
	private double Cr;
	private StrategyDE strategy;
	private IEvaluator evaluator;
	
	
	
	public AlgorithmDE(int pop_size, int sol_size, int max_iter, double merr, double f, double cr, StrategyDE strategy,
			IEvaluator evaluator) {
		super();
		this.pop_size = pop_size;
		this.sol_size = sol_size;
		this.max_iter = max_iter;
		this.merr = merr;
		F = f;
		Cr = cr;
		this.strategy = strategy;
		this.evaluator = evaluator;
	}



	public DoubleArraySolution run() {
		
		Population pop = new Population(pop_size);
		
		while (!pop.isFull()) {
			DoubleArraySolution sol = new DoubleArraySolution(sol_size);
			sol.randomize(1, -1);
			sol.fitness = evaluator.evaluateFitness(sol);
			pop.addSolution(sol);
		}
		
		int t=0;
		while (t++<max_iter) {
			System.out.println("Generacija: " + t + " - " + pop.getBest().fitness);
			if (pop.getBest().fitness <= merr) break;
			Population newPop = new Population(pop_size);
			for (int i=0; i<pop_size; i++) {
				DoubleArraySolution v = strategy.mutation(F, i, pop);
				DoubleArraySolution u = strategy.crossover(pop.population.get(i), v, Cr);
				
				u.fitness = evaluator.evaluateFitness(u);
				if (Double.compare(u.fitness, pop.population.get(i).fitness) <= 0) {
					newPop.addSolution(u);
				} else {
					newPop.addSolution(pop.population.get(i));
				}
			}
			
			pop = newPop;
			
		}
		
		
		return pop.getBest();
	}
	
}

