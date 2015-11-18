package hr.fer.zemris.optjava.dz7.part2;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import hr.fer.zemris.optjava.dz7.part1.FFAN;

public class ClonAlg {

	private double max, min;
	private int dim;
	private int len;
	private int maxIter;
	private int N;
	private int d;
	private int n;
	private double beta;
	private double ro;
	private FFAN ffan;

	/**
	 * 
	 * @param max maksimalna vrijednost x-a
	 * @param min minimalna vrijednost x-a
	 * @param dim dimenzija vektora rješenja
	 * @param len duljina jednog prikaza dimenzije
	 * @param maxIter maksimalni broj iteracija
	 * @param N veličina populacije
	 * @param d broj slučajnih antitijela za ubacivanje
	 * @param n broj odabranih antitijela za kloniranje
	 * @param beta 
	 * @param ro
	 * @param ffan
	 */
	public ClonAlg(double max, double min, int dim, int len, int maxIter, int N, int d, int n, double beta, double ro, FFAN ffan) {
		this.max = max;
		this.min = min;
		this.dim = dim;
		this.len = len;
		this.maxIter = maxIter;
		this.N = N;
		this.d = d;
		this.n = n;
		this.beta = beta;
		this.ro = ro;
		this.ffan = ffan;
	}

	public BitvectorSolution run() {

		Population pop = initPopulation(N);
		
		List<double[]> inputs = ffan.getDataset().getInputs();
		int outputLen = ffan.getDataset().getOutputs().get(0).length;
		int sampleNumber = ffan.getDataset().numberOfSamples();
		
		int t=0;
		
		while (t++ < maxIter) {
			pop.population.forEach(sol -> sol.fitness = evaluateSolution(sol, sampleNumber, outputLen, inputs)); //evaluacija
			Population popSel = select(pop);
			Population popClo = clone(popSel);
			popClo.population.forEach(sol -> sol.mutate(Math.exp(-ro * 1.0/sol.fitness)));	//hipermutacija		
			popClo.population.forEach(sol -> sol.fitness = evaluateSolution(sol, sampleNumber, outputLen, inputs)); //evaluacija
						
			Population popNew = new Population(N);
			select(popClo).population.forEach(sol -> popNew.addSolution(sol));

			
			Population popRand = initPopulation(d);
			popRand.population.forEach(sol -> sol.fitness = evaluateSolution(sol, sampleNumber, outputLen, inputs)); //evaluacija
			
			Random rand = new Random();
			
			while (!popNew.isFull()) {
				popNew.addSolution(pop.population.get(rand.nextInt(N)));
			}
			
			for (BitvectorSolution sol : popRand.population) {
				popNew.population.set(rand.nextInt(N), sol);
			}
			
			pop = popNew;
			
			System.out.println(popNew.getBest().fitness);

		}
		
		
		return pop.getBest();
	}


	private Population clone(Population popSel) {

		List<BitvectorSolution> selected = popSel.getPopulation();
		Collections.sort(selected);
		
		int size = 0;
		for (int i=1; i<=n; i++) {
			size += (beta*n)/i;
		}
		
		Population cloned = new Population(size);
		
		int i=1;
		for (BitvectorSolution sol : selected) {
			int numOfDup =  (int) ((beta*n)/i);
			for (int j=0; j<numOfDup; j++) {
				cloned.addSolution(sol);
			}
		}
		
		
		return cloned;
	}

	private Population select(Population pop) {
		
		List<BitvectorSolution> population = pop.getPopulation();
		
		Population bestN = new Population(n);
		
		while (!bestN.isFull()) {
			BitvectorSolution best = Collections.max(population);
			bestN.addSolution(best);
			population.remove(best);
		}
		
		return bestN;
	}

	private double evaluateSolution(BitvectorSolution sol, int sampleNumber, int outputLen, List<double[]> inputs) {
		
		List<double[]> outputs = new LinkedList<>();

		for (int i = 0; i < sampleNumber; i++) {
			double[] output = new double[outputLen];
			ffan.calcOutputs(inputs.get(i), sol.decoded(max, min), output);
			outputs.add(output);
		}
		
		return ffan.error(outputs);
	}

	private Population initPopulation(int size) {
		
		Population pop = new Population(size);
		
		while (!pop.isFull()) {
			BitvectorSolution sol = new BitvectorSolution(len, dim);
			sol.randomize();
			pop.addSolution(sol);
		}
		
		return pop;
	}

}
