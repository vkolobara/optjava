package hr.fer.zemris.optjava.dz6;

import java.util.List;

public class Graph {
	
	private List<Node> nodes;
	private double[][] distanceMatrix;
	private double[][] heuristicMatrix;
	private double[][] pheromoneMatrix;
	private int n;
	private double alpha;
	
	public Graph(List<Node> nodes, double[][] distanceMatrix, double[][] heuristicMatrix, double[][] pheromoneMatrix,
			int n, double alpha, double beta) {
		super();
		this.nodes = nodes;
		this.distanceMatrix = distanceMatrix;
		this.heuristicMatrix = heuristicMatrix;
		this.pheromoneMatrix = pheromoneMatrix;
		this.n = n;
		this.alpha = alpha;
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
