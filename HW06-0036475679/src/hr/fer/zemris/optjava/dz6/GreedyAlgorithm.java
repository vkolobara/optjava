package hr.fer.zemris.optjava.dz6;

import java.util.LinkedList;
import java.util.List;

public class GreedyAlgorithm {
	
	public static double run(List<Node> nodes, double[][] distanceMatrix) {
		
		double best = 0;
		int n = nodes.size();
		Node start = nodes.get(0);
		Node current = start;
		List<Node> visited = new LinkedList<>();
		visited.add(start);
		List<Node> neighbors = start.getNeighborList();
		
		for (int i=1; i<n; i++){	
			Node next = nodes.get(neighbors.get(0).getIndex());
			best += distanceMatrix[current.getIndex()][next.getIndex()];
			neighbors = next.getNeighborList();
			neighbors.removeAll(visited);
			visited.add(next);
			current = next;
		}

		return best + distanceMatrix[current.getIndex()][start.getIndex()];
	}
	
}
