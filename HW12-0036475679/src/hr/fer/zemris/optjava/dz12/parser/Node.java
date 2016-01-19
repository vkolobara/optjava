package hr.fer.zemris.optjava.dz12.parser;

public abstract class Node {
	protected Node left;
	protected Node right;

	public boolean getValue() {
		return false;
	};

	public void setLeft(Node left) {
		this.left = left;
	}

	public void setRight(Node right) {
		this.right = right;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		if (left != null) {
			sb.append(left + " ");
		} 
		sb.append(getClass());
		if (right != null) {
			sb.append(" " + right);
		}
		return sb.toString();
	}
}
