package hr.fer.zemris.optjava.gp.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import hr.fer.zemris.optjava.gp.Eater;
import hr.fer.zemris.optjava.gp.tree.function.Function;
import hr.fer.zemris.optjava.gp.tree.terminal.Terminal;
import hr.fer.zemris.optjava.gp.tree.Node;

public class Tree {

	private Node root;

	private List<Function> functionSet;
	private List<Terminal> terminalSet;
	private static Random rand = new Random();

	public Tree() {
		functionSet = new ArrayList<>();
		terminalSet = new ArrayList<>();
	}
	
	public Node getRoot() {
		return root;
	}
	
	public Node getAtIndex(int index) {
		return getAtIndex(index, root);
	}
	
	private Node getAtIndex(int index, Node currNode) {
		if (index == 0) return currNode;
		index--;
		for (Node child : currNode.children){
			int size = child.size();
			if (index < size) {
				return getAtIndex(index, child);
			} else {
				index -= size;
			}
		}
		
		return null;
	}
	
	public void setAtIndex(int index, Node node) {
		setAtIndex(index, root, node);
	}
	
	private void setAtIndex(int index, Node currNode, Node node) {
		if (index < currNode.numOfChildren()) currNode.children.set(index, node);
		else {
			index--;
			int num = currNode.getChildren().size();
			for (int i=0; i<num; i++) {
				int size = currNode.getChildAt(i).size();
				if (index < size) {
					setAtIndex(index, currNode.getChildAt(i), node);
					break;
				} else {
					index -= size;
				}
			}
		}
	}

 
	public void execute(Eater eater) {
		root.execute(eater);
	};
	
	public void addPrimitive(Primitive primitive) {
		if (primitive instanceof Function) {
			functionSet.add((Function) primitive);
		} else {
			terminalSet.add((Terminal) primitive);
		}
	}

	public void buildTreeRampedHalfAndHalf(int maxDepth, int currDepth) {
		root = new Node(currDepth);
		root.setPrimitive(functionSet.get(rand.nextInt(functionSet.size())));
		int childrenSize = root.getPrimitive().numOfArgs;
		List<Node> children = new ArrayList<>();

		for (int i = 0; i < childrenSize; i++) {
			children.add(rampedBuild(maxDepth, currDepth + 1));
		}
		
		root.setChildren(children);
	}

	public Node rampedBuild(int maxDepth, int currDepth) {

		Node novi;
		if (rand.nextBoolean()) {
			novi = fullBuild(maxDepth, currDepth);
		} else {
			novi = growBuild(maxDepth, currDepth);
		}

		return novi;
	}

	public Node fullBuild(int maxDepth, int currDepth) {
		if (currDepth + 1 == maxDepth) {
			Node novi = new Node(currDepth);
			novi.setPrimitive(terminalSet.get(rand.nextInt(terminalSet.size())));
			novi.setChildren(new ArrayList<>());
			return novi;
		}

		Node novi = new Node(currDepth);
		novi.setPrimitive(functionSet.get(rand.nextInt(functionSet.size())));
		int childrenSize = novi.getPrimitive().numOfArgs;
		List<Node> children = new ArrayList<>();

		for (int i = 0; i < childrenSize; i++) {
			children.add(rampedBuild(maxDepth, currDepth + 1));
		}
		
		novi.setChildren(children);

		return novi;

	}

	public Node growBuild(int maxDepth, int currDepth) {
		Node novi = new Node(currDepth);

		if (rand.nextBoolean() && currDepth + 1 < maxDepth) {
			novi.setPrimitive(functionSet.get(rand.nextInt(functionSet.size())));
			int size = novi.getPrimitive().numOfArgs;
			List<Node> children = new ArrayList<>();

			for (int i = 0; i < size; i++) {
				children.add(rampedBuild(maxDepth, currDepth + 1));
			}
			novi.setChildren(children);

		} else {
			novi.setPrimitive(terminalSet.get(rand.nextInt(terminalSet.size())));
			novi.setChildren(new ArrayList<>());
		}

		return novi;
	}
	
	public int size() {
		return root.size();
	}

	public Tree duplicate() {
		Tree dupl = new Tree();
		dupl.functionSet = functionSet;
		dupl.terminalSet = terminalSet;

		Node newRoot = root.duplicate();
		dupl.root = newRoot;
		
		return dupl;
	}
	
	@Override
	public String toString() {
		return root.toString();		
	}

}
