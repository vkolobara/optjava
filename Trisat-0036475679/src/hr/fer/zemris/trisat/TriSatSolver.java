package hr.fer.zemris.trisat;

import java.io.IOException;

import hr.fer.zemris.trisat.algorithms.Algorithm;
import hr.fer.zemris.trisat.algorithms.Algorithm1;
import hr.fer.zemris.trisat.algorithms.Algorithm2;
import hr.fer.zemris.trisat.algorithms.Algorithm3;
import hr.fer.zemris.trisat.parser.Parser;

public class TriSatSolver {

	public static void main(String[] args) {

		if (args.length != 2) {
			System.err.println(
					"Potrebna 2 argumenta:\n" + "1. - redni broj algoritma kojeg se treba koristiti (1, 2, 3)\n"
							+ "2. - putanja do konfiguracijske datoteke");
			System.exit(0);
		}

		int algNum = 313;
		try {
			algNum = Integer.parseInt(args[0]);
			if (algNum < 1 || algNum > 3) {
				throw new IllegalArgumentException();
			}
		} catch (Exception e) {
			System.err.println("Broj algoritma mora biti iz skupa (1, 2, 3).");
			System.exit(0);
		}

		Parser parser = new Parser(args[1]);
		try {
			parser.parse();
		} catch (IOException e) {
			System.err.println("Ne postoji datoteka: " + args[1] + ".");
			System.exit(0);
		}

		SATFormula formula = new SATFormula(parser.getNumberOfVariables(), parser.getClauses());
		Algorithm alg = null;

		switch (algNum) {
		case 1:
			alg = new Algorithm1(formula);
			break;
		case 2:
			alg = new Algorithm2(formula);
			break;
		case 3:
			alg = new Algorithm3(formula);
			break;
		default:
			break;
		}

		BitVector solution = alg.execute();

		if (formula.isSatisfied(solution)) {
			System.out.println("Zadovoljivo: " + solution);
		} else {
			System.out.println("Rješenje nije pronađeno nakon maksimalnog broja iteracija.");
		}

	}

}