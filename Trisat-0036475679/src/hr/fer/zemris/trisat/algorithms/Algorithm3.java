package hr.fer.zemris.trisat.algorithms;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import java.util.TreeSet;

import hr.fer.zemris.trisat.BitVector;
import hr.fer.zemris.trisat.BitVectorNGenerator;
import hr.fer.zemris.trisat.MutableBitVector;
import hr.fer.zemris.trisat.SATFormula;
import hr.fer.zemris.trisat.SATFormulaStats;

public class Algorithm3 implements Algorithm{

	private final int MAX_ITER = 100000;
	private final int NUMBER_OF_BEST = 2;
	
	private SATFormula formula;
	private SATFormulaStats stats;
	
	public Algorithm3 (SATFormula formula) {
		this.formula = formula;
		stats = new SATFormulaStats(formula);
	}
	
	@Override
	public double fitness(BitVector assignment) {
		stats.setAssignment(assignment, false);
		return stats.getNumberOfSatisfied() + stats.getPercentageBonus();
	}

	@Override
	public BitVector execute() {
		
		//početno rješenje
		BitVector solution = new BitVector(new Random(), formula.getNumberOfVariables());
		Random random = new Random();
		
		//TreeSet koji sortira članove po dobroti
		TreeSet<BitVector> bestNeighbors = new TreeSet<BitVector>(new Comparator<BitVector>() {

			@Override
			public int compare(BitVector o1, BitVector o2) {
				return Double.compare(fitness(o1), fitness(o2));
			}
		});
		
		int t=0;		
		do {			
			stats.setAssignment(solution, true);
			if (stats.isSatisfied()) break;
			
			BitVectorNGenerator gen = new BitVectorNGenerator(solution);
			MutableBitVector[] neighbors = gen.createNeighborhood();
			
			bestNeighbors.clear();
			bestNeighbors.addAll(Arrays.asList(neighbors));
						
			//ostavi samo NUMBER_OF_BEST najboljih susjeda
			while (bestNeighbors.size() > NUMBER_OF_BEST) {
				bestNeighbors.pollFirst();
			}
			
			//slučajno odaberi jednog od preživjelih susjeda
			solution = (BitVector) bestNeighbors.toArray()[random.nextInt(bestNeighbors.size())];		
			
			t++;
		} while (t < MAX_ITER);
				
		return solution;
	}

}
