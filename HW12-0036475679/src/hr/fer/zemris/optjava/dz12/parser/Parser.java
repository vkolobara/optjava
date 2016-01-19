package hr.fer.zemris.optjava.dz12.parser;

import java.util.HashMap;
import java.util.Map;

public class Parser {
	protected static Map<String, Integer> nazivLokacijaMapa = new HashMap<>();
	public static boolean[] truthTable;
	public static boolean[] values;
	public static int numOfVariables = 0;

	public static void main(String[] args) {

		int clbs = 6;

		String input = ("((NOT S) AND A) OR (S AND B)");
		Node node = parse(input);
		truthTable = new boolean[(int) Math.pow(2, numOfVariables)];
		values = new boolean[numOfVariables + clbs];

		for (int i = 0; i < truthTable.length; i++) {
			String s = Integer.toBinaryString(i);
			while (s.length() != numOfVariables) {
				s = '0' + s;
			}

			int j = 0;
			for (char c : s.toCharArray()) {
				values[j++] = c == '0' ? false : true;
			}

			truthTable[i] = node.getValue();
		}

	}

	public static Node parse(String current) {
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
					currNode = new VariableNode(current.substring(i, len));
					if (!nazivLokacijaMapa.containsKey(((VariableNode) currNode).getName()))
						nazivLokacijaMapa.put(((VariableNode) currNode).getName(), numOfVariables++);
				} else {
					continue;
				}
				break;
			}

		}
		return currNode;

	}

}
