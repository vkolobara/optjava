package hr.fer.zemris.optjava.dz5;

import java.util.Random;

/**
 * Razred koji sadrži sve genetske operatore.
 * 
 * @author Vinko
 *
 */
public class GeneticOperators {
	
	public final static int CROSSOVER_NUM = 4;

	
	private final static double MUT_RATE = 0.02;

	private static Random rand = new Random();

	/**
	 * Uniformno križanje
	 * 
	 * @param r1
	 *            prvi roditelj
	 * @param f2
	 *            drugi roditelj
	 * @return križano dijete
	 */
	public static BitVectorSolution unifCrossover(BitVectorSolution r1, BitVectorSolution r2) {
		BitVectorSolution child = (BitVectorSolution) r1.newLikeThis();
		int size = r1.getSize();
		for (int i = 0; i < size; i++) {
			child.values[i] = rand.nextBoolean() ? r1.values[i] : r2.values[i];
		}
		return child;
	}

	/**
	 * Križanje s jednom točkom prekida
	 * 
	 * @param r1
	 *            prvi roditelj
	 * @param f2
	 *            drugi roditelj
	 * @return križano dijete
	 */
	public static BitVectorSolution onePointCrossover(BitVectorSolution r1, BitVectorSolution r2) {
		BitVectorSolution child = (BitVectorSolution) r1.newLikeThis();
		int size = r1.getSize();
		int breakPoint = rand.nextInt(size - 2) + 1;
		int i;
		
		for (i = 0; i < breakPoint; i++) {
			child.values[i] = r1.values[i];
		}

		for (; i < size; i++) {
			child.values[i] = r2.values[i];
		}
		
		return child;
	}
	
	/**
	 * Križanje s dvije točke prekida
	 * 
	 * @param r1
	 *            prvi roditelj
	 * @param f2
	 *            drugi roditelj
	 * @return križano dijete
	 */
	public static BitVectorSolution twoPointCrossover(BitVectorSolution r1, BitVectorSolution r2) {
		BitVectorSolution child = (BitVectorSolution) r1.newLikeThis();
		int size = r1.getSize();
		int breakPoint1 = rand.nextInt(size - 2) + 1;
		int breakPoint2 = rand.nextInt(size-1 - breakPoint1) + breakPoint1;
		int i;
		
		for (i = 0; i < breakPoint1; i++) {
			child.values[i] = r1.values[i];
		}

		for (; i < breakPoint2; i++) {
			child.values[i] = r2.values[i];
		}
		for (; i < size; i++) {
			child.values[i] = r1.values[i];
		}
		
		return child;
	}
	
	/**
	 * Križanje s XOR operatorom
	 * 
	 * @param r1
	 *            prvi roditelj
	 * @param f2
	 *            drugi roditelj
	 * @return križano dijete
	 */
	public static BitVectorSolution xorCrossover(BitVectorSolution r1, BitVectorSolution r2) {
		BitVectorSolution child = (BitVectorSolution) r1.newLikeThis();
		int size = r1.getSize();
		
		for (int i=0; i<size; i++) {
			child.values[i] = r1.values[i] ^ r2.values[i];
		}
		
		return child;
	}
	
	/**
	 * Mutacija flipanjem bitova
	 * @param child
	 * @return mutirano dijete
	 */
	public static BitVectorSolution mutate(BitVectorSolution child) {
		BitVectorSolution mutation = (BitVectorSolution) child.duplicate();	
		int size = mutation.getSize();	
		
		for (int i=0; i<size; i++) {
			if (rand.nextDouble() <= MUT_RATE) {
				mutation.values[i] = !mutation.values[i];
			}
		}		
		return mutation;
	}

}
