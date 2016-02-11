package hr.fer.zemris.optjava.gp.tree;

import hr.fer.zemris.optjava.gp.AntMap;
import hr.fer.zemris.optjava.gp.Eater;
import hr.fer.zemris.optjava.gp.tree.function.IfFoodAhead;
import hr.fer.zemris.optjava.gp.tree.function.Prog2;
import hr.fer.zemris.optjava.gp.tree.function.Prog3;
import hr.fer.zemris.optjava.gp.tree.terminal.Left;
import hr.fer.zemris.optjava.gp.tree.terminal.Move;
import hr.fer.zemris.optjava.gp.tree.terminal.Right;

public class Algorithm {
	
	double p;
	int n;
	int pop_size;
	int max_iter;
	double min_fit;
	
	private final static int INIT_DEPTH = 6;
	
	public Algorithm(double p, int n, int pop_size, int max_iter, double min_fit, AntMap map) {
		this.p = p;
		this.n = n;
		this.pop_size = pop_size;
		this.max_iter = max_iter;
		this.min_fit = min_fit;
		Eater.map = map;

	}

	public Eater run() {
		Population pop = initPopulation();
		pop.getPopulation().forEach(e -> evaluate(e));

		for (int t=1; pop.getBest().getFitness() < min_fit && t<=max_iter; t++) {
			System.out.println("Generacija " + t + ": Fitness - " + pop.getBest().getFitness());
			Population newPop = new Population(pop_size);
			newPop.add(pop.getBest());
			while (!newPop.isFull()) {
				Eater p1 = GeneticOperators.tournamentSelection(pop, n);
				Eater p2 = GeneticOperators.tournamentSelection(pop, n);
				
				Tree child = GeneticOperators.crossover(p1.getTree(), p2.getTree());
				child = GeneticOperators.mutate(child);
				Eater e = new Eater(child);
				evaluate(e);
				if (e.getFoodEaten() == p1.getFoodEaten()) {
					e.setFitness(e.getFitness()*p);
				}
				newPop.add(e);
			}
			pop = newPop;
		}
		
		return pop.getBest();
	}

	private void evaluate(Eater e) {
		while (!e.isDone()) {
			e.executeTree();
		}
		Eater.map.restore();
		e.setFitness(e.getFoodEaten());
	}

	private Population initPopulation() {
		Population pop = new Population(pop_size);
		while (!pop.isFull()) {
			Tree tree = new Tree();
			tree.addPrimitive(new IfFoodAhead());
			tree.addPrimitive(new Prog2());
			tree.addPrimitive(new Prog3());
			tree.addPrimitive(new Left());
			tree.addPrimitive(new Move());
			tree.addPrimitive(new Right());
			tree.buildTreeRampedHalfAndHalf(INIT_DEPTH, 0);
			Eater eater = new Eater(tree);
			pop.add(eater);
		}
		return pop;
	}
	
	
}
