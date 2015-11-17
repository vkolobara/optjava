package hr.fer.zemris.optjava.dz7;

import java.util.Random;

public class Particle {

	public double fitness;
	public Particle bestSeen;
	public double[] v;
	public double[] x;
	private double w;
	private int d;
	public Swarm neighbors;
	private double c1;
	private double c2;
	private Random rand;

	public Particle(int d, double c1, double c2) {
		this.v = new double[d];
		this.x = new double[d];
		this.d = d;
		this.bestSeen = null;
		this.c1 = c1;
		this.c2 = c2;
		rand = new Random();
	}

	public void initializeRandom(double[] xMax, double[] xMin, double[] vMax, double[] vMin) {
		for (int i = 0; i < d; i++) {
			x[i] = rand.nextDouble() * (xMax[i] - xMin[i]) + xMin[i];
			v[i] = rand.nextDouble() * (vMax[i] - vMin[i]) + vMin[i];
		}
	}
	
	public void setNeighbors(int n, Swarm pop) {
		neighbors = pop.neighbors(n);
	}

	public void updatePosition(double[] xMax, double[] xMin, double[] vMax, double[] vMin) {
		Particle bestNeighbor = neighbors.getBest();
		for (int i = 0; i < d; i++) {
			v[i] = w * v[i] +  c1 * rand.nextDouble() * (bestSeen.x[i] - x[i])
					+ c2 * rand.nextDouble() * (bestNeighbor.x[i] - x[i]);
			v[i] = Math.max(vMin[i], v[i]);
			v[i] = Math.min(vMax[i], v[i]);
			x[i] += v[i];
			

		}
	}
	
	
	public void updateW(int t, int wTMax, double wMin, double wMax) {
		if (t<=wTMax) {
			w = (wTMax - t) * (wMax - wMin) / wTMax + wMin;
		} else {
			w = wMin;
		}
	}

}
