package hr.fer.zemris.trisat.algorithms;

import hr.fer.zemris.trisat.BitVector;
import hr.fer.zemris.trisat.MutableBitVector;
import hr.fer.zemris.trisat.SATFormula;

/**
 * Algoritam koji ide kroz sve moguće kombinacije 
 * i vraća prvo rješenje.
 * @author Vinko
 *
 */
public class Algorithm1 implements Algorithm {

	/**
	 * Formula koju treba zadovoljiti
	 */
	private SATFormula formula;
	
	public Algorithm1 (SATFormula formula) {
		this.formula = formula;
	}
	
	@Override
	public double fitness(BitVector assignment) {
		return 0;
	}

	@Override
	public BitVector execute() {
		
		//stvori početno rješenje (niz nula)
		MutableBitVector assignment = new MutableBitVector(formula.getNumberOfVariables());
		
		BitVector solution = null;
		double size = Math.pow(2, formula.getNumberOfVariables());
		
		//iteriraj kroz sva moguća rješenja i vrati prvo točno
		for (int i=0; i<size; i++) {
			if (formula.isSatisfied(assignment)) {
					solution = assignment;
					break;
				}
			assignment = BitVector.increment(assignment);
		}
		
		return solution;
	}
	

}
