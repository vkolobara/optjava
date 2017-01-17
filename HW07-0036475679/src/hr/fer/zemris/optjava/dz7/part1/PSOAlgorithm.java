package hr.fer.zemris.optjava.dz7.part1;

import java.util.LinkedList;
import java.util.List;

public class PSOAlgorithm {

	private Swarm population;
	private int neighborSize, d, maxIter, wTMax;
	private double c1, c2, merr, wMin, wMax;
	private double[] xMax, xMin, vMax, vMin;
	private FFAN ffan;

	public PSOAlgorithm(FFAN ffan, int popSize, int neighborSize, int d, double c1, double c2, double[] xMax,
			double[] xMin, double[] vMax, double[] vMin, double wMax, double wMin, int wTMax, double merr,
			int maxIter) {

		this.population = new Swarm(popSize);
		this.neighborSize = neighborSize;
		this.d = d;
		this.c1 = c1;
		this.c2 = c2;
		this.xMax = xMax;
		this.xMin = xMin;
		this.vMax = vMax;
		this.vMin = vMin;
		this.ffan = ffan;
		this.wMax = wMax;
		this.wMin = wMin;
		this.wTMax = wTMax;
		this.merr = merr;
		this.maxIter = maxIter;
	}

	public Particle run() {

		population.initializePopulation(neighborSize, c1, c2, d, xMax, xMin, vMax, vMin, wMin, wTMax);

		int j = 0;
		for (Particle p : population.population) {
			p.setNeighbors(j++, population);
		}

		int t = 0;
		List<double[]> inputs = ffan.getDataset().getInputs();
		int outputLen = ffan.getDataset().getOutputs().get(0).length;
		int sampleNumber = ffan.getDataset().numberOfSamples();

		while (true) {
			for (Particle p : population.population) {
				List<double[]> outputs = new LinkedList<>();

				for (int i = 0; i < sampleNumber; i++) {
					double[] output = new double[outputLen];
					ffan.calcOutputs(inputs.get(i), p.x, output);
					outputs.add(output);
				}

				p.updateW(t, wTMax, wMin, wMax);
				p.fitness = ffan.error(outputs);
				if ( p.fitness < p.bestSeen.fitness) {
					p.bestSeen = p;
				}
			}

			System.out.println("Generacija: " + (t+1) + ", Najbolje rješenje: " + population.getBest().fitness);

			if (population.getBest().fitness < merr || t >= maxIter-1) {
				break;
			}

			for (Particle p : population.population) {
				p.updatePosition(xMax, xMin, vMax, vMin);
			}

			t++;
		}

		return population.getBest();
	}

}
