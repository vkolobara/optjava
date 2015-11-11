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
	
	public void addCandidate(Node node) {
		candidateList.add(node);
	}
	
	public int getIndex() {
		return index;
	}
	
	public List<Node> getCandidateList() {
		return new LinkedList<>(candidateList);
	}
	
	@Override
	public String toString() {
		return ""+index;
	}
	
	@Override
	public int hashCode() {
		return Integer.hashCode(index);
	}
	
	@Override
	public boolean equals(Object obj) {
		return index == ((Node) obj).index;
	}
	

}
