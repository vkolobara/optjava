package hr.fer.zemris.optjava.dz12.parser;

import java.util.HashMap;
import java.util.Map;

public class Parser {
	public static Map<String, Integer> nazivLokacijaMapa = new HashMap<>();
	private boolean[] truthTable;
	private int numOfVariables = 0;
	private int clbNum = 0;
	private String input;

	public Parser(String expression, int clbNum) {
		this.input = expression;
		this.clbNum = clbNum;
	}

	public Node parseAndCalculateTruth() {
		Node node = parse(input);
		truthTable = calculateTruth(node);
		for (int i=numOfVariables; i<clbNum+numOfVariables; i++) {
			nazivLokacijaMapa.put("CLB" + (i-numOfVariables), i);
		}
		
		return node;
	}
	
	public boolean[] getTruthTable() {
		return truthTable;
	}

	private boolean[] calculateTruth(Node node) {
		boolean[] truthTable = new boolean[(int) Math.pow(2, numOfVariables)];
		Node.values.clear();
		for (int i=0; i<numOfVariables; i++) {
			Node.values.addElement(false);
		}
		for (int i = 0; i < truthTable.length; i++) {
			String s = Integer.toBinaryString(i);
			while (s.length() < numOfVariables) {
				s = '0' + s;
			}

			int j = 0;
			for (char c : s.toCharArray()) {
				Node.values.set(j++, c == '0' ? false : true); 
			}

			truthTable[i] = node.getValue();
		}
		
		return truthTable;
	}
	
	public Map<String, Integer> getNazivLokacijaMapa() {
		return nazivLokacijaMapa;
	}
	
	public int getNumOfVariables() {
		return numOfVariables;
	}

	private Node parse(String current) {
		current = current.trim();
		Node currNode = null;
		int len = current.length();
		int brackets = 0;
		boolean flag = false;
		if (current.startsWith("(")) {
			brackets++;
			for (int i = 1; i < len; i++) {
				if (current.charAt(i) == '(')
					brackets++;
				else if (current.charAt(i) == ')')
					brackets--;
				if (i == len - 1)
					flag = true;
				if (brackets == 0)
					break;
			}
		}

		if (flag)
			current = current.substring(1, current.length() - 1);
		len = current.length();

		brackets = 0;
		for (int i = 0; i < len; i++) {
			if (current.charAt(i) == '(')
				brackets++;
			else if (current.charAt(i) == ')')
				brackets--;
			else if (current.charAt(i) == ' ')
				continue;
			else if (brackets == 0) {
				if (current.substring(i).startsWith("OR")) {
					currNode = new Function2ArgNode((a, b) -> a | b);
					currNode.left = parse(current.substring(0, i).trim());
					currNode.right = parse(current.substring(i + 2).trim());
				} else if (current.substring(i).startsWith("AND")) {
					currNode = new Function2ArgNode((a, b) -> a & b);
					currNode.left = parse(current.substring(0, i).trim());
					currNode.right = parse(current.substring(i + 3).trim());
				} else if (current.substring(i).startsWith("NOT")) {
					currNode = new Function1ArgNode((a) -> !a);
					currNode.left = parse(current.substring(i + 3));
				} else if (!current.contains(" ")) {
					String name = current.substring(i, len);
					if (!nazivLokacijaMapa.containsKey(name)) {
						nazivLokacijaMapa.put(name, numOfVariables++);
					} 
					currNode = new VariableNode(name);
				} else {
					continue;
				}
				break;
			}

		}
		return currNode;

	}

}
