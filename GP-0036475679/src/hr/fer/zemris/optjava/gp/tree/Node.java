package hr.fer.zemris.optjava.gp.tree;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.optjava.gp.Eater;

public class Node {

	private int depth;
	private Primitive primitive;
	public List<Node> children;

	public Node(int depth) {
		this.depth = depth;
		children = new ArrayList<>();
	}
	
	public int getDepth() {
		return depth;
	}
	
	public void setDepth(int depth) {
		this.depth = depth;
		for (Node node : children) {
			node.setDepth(depth+1);
		}
		
	}

	public Node getChildAt(int index) {
		return children.get(index);
	}
	
	public List<Node> getChildren() {
		return children;
	}

	public Primitive getPrimitive() {
		return primitive;
	}

	public void setPrimitive(Primitive primitive) {
		this.primitive = primitive;
	}

	public void setChildren(List<Node> children) {
		this.children = children;
	}

	public Node duplicate() {
		Node dupl = new Node(depth);
		dupl.setPrimitive(primitive);

		int size = children.size();
		List<Node> newChildren = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			newChildren.add(getChildAt(i).duplicate());
		}
		dupl.setChildren(newChildren);
		
		return dupl;
	}

	public void execute(Eater eater) {
		primitive.execute(eater, this);
	};
	
	public int getMostDeep() {
		if (children == null || children.isEmpty()) {
			return this.depth;
		}
		int max = 0;
		for(Node child : children) {
			int depth = child.getMostDeep();
			if (depth > max) max = depth;
		}
		return max;
	}

	public int numOfChildren() {
		return children.size();
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i=0; i<depth; i++) {
			sb.append("  ");
		}
		
		sb.append(primitive.toString()+"\n");
		int size = primitive.getNumOfArgs();
				
		for (int i=0; i<size; i++) {
			sb.append(getChildAt(i).toString());
		}
		
		return sb.toString();
	}

	public int size() {
		int sum = 1;
		for (Node child : children) {
			if (child == null) continue;
			sum += child.size();
		}
		return sum;
	}


}
