package hr.fer.zemris.optjava.dz5.part2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import hr.fer.zemris.optjava.dz5.algorithm.AlgorithmSASEGASA;
import hr.fer.zemris.optjava.dz5.population.Population;
import hr.fer.zemris.optjava.dz5.population.SASEGASAPopulation;
import hr.fer.zemris.optjava.dz5.solution.PermutationSolution;
import hr.fer.zemris.optjava.dz5.solution.Solution;

/**
 * Razred koji pokreće rješavanje QAP. Pojedina podpopulacija se vrti Offspring
 * Selection-om (paralelizirano).
 * 
 * Potrebni argumenti komandne linije: 
 * <ul>
 * <li>Putanja do datoteke</li>
 * <li>Ukupan broj jedinki</li>
 * <li>Broj podpopulacija</li>
 * </ul>
 * 
 * Optimalna rješenja:
 * <table>
 * <tr>
 * <td>nug12.dat</td>
 * <td>->578</td>
 * </tr>
 * <tr>
 * <td>nug25.dat</td>
 * <td>->3744</td>
 * </tr>
 * <tr>
 * <td>els19.dat</td>
 * <td>->17 212 548</td>
 * </tr>
 * </table>
 * 
 * 
 * @author Vinko
 *
 */
public class GeneticAlgorithm {

	public static int[][] c;
	public static int[][] d;
	private static int n;

	public static void main(String[] args) {
		try {
			int solNumber = 150;
			int popNumber = 10;
			double succRatio = 0.40;
			double compFactor = 0;
			double maxSelPress = 10;

			SASEGASAPopulation pop = new SASEGASAPopulation(popNumber);

			
			readFile(args[0]);
			solNumber = Integer.parseInt(args[1]);
			popNumber = Integer.parseInt(args[2]);

			
			int size = solNumber / popNumber;
			int rem = solNumber % popNumber;
			while (!pop.isFull()) {
				pop.addPopulation(generateRandomPop(size));
			}

			if (rem != 0) {
				pop.remove(pop.getSize() - 1);
				pop.addPopulation(generateRandomPop(rem));
			}

			AlgorithmSASEGASA alg = new AlgorithmSASEGASA(solNumber, popNumber, n, succRatio, compFactor, maxSelPress);
			Solution sol = alg.run(pop);

			System.out.println(sol + " Error: " + sol.fitness);

		} catch (IOException e) {
			throw new IllegalArgumentException("Neispravni argumenti komandne linije!");
		}
	}

	private static void readFile(String path) throws IOException {
		List<String> rows = Files.readAllLines(Paths.get(path));

		int i = 0;

		String row = rows.get(i++);
		n = Integer.parseInt(row.trim());
		i++;

		c = new int[n][n];
		d = new int[n][n];

		fillMatrix(rows, i, d);
		i += n + 1;
		fillMatrix(rows, i, c);

	}

	private static void fillMatrix(List<String> rows, int i, int[][] mat) {
		String row;
		int j = 0;
		int size = rows.size();
		int k=0;
		while (true && i < size) {
			if (k==n) {
				k=0;
				j++;
			}
			
			row = rows.get(i++);
			if (row.isEmpty())
				break;
			String[] values = row.trim().split("\\s+");
			for (String val : values) {
				mat[j][k++] = Integer.parseInt(val);
			}

		}
	}

	/**
	 * Generira nasumičnu populaciju veličine <i>size</i>
	 * 
	 * @param size
	 *            veličina populacije koja se generira
	 * @return novonastalu populaciju
	 */
	private static Population<PermutationSolution> generateRandomPop(int size) {
		Population<PermutationSolution> pop = new Population<>(size);

		while (!pop.isFull()) {
			PermutationSolution sol = new PermutationSolution(n);
			sol.randomize();
			sol.setFitness();
			pop.add(sol);
		}

		return pop;

	}

}
