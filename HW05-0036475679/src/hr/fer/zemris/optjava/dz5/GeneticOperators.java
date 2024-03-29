package hr.fer.zemris.optjava.dz5;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import hr.fer.zemris.optjava.dz5.solution.BitVectorSolution;
import hr.fer.zemris.optjava.dz5.solution.PermutationSolution;

/**
 * Razred koji sadrži sve genetske operatore.
 * 
 * @author Vinko
 *
 */
public class GeneticOperators {

	public final static int BIT_CROSSOVER_NUM = 4;
	private final static double MUT_RATE = 0.2;

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
		int breakPoint2 = rand.nextInt(size - 1 - breakPoint1) + breakPoint1;
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

		for (int i = 0; i < size; i++) {
			child.values[i] = r1.values[i] ^ r2.values[i];
		}

		return child;
	}

	/**
	 * Križanje rješenja zapisanog u permutacijskom obliku
	 * 
	 * @param r1
	 *            prvi roditelj
	 * @param r2
	 *            drugi roditelj
	 * @return križano dijete
	 */
	public static PermutationSolution permCrossover(PermutationSolution r1, PermutationSolution r2) {
		PermutationSolution child = (PermutationSolution) r1.newLikeThis();

		// TODO izaberi podniz prvog roditelja, ubaci ga na isto mjesto u drugog
		// i pobriši duple brojeve.

		int maxSubLen = r1.getSize() / 2;

		int subLen = rand.nextInt(maxSubLen);
		int ind = rand.nextInt(r1.getSize() - subLen);

		List<Integer> subArr = IntStream.of(Arrays.copyOfRange(r1.values, ind, ind + subLen)).boxed()
				.collect(Collectors.toList());
		
		int i = 0;
		int j=0;
		for (; j < ind; i++) {
			if (subArr.contains(r2.values[i])) {
				continue;
			}
			child.values[j++] = r2.values[i];
		}

		for (int num : subArr) {
			child.values[j++] = num;
		}

		for (; i < r2.values.length; i++) {
			if (subArr.contains(r2.values[i])) {
				continue;
			}
			child.values[j++] = r2.values[i];
		}

		return child;
	}

	/**
	 * Mutacija flipanjem bitova
	 * 
	 * @param child
	 * @return mutirano dijete
	 */
	public static BitVectorSolution mutate(BitVectorSolution child) {
		BitVectorSolution mutation = (BitVectorSolution) child.duplicate();
		int size = mutation.getSize();

		for (int i = 0; i < size; i++) {
			if (rand.nextDouble() <= MUT_RATE) {
				mutation.values[i] = !mutation.values[i];
			}
		}
		return mutation;
	}

	/**
	 * Mutira permutaciju tako što mijenja mjesta 2 random indeksa.
	 * 
	 * @param child
	 *            dijete za mutaciju
	 * @return mutirano dijete
	 */
	public static PermutationSolution mutate(PermutationSolution child) {

		for (int i = 0; i < child.values.length; i++) {
			if (rand.nextDouble() <= MUT_RATE) {
				int ind = rand.nextInt(child.values.length);
				int temp = child.values[i];
				child.values[i] = child.values[ind];
				child.values[ind] = temp;
			}
		}

		return child;

	}

}
