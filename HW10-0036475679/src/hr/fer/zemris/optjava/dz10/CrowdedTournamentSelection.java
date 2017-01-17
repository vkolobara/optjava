package hr.fer.zemris.optjava.dz10;
import java.util.Random;

public class CrowdedTournamentSelection {

	private Random rand;
	
	public CrowdedTournamentSelection() {
		rand = new Random();
	}
	
	public DoubleArraySolution[] select(Population pop) {

		DoubleArraySolution p1 = pop.getPopulation().get(rand.nextInt(pop.getSize()));
		DoubleArraySolution p2 = pop.getPopulation().get(rand.nextInt(pop.getSize()));
		
		DoubleArraySolution p3 = pop.getPopulation().get(rand.nextInt(pop.getSize()));
		DoubleArraySolution p4 = pop.getPopulation().get(rand.nextInt(pop.getSize()));

		return new DoubleArraySolution[] { p1.compareTo(p2) > 0 ? p1 : p2, p3.compareTo(p4) > 0 ? p3 : p4 };
	}

	
}
