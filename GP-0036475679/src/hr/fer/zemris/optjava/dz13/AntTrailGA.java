package hr.fer.zemris.optjava.dz13;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import javax.swing.SwingUtilities;

import hr.fer.zemris.optjava.gp.AntMap;
import hr.fer.zemris.optjava.gp.Eater;
import hr.fer.zemris.optjava.gp.tree.Algorithm;
import hr.fer.zemris.optjava.gui.EaterGUI;


public class AntTrailGA {

	/**
	 * 
	 * @param args putanja do datoteke s putem, max generacija, veličina pop,
	 * 			   min fitness, putanja do datoteke za ispis najboljeg
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		
//		double p = 0.9;
//		int n = 7;
//		int pop_size = 500;
//		int max_iter = 100;
//		AntMap map = new AntMap("trail.txt");
//		double min_fit = map.getFoodNum();
		
		if (args.length != 5) {
			System.err.println("Pogrešan broj argumenata!");
			System.exit(1);
		}
		try {
			double p = 0.9;
			int n = 7;
			
			String inputPath = args[0];
			AntMap map = new AntMap(inputPath);
			int max_iter = Integer.parseInt(args[1]);
			int pop_size = Integer.parseInt(args[2]);
			double min_fit = Double.parseDouble(args[3]);
			String ispisPath = args[4];
			
			Algorithm alg = new Algorithm(p, n, pop_size, max_iter, min_fit, map);
			Eater e = alg.run();
			ispisi_u_datoteku(e, ispisPath);
			
			SwingUtilities.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					EaterGUI gui = new EaterGUI(map, e);
					gui.setSize(600,600);
					gui.setVisible(true);
				}
			});
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

	}

	private static void ispisi_u_datoteku(Eater e, String ispisPath) throws FileNotFoundException {
		PrintStream ispis = new PrintStream(new FileOutputStream(new File(ispisPath)));
		ispis.print(e.getTree());
		ispis.close();
	}
	
}
