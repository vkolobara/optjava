package hr.fer.zemris.optjava.dz6;

import java.util.Arrays;
import java.util.List;

public class Graph {
	
	private List<Node> nodes;
	public double[][] distanceMatrix;
	public double[][] heuristicMatrix;
	public double[][] pheromoneMatrix;
	private int n;
	private double alpha;
	private double ro;
	private double tMin, tMax;
	
	public Graph(List<Node> nodes, double[][] distanceMatrix, double[][] heuristicMatrix, double[][] pheromoneMatrix,
			int n, double alpha, double ro, double tMin, double tMax) {
		super();
		this.nodes = nodes;
		this.distanceMatrix = distanceMatrix;
		this.heuristicMatrix = heuristicMatrix;
		this.pheromoneMatrix = pheromoneMatrix;
		this.n = n;
		this.alpha = alpha;
		this.tMin = tMin;
		this.tMax = tMax;
		this.ro = ro;
	}
	
	public void pheromoneTrail(Ant ant) {
		for (int i=0; i<n; i++) {
			int j = ant.getVisited(i).getIndex();
			int k = ant.getVisited((i+1)%n).getIndex();
			pheromoneMatrix[j][k] += ant.fitness;
			pheromoneMatrix[j][k] = Math.min(pheromoneMatrix[j][k], tMax);
		}
	}
	
	public void evaporate() {
		for (int i=0; i<n; i++) {
			for (int j=0; j<n; j++) {
				pheromoneMatrix[i][j] *= (1-ro);
				pheromoneMatrix[i][j] = Math.max(pheromoneMatrix[i][j], tMin);
			}
		}
	}
	
	public double getTau(int i, int j) {
		return Math.pow(pheromoneMatrix[i][j], alpha);
	}
	
	public double getHeuristic(int i, int j) {
		return heuristicMatrix[i][j];
	}
	
	public double getDistance(int i, int j) {
		return distanceMatrix[i][j];
	}
	
	public int getN() {
		return n;
	}
	
	public List<Node> getNodes() {
		return nodes;
	}
	
	

}
