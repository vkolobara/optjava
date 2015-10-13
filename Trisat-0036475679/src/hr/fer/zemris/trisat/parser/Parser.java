package hr.fer.zemris.trisat.parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

import hr.fer.zemris.trisat.Clause;

public class Parser {

	private int numberOfVariables;
	private int numberOfClauses;
	private String path;
	private Clause[] clauses;
	
	public Parser(String path) {
		this.path = path;
	}
	
	public void parse() throws IOException {
		List<String> rows = Files.readAllLines(Paths.get(path));
		removeRows(rows);
		parseFirst(rows.get(0));	
		rows.remove(0);
		parseRest(rows);
	}

	public int getNumberOfVariables() {
		return numberOfVariables;
	}

	public int getNumberOfClauses() {
		return numberOfClauses;
	}

	public String getPath() {
		return path;
	}

	public Clause[] getClauses() {
		return clauses;
	}

	private void parseRest(List<String> rows) {
		clauses = new Clause[rows.size()];
		int i=0;
		for (String row : rows) {
			String[] splitted = row.trim().split("\\s+");
			int[] indexes = new int[splitted.length-1];
			for (int j=0; j<indexes.length; j++) {
				indexes[j] = Integer.parseInt(splitted[j]);
			}

			clauses[i++] = new Clause(indexes);
		}
	}

	private void parseFirst(String row) {
		String[] splitted = row.split("\\s+");
		numberOfVariables = Integer.parseInt(splitted[2]);
		numberOfClauses = Integer.parseInt(splitted[3]);
		
	}

	private void removeRows(List<String> rows) {
		Iterator<String> it = rows.iterator();
		
		while(it.hasNext()) {
			String next = it.next();
			if (next.startsWith("c")) {
				it.remove();
			} else if (next.startsWith("%")) {
				it.remove();
				while (it.hasNext()) {
					it.next();
					it.remove();
				}
			}
		}
	}
	
}
