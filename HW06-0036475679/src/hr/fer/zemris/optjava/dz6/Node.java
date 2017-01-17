package hr.fer.zemris.optjava.dz6;

import java.util.LinkedList;
import java.util.List;

public class Node {
	
	private List<Node> candidateList;
	private List<Node> neighborList;
	private int index;
	
	public Node(int index) {
		this.index = index;
		candidateList = new LinkedList<>();
		neighborList = new LinkedList<>();
	}
	
	public void addCandidate(Node node) {
		candidateList.add(node);
	}
	
	public void addNeighbor(Node node) {
		neighborList.add(node);
	}
	
	public int getIndex() {
		return index;
	}
	
	public List<Node> getCandidateList() {
		return new LinkedList<>(candidateList);
	}
	
	public List<Node> getNeighborList() {
		return new LinkedList<>(neighborList);
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
