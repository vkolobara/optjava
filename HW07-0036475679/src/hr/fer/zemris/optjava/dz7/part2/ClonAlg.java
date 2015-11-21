package hr.fer.zemris.optjava.dz7.part2;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import hr.fer.zemris.optjava.dz7.part1.FFAN;

public class ClonAlg {

	private double max, min;
	private int dim;
	private int maxIter;
	private int N;
	private int d;
	private int n;
	private double beta;
	private double ro;
	private FFAN ffan;
	private double sigma;
	private Random rand;

	/**
	 * 
	 * @param max
	 *            maksimalna vrijednost x-a
	 * @param min
	 *            minimalna vrijednost x-a
	 * @param dim
	 *            dimenzija vektora rješenja
	 * @param maxIter
	 *            maksimalni broj iteracija
	 * @param N
	 *            veličina populacije
	 * @param d
	 *            broj slučajnih antitijela za ubacivanje
	 * @param n
	 *            broj odabranih antitijela za kloniranje
	 * @param beta
	 * @param ro
	 * @param ffan
	 */
	public ClonAlg(double max, double min, int dim, int maxIter, int N, int d, int n, double beta, double ro,
			FFAN ffan, double sigma) {
		this.max = max;
		this.min = min;
		this.dim = dim;
		this.maxIter = maxIter;
		this.N = N;
		this.d = d;
		this.n = n;
		this.beta = beta;
		this.ro = ro;
		this.ffan = ffan;
		this.sigma = sigma;
		rand = new Random();
	}

	public DoubleArraySolution run() {

		Population pop = initPopulation(N);

		List<double[]> inputs = ffan.getDataset().getInputs();
		int outputLen = ffan.getDataset().getOutputs().get(0).length;
		int sampleNumber = ffan.getDataset().numberOfSamples();

		int t = 0;

		while (t++ < maxIter) {
			System.out.println("Generacija: " + t);
			pop.population.forEach(sol -> sol.fitness = evaluateSolution(sol, sampleNumber, outputLen, inputs)); // evaluacija
			System.out.println(pop.getBest().fitness);
			
			Population popSel = select(pop);
			Population popClo = clone(popSel);
			Population popNew = new Population(N);
			popNew.addSolution(popClo.getBest());
			
			popClo.population.forEach(sol -> sol.mutate(Math.exp(-ro * 1.0 / sol.fitness), sigma)); // hipermutacija
			popClo.population.forEach(sol -> sol.fitness = evaluateSolution(sol, sampleNumber, outputLen, inputs)); // evaluacija

			select(popClo).population.forEach(sol -> popNew.addSolution(sol));

			Population popRand = initPopulation(d);
			popRand.population.forEach(sol -> sol.fitness = evaluateSolution(sol, sampleNumber, outputLen, inputs)); //evaluacija

			while (!popNew.isFull()) {
				popNew.addSolution(pop.getPopulation().get(rand.nextInt(N)));
			}

			for (DoubleArraySolution sol : popRand.population) {
				popNew.population.set(rand.nextInt(N-2)+2, sol);
			}

			pop = popNew;

		}

		return pop.getBest();
	}

	private Population clone(Population popSel) {

		List<DoubleArraySolution> selected = popSel.getPopulation();

		int size = 0;
		for (int i = 1; i <= n; i++) {
			size += (beta * n) / i + 0.5;
		}

		Population cloned = new Population(size);

		int i = 1;
		for (DoubleArraySolution sol : selected) {
			int numOfDup = (int) ((beta * n) / i + 0.5);
			for (int j = 0; j < numOfDup; j++) {
				cloned.addSolution(sol);
			}
		}

		return cloned;
	}

	private Population select(Population pop) {

		List<DoubleArraySolution> population = pop.getPopulation();

		Population bestN = new Population(n);

		while (!bestN.isFull()) {
			DoubleArraySolution best = Collections.max(population);
			bestN.addSolution(best);
			population.remove(best);
		}

		return bestN;
	}

	private double evaluateSolution(DoubleArraySolution sol, int sampleNumber, int outputLen, List<double[]> inputs) {

		List<double[]> outputs = new LinkedList<>();

		for (int i = 0; i < sampleNumber; i++) {
			double[] output = new double[outputLen];
			ffan.calcOutputs(inputs.get(i), sol.solution, output);
			outputs.add(output);
		}

		return ffan.error(outputs);
	}

	private Population initPopulation(int size) {

		Population pop = new Population(size);

		while (!pop.isFull()) {
			DoubleArraySolution sol = new DoubleArraySolution(dim);
			sol.randomize(max, min);
			pop.addSolution(sol);
		}

		return pop;
	}

}
