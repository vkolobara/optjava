package hr.fer.zemris.generic.ga;

import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.art.GrayScaleImage;
import hr.fer.zemris.art.IscrtajTrenutni;
import hr.fer.zemris.optjava.hw11.Evaluator;
import hr.fer.zemris.optjava.hw11.Population;
import hr.fer.zemris.optjava.rng.EVOThread;

public class GAAlgorithm1 implements GAAlgorithm{

	protected int[] min, max;
	protected int max_iter;
	protected int pop_size;
	protected int N;
	protected double merr;
	protected String imagePath, bestPath;
	protected final static IntegerArraySolution POISON = new IntegerArraySolution(0);
	protected JFrame f;

	public GAAlgorithm1(int[] min, int[] max, int max_iter, int pop_size, int N, double merr, String imagePath, String bestImage) {
		this.min = min;
		this.max = max;
		this.max_iter = max_iter;
		this.pop_size = pop_size;
		this.N = N;
		this.merr = merr;
		this.imagePath = imagePath;
		this.bestPath = bestImage;
	}

	public IntegerArraySolution run() throws IOException {

		int cores = Runtime.getRuntime().availableProcessors();
		Evaluator evaluator = new Evaluator(GrayScaleImage.load(new File(imagePath)));
		int brojSlika = max_iter / 25;
		Population pop = randomizePop();
		Queue<IntegerArraySolution> zaIzracun = new ConcurrentLinkedQueue<>();
		Queue<IntegerArraySolution> izracunao = new ConcurrentLinkedQueue<>();
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				f = new JFrame("0. Generacija");
				IscrtajTrenutni original = new IscrtajTrenutni(imagePath);
				IscrtajTrenutni kopija = new IscrtajTrenutni(bestPath);
				f.getContentPane().setLayout(new GridLayout(1, 2));
				f.getContentPane().add(original);
				f.getContentPane().add(kopija);
				f.pack();
				f.setSize(200*2, 133 + f.getInsets().top + f.getInsets().bottom);
				f.setVisible(true);
				f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
				
			}
		});
		for (int i = 0; i < cores; i++) {
			EVOThread thread = new EVOThread(new Runnable() {

				private Evaluator evaluator = new Evaluator(GrayScaleImage.load(new File(imagePath)));

				@Override
				public void run() {
					synchronized (zaIzracun) {
						while (true) {
							if (zaIzracun.peek() == null)
								continue;
							if (zaIzracun.peek().getData().length == 1)
								return;
							IntegerArraySolution sol = zaIzracun.poll();
							evaluator.evaluate(sol);
							izracunao.offer(sol);
						}
					}
					
				}
			});

			thread.start();
		}

		for (int t = 0; t < max_iter; t++) {

			Population newPop = new Population(pop_size);
			newPop.addSolution(pop.getBest());
			while (!newPop.isFull()) {

				for (int i = 0; i < pop_size; i++) {
					IntegerArraySolution p1 = GeneticOperators.tournamentSelection(pop, 12);
					IntegerArraySolution p2 = GeneticOperators.tournamentSelection(pop, 12);
					IntegerArraySolution child = GeneticOperators.crossover(p1, p2);
					child = GeneticOperators.mutate(min, max, child);
					zaIzracun.offer(child);
				}

				for (int i = 0; i < pop_size; i++) {
					while (izracunao.peek() == null)
						;
					newPop.addSolution(izracunao.poll());
				}
			}

			pop = newPop;
			System.out.println("Generacija: " + (t+1) + "; Fitness: " + pop.getBest().fitness);
			
			if ((t+1) % brojSlika == 0) {
				GrayScaleImage im = evaluator.draw(pop.getBest(), null);
				im.save(new File("slika.png"));
				f.setTitle("Generacija " + (t+1));
				f.repaint();
			}

			if (pop.getBest().fitness > merr) break;

		}

		zaIzracun.offer(POISON);
		return pop.getBest();

	}

	private Population randomizePop() throws IOException {
		Evaluator evaluator = new Evaluator(GrayScaleImage.load(new File(imagePath)));

		Population pop = new Population(pop_size);

		while (!pop.isFull()) {
			IntegerArraySolution sol = new IntegerArraySolution(N);
			sol.randomize(min, max);
			evaluator.evaluate(sol);
			pop.addSolution(sol);
		}

		return pop;
	}
}
