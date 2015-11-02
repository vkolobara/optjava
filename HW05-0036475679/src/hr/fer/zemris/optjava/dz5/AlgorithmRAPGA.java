package hr.fer.zemris.optjava.dz5;

import java.util.Random;

/**
 * Implementacija RAPGA algoritma.
 * @author Vinko
 *
 */

public class AlgorithmRAPGA implements Algorithm<Solution>{
	
	private Random rand;
	
	private int minPop;
	private int maxPop;
	private int n;
	private int k;
	
	private double actSelPress;
	private double maxSelPress;
	private double compFactor;
	
	/**
	 * 
	 * @param minPop minimalna veličina populacije
	 * @param maxPop maksimalna veličina populacije
	 * @param maxSelPress maksimalni pritisak
	 * @param compFactor faktor usporedbe djece i roditelja
	 * @param n dužina vektora
	 * @param k k za k-turnirsku selekciju
	 */
	public AlgorithmRAPGA(int minPop, int maxPop, double maxSelPress, double compFactor, int n, int k) {
		this.minPop = minPop;
		this.maxPop = maxPop;
		this.maxSelPress = maxSelPress;
		this.compFactor = compFactor;
		this.n = n;
		this.k = k;
		rand = new Random();
	}



	@Override
	public Solution run() {
		
		RAPGAPopulation pop = new RAPGAPopulation(maxPop);
		generateRandomPop(pop);
				
outer:	while (true) {
			TournamentSelection tSel = new TournamentSelection(pop);
			RandomSelection rSel = new RandomSelection(pop);
			int limit = (int) (pop.getSize() * maxSelPress);
			RAPGAPopulation newPop = new RAPGAPopulation(maxPop);
			
			for (int i=0; i<limit; i++) {
				
				BitVectorSolution r1 = (BitVectorSolution) tSel.select(k);
				BitVectorSolution r2 = (BitVectorSolution) rSel.select();
				
				BitVectorSolution child = randCrossover(r1, r2);
				child = GeneticOperators.mutate(child);
				child.setFitness();

				if (newPop.isFull()) {
					pop = newPop;
					continue outer;
				}
				
			}
			
			break;
		}
		
		
		return null;
	}

	private BitVectorSolution randCrossover(BitVectorSolution r1, BitVectorSolution r2) {
		
		int num = rand.nextInt(GeneticOperators.CROSSOVER_NUM);
		
		BitVectorSolution child;
		
		switch(num) {
		case 0:
			child = GeneticOperators.onePointCrossover(r1, r2);
			break;
		case 1:
			child = GeneticOperators.twoPointCrossover(r1, r2);
			break;
		case 2:
			child = GeneticOperators.unifCrossover(r1, r2);
			break;
		default:
			child = GeneticOperators.xorCrossover(r1, r2);
		}
		
		return child;
		
	}

	private void generateRandomPop(RAPGAPopulation pop) {
		while (!pop.isFull()) {
			BitVectorSolution sol = new BitVectorSolution(n);
			sol.randomize();
			sol.setFitness();
			pop.add(sol);
		}
	}

}
