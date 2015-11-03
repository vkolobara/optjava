package hr.fer.zemris.optjava.dz5.part2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class GeneticAlgorithm {

	public static int[][] c;
	public static int[][] d;
	private static int n;
	
	public static void main(String[] args) {
		try {
			readFile("./data/nug12.dat");
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void readFile(String path) throws IOException {
		List<String> rows = Files.readAllLines(Paths.get(path));
		
		int i=0;
		
		String row = rows.get(i++);
		n = Integer.parseInt(row);
		i++;
		
		c = new int[n][n];
		d = new int[n][n];
		
		fillMatrix(rows, i, d);
		i++;
		fillMatrix(rows, i, c);
			
	}

	private static void fillMatrix(List<String> rows, int i, int[][] mat) {
		String row;
		int j=0;
		while(true) {
			row = rows.get(i++);
			if (row.isEmpty()) break;
			String[] values = row.split("\\s+");
			
			int k=0;
			for (String val : values) {
				mat[j][k++] = Integer.parseInt(val);
			}
			j++;

		}
	}
	
}

