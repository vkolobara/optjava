package hr.fer.zemris.optjava.dz5.algorithm;

import java.util.Random;

import hr.fer.zemris.optjava.dz5.GeneticOperators;
import hr.fer.zemris.optjava.dz5.population.Population;
import hr.fer.zemris.optjava.dz5.population.RAPGAPopulation;
import hr.fer.zemris.optjava.dz5.selection.RandomSelection;
import hr.fer.zemris.optjava.dz5.selection.TournamentSelection;
import hr.fer.zemris.optjava.dz5.solution.BitVectorSolution;
import hr.fer.zemris.optjava.dz5.solution.Solution;

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
	public Solution run(Population<Solution> population) {
		
		Population<? extends Solution> pop = population;
						
		int t=0;
outer:	while (true) {
	
			//TODO
			//ZA OVO MOŽDA TREBA FUNKCIJU NAPRAVITI, ILI ČAK RAZRED KAO 
			//COMPFACTOR SCHEDULE
			if (compFactor < 1) {
				compFactor = Math.max(Math.log(1.0*++t/(10*n)), 0);
			}
			
			TournamentSelection tSel = new TournamentSelection(pop);
			RandomSelection rSel = new RandomSelection(pop);
			int limit = (int) (pop.getSize() * maxSelPress);
			RAPGAPopulation newPop = new RAPGAPopulation(maxPop);
			
			BitVectorSolution r1 = (BitVectorSolution) tSel.select(true, k);
			BitVectorSolution r2 = (BitVectorSolution) rSel.select(true);
			for (int i=0; i<limit; i++) {

				BitVectorSolution child = randCrossover(r1, r2);
				child = GeneticOperators.mutate(child);
				child.setFitness();

				if (successfull(child, r1, r2)) {
					newPop.add(child);
				}
				
				if (newPop.isFull()) {
					pop = newPop;
					continue outer;
				}
			
			}	
			
			if (newPop.population.size() < minPop){
				break;
			} else {
				pop = newPop;
			}
		}
		
		return pop.getBest();
	}

	private boolean successfull(BitVectorSolution child, BitVectorSolution r1, BitVectorSolution r2) {
		double smallerFit = Math.min(r1.fitness, r2.fitness);
		double biggerFit = Math.max(r1.fitness, r2.fitness);
		return child.fitness >= smallerFit + compFactor * (biggerFit - smallerFit);
	}

	private BitVectorSolution randCrossover(BitVectorSolution r1, BitVectorSolution r2) {
		
		int num = rand.nextInt(GeneticOperators.BIT_CROSSOVER_NUM);
		
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


}
