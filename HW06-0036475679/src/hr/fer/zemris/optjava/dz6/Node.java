package hr.fer.zemris.optjava.dz6;

import java.util.LinkedList;
import java.util.List;

public class Node {
	
	private List<Node> candidateList;
	private int index;
	
	public Node(int index) {
		this.index = index;
		candidateList = new LinkedList<>();
	}
	
	public int getIndex() {
		return index;
	}
	
	public List<Node> getCandidateList() {
		return candidateList;
	}
	

}
