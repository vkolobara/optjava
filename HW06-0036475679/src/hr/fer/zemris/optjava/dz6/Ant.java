package hr.fer.zemris.optjava.dz6;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Ant extends Solution {

	public List<Node> visited;
	private Node current;
	public double distance;
	private Graph graph;
	private Random rand;

	public Ant(Graph graph, Node start) {
		current = start;
		visited = new LinkedList<>();
		visited.add(current);
		this.graph = graph;
		rand = new Random();
	}

	public Node getVisited(int i) {
		return visited.get(i);
	}
	
	public void setFirstTown() {
		while(visited.get(0).getIndex() != 0) {
			Collections.rotate(visited, 1);
		}
	}
	
	public void move() {

		List<Node> candidates = current.getCandidateList();
		candidates.removeAll(visited);
		if (candidates.isEmpty()) {
			candidates.addAll(current.getNeighborList());
			candidates.removeAll(visited);
		}
		List<Range> ranges = new LinkedList<>();

		double sum = 0;
		double[] p = new double[candidates.size()];

		int i = 0;
		for (Node candidate : candidates) {
			int j = current.getIndex();
			int k = candidate.getIndex();
			p[i] = graph.getTau(j, k) * graph.getHeuristic(j, k);
			sum += p[i++];
		}

		double currLow = 0;
		for (i = 0; i < p.length; i++) {
			double val = 1.0 * p[i] / sum;
			ranges.add(new Range(currLow, currLow+val));
			currLow += val;
		}

		double pVal = rand.nextDouble();

		i = 0;
		for (Range range : ranges) {
			if (range.isInRange(pVal)){
				break;
			}
			i++;
		}
		Node next = graph.getNodes().get(candidates.get(i).getIndex());
		distance += graph.getDistance(current.getIndex(), next.getIndex());
		visited.add(next);
		current = next;

		if (isFinished()) {
			distance += graph.getDistance(current.getIndex(), visited.get(0).getIndex());
		}
	}
	
	public boolean isFinished() {
		return visited.size() == graph.getN();
	}

	@Override
	public Solution duplicate() {
		return null;
	}

	@Override
	public Solution newLikeThis() {
		return null;
	}

	@Override
	public void setFitness() {
		fitness = 1.0 / distance;
	}
	
	public void clear() {
		distance = 0;
		visited.clear();
		fitness = 0;
		current = graph.getNodes().get(rand.nextInt(graph.getN()));
	}

	@Override
	public void randomize() {
	}

	@Override
	public int getSize() {
		return 0;
	}

}
