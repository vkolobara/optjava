package hr.fer.zemris.optjava.dz11;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import hr.fer.zemris.art.GrayScaleImage;
import hr.fer.zemris.generic.ga.GAAlgorithm;
import hr.fer.zemris.generic.ga.GAAlgorithm1;
import hr.fer.zemris.generic.ga.IntegerArraySolution;
import hr.fer.zemris.optjava.rng.EVOThread;

public class Pokretac1 {

	public static void main(String[] args) throws Exception {
//
//		String originalImage = "11-kuca-200-133.png";
//		int N = 200;
//		int pop_size = 200;
//		int max_iter = 1000;
//		double merr = -1;
//		String params = "params.txt";
//		String bestImage = "slika.png";
		
		if (args.length != 7) {
			System.err.println("Potrebno 7 argumenata!");
			return;
		}
		
		String originalImage = args[0];
		int N = Integer.parseInt(args[1]);
		int pop_size = Integer.parseInt(args[2]);
		int max_iter = Integer.parseInt(args[3]);
		double merr = Double.parseDouble(args[4]);
		String params = args[5];
		String bestImage = args[6];
		
		
		GrayScaleImage slika = GrayScaleImage.load(new File(originalImage));
		
		int[] min = {0, 0, 0, 1, 1, 0};
		int[] max = {255, slika.getWidth(), slika.getHeight(), slika.getWidth(), slika.getHeight(), 255};
		
		EVOThread thread = new EVOThread(new Runnable() {
			
			@Override
			public void run() {
				GAAlgorithm alg = new GAAlgorithm1(min, max, max_iter, pop_size, N, merr, originalImage, bestImage);
				try {
					IntegerArraySolution sol = alg.run();
					write(params, sol.data);

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			}
		});
		
		thread.start();		
	}
	
	private static void write(String path, int[] var) throws IOException {
		BufferedWriter outputWriter = null;
		  outputWriter = new BufferedWriter(new FileWriter(path));
		  for (int i = 0; i < var.length; i++) {
		    outputWriter.write(var[i]+"");		  
		    outputWriter.newLine();
		  }
		  outputWriter.flush();  
		  outputWriter.close();  
	}
}
