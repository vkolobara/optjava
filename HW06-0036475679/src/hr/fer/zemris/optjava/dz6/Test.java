package hr.fer.zemris.optjava.dz6;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
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
	
    public static void main(String []args) throws IOException{

    	double alpha ,beta;
    	int a, maxIter, k, l;
    	String path;

        alpha = 1;
        beta = 1;
        a = 100;
        path = "TSP/bays29.tsp";
        maxIter = 250;
        k = 5;
        l = 30;


        readFile(path, beta);
        fillCandidateList();

        
        
        
    }

    private static void fillCandidateList() {


    }


    private static void readFile(String path, double beta) throws IOException{

        List<String> rows = Files.readAllLines(Paths.get(path));

        Iterator<String> it = rows.iterator();

        while (it.hasNext()) {
            String row = it.next();

            if (row.startsWith("DIMENSION")) {
                int  n = Integer.parseInt(row.trim().split(": ")[1]);
                heuristicMatrix = new double[n][n];
                distanceMatrix = new double[n][n];
            }
            else if (row.startsWith("EDGE_WEIGHT_SECTION")) {
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
        int i=0, j;
        for (String row : rows) {
            if (row.matches("^[a-zA-Z].*$")) break;
            String[] split = row.trim().split("\\s+");

            j=0;

            for (String el : split) {
                double num = Double.parseDouble(el);
                heuristicMatrix[i][j] = Math.pow(1.0/num, beta);
                distanceMatrix[i][j++] = num;
            }

            i++;
        }
        
    }

    private static void readNodes(List<String> rows, double beta) {
        List<Point> nodes = new LinkedList<>();
        for (String row : rows) {
            if (row.matches("^[a-zA-Z].*$")) break;

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
                heuristicMatrix[i][j] = Math.pow(1.0/num, beta);
                distanceMatrix[i][j++] = num;
            }
        }
    }


}
