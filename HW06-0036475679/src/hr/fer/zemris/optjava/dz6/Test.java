package hr.fer.zemris.optjava.dz6;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;

/**
 * Created by Vinko on 09.11.2015.
 */
public class Test {

	private static double[][] heuristicMatrix;
	private static double[][] distanceMatrix;
	private static int n;

	public static void main(String[] args) throws IOException {

		double tMin, tMax;
		double alpha, beta, ro;
		int a, maxIter, k, l;
		String path;

		alpha = 1;
		beta = 2;
		a = 100;
		path = "TSP/bays29.tsp";
		maxIter = 150;
		l = 30;
		tMax = 0.001;
		tMin = 0;
		ro = 0.02;

		readFile(path, beta);
		k = n;

		List<Node> nodes = fillCandidateList(k);

		Graph graph = new Graph(nodes, distanceMatrix, heuristicMatrix, generatePheromoneMap(tMax), n, alpha, ro, tMin,
				tMax);

		AntAlgorithm alg = new AntAlgorithm(maxIter, graph, l);

		System.out.println(alg.run().distance);

	}

	private static double[][] generatePheromoneMap(double t0) {
		double[][] pheromoneMap = new double[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				pheromoneMap[i][j] = t0;
			}
		}

		return pheromoneMap;
	}

	private static List<Node> fillCandidateList(int k) {
		List<Node> nodeList = new LinkedList<>();
		for (int i = 0; i < n; i++) {
			Node node = new Node(i);
			List<Node> candidates = new LinkedList<>();

			for (int j = 0; j < n; j++) {
				if (i == j)
					continue;
				candidates.add(new Node(j));
			}

			Collections.sort(candidates, new Comparator<Node>() {

				@Override
				public int compare(Node o1, Node o2) {
					return Double.compare(distanceMatrix[node.getIndex()][o1.getIndex()],
							distanceMatrix[node.getIndex()][o2.getIndex()]);
				}
			});

			int size = candidates.size();

			for (int j = 0; j < k && j < size; j++) {
				node.addCandidate(candidates.get(j));
			}

			nodeList.add(node);

		}

		return nodeList;

	}

	private static void readFile(String path, double beta) throws IOException {

		List<String> rows = Files.readAllLines(Paths.get(path));

		Iterator<String> it = rows.iterator();

		while (it.hasNext()) {
			String row = it.next();

			if (row.startsWith("DIMENSION")) {
				n = Integer.parseInt(row.trim().split(": ")[1]);
				heuristicMatrix = new double[n][n];
				distanceMatrix = new double[n][n];
			} else if (row.startsWith("EDGE_WEIGHT_SECTION")) {
				it.remove();
				readMatrix(rows, beta);
				break;
			} else if (row.startsWith("NODE_COORD_SECTION")) {
				it.remove();
				readNodes(rows, beta);
				break;
			}
			it.remove();
		}

	}

	private static void readMatrix(List<String> rows, double beta) {
		int i = 0, j;
		for (String row : rows) {
			if (row.matches("^[a-zA-Z].*$"))
				break;
			String[] split = row.trim().split("\\s+");

			j = 0;

			for (String el : split) {
				double num = Double.parseDouble(el);
				heuristicMatrix[i][j] = Math.pow(1.0 / num, beta);
				distanceMatrix[i][j++] = num;
			}

			i++;
		}

	}

	private static void readNodes(List<String> rows, double beta) {
		List<Point> nodes = new LinkedList<>();
		for (String row : rows) {
			if (row.matches("^[a-zA-Z].*$"))
				break;

			String[] split = row.split("\\s+");
			nodes.add(new Point(Double.parseDouble(split[1]), Double.parseDouble(split[2])));

		}

		nodesToMatrix(nodes, beta);

	}

	private static void nodesToMatrix(List<Point> nodes, double beta) {
		int n = nodes.size();
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				double num = nodes.get(i).distance(nodes.get(j));
				heuristicMatrix[i][j] = Math.pow(1.0 / num, beta);
				distanceMatrix[i][j++] = num;
			}
		}
	}

}
