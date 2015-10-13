package hr.fer.zemris.trisat.algorithms;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import hr.fer.zemris.trisat.BitVector;
import hr.fer.zemris.trisat.BitVectorNGenerator;
import hr.fer.zemris.trisat.MutableBitVector;
import hr.fer.zemris.trisat.SATFormula;
import hr.fer.zemris.trisat.SATFormulaStats;

/**
 * Implementacija pohlepnog algoritma uspona na vrh.
 * @author Vinko
 *
 */
public class Algorithm2 implements Algorithm{

	/**
	 * Maksimalni broj iteracija.
	 */
	private final int MAX_ITER = 100000;
	
	/**
	 * Formula koju treba zadovoljiti.
	 */
	private SATFormula formula;
	
	/**
	 * Statistika formule; služi za računanje dobrote
	 */
	private SATFormulaStats stats;
	
	public Algorithm2 (SATFormula formula) {
		this.formula = formula;
		stats = new SATFormulaStats(formula);
	}
	
	@Override
	public double fitness(BitVector assignment) {	
		stats.setAssignment(assignment, false);
		return stats.getNumberOfSatisfied();
	}

	@Override
	public BitVector execute() {

		Random rand = new Random();
		
		int t=0;
		
		//stvori slučajno početno rješenje
		BitVector solution = new BitVector(rand, formula.getNumberOfVariables());
		
		do {
			if (formula.isSatisfied(solution)) break;

			BitVectorNGenerator gen = new BitVectorNGenerator(solution);	
				
			double solutionFitness = fitness(solution);
			
			double currBestFitness = 0;
			List<MutableBitVector> bestNeighbors = new LinkedList<>();
			
			//nađi susjeda/e s najboljom dobrotom.
			for (MutableBitVector neighbor : gen) {
				double fitness = fitness(neighbor);
				if (Double.compare(fitness, currBestFitness) < 0) {
					continue;
				} else if (Double.compare(fitness, currBestFitness) > 0) {
					bestNeighbors.clear();
				}
				currBestFitness = fitness;
				bestNeighbors.add(neighbor);
			}
			
			//slučajno odaberi jednog od preživjelih
			BitVector bestSolution = bestNeighbors.get(rand.nextInt(bestNeighbors.size()));
			
			if (currBestFitness < solutionFitness) {
				System.out.println("Pronađen lokalni optimum, prekid programa.");
				System.exit(0);
			}
						
			solution = bestSolution;
			solutionFitness = currBestFitness;
			t++;
		} while (t < MAX_ITER);
		
		return solution;
	}

}
