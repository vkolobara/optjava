package hr.fer.zemris.optjava.dz6;

import java.util.LinkedList;
import java.util.List;

public class Ant extends Solution {

	private List<Node> visited;
	private Node current;
	public double distance;
	
	public Ant(Node start) {
		current = start;
		visited = new LinkedList<>();
		visited.add(current);
	}
	
	public void move(Graph graph) {
		
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
		fitness = 1/distance;
		visited.clear();
		distance = 0;
		
	}

	@Override
	public void randomize() {		
	}

	@Override
	public int getSize() {
		return 0;
	}
	
}
