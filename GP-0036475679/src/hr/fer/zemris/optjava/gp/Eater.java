package hr.fer.zemris.optjava.gp;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.optjava.gp.tree.Tree;

public class Eater implements Comparable<Eater>{
public class PointDirection{
	private Point point;
	private int direction;
	
	public PointDirection(Point point, int direction) {
		this.point = point;
		this.direction = direction;
	}
	
	public Point getPoint() {
		return point;
	}
	
	public int getDirection() {
		return direction;
	}
	
}
	public static final int UP = 0;
	public static final int LEFT = 1;
	public static final int DOWN = 2;
	public static final int RIGHT = 3;

	private static final int CONST_NUM = 4;

	private static final int MAX_ACTIONS = 600;
	private int currActions;
	private Tree tree;
	private int currX, currY;
	public static AntMap map;
	private int direction;
	private boolean done;
	private int foodEaten;
	private double fitness;
	private List<PointDirection> visited;

	public Eater(Tree tree) {
		direction = RIGHT;
		done = false;
		currX = currY = 0;
		currActions = 0;
		fitness = 0;
		foodEaten = 0;
		this.tree = tree.duplicate();
		visited = new ArrayList<>();
	}
	
	public void setFitness(double fitness) {
		this.fitness = fitness;
	}
	
	public void executeTree() {
		tree.execute(this);
	}
	
	public double getFitness() {
		return fitness;
	}
	
	public int getFoodEaten() {
		return foodEaten;
	}
	
	public void move() {
		if (direction % 2 == 0) {
			currY = Math.floorMod((currY + direction - 1), map.getHeight());
		} else {
			currX = Math.floorMod((currX + direction - 2), map.getWidth());
		}
		action();

		
	};
	
	public List<PointDirection> getVisited() {
		return visited;
	}

	private void action() {
		currActions++;
		visited.add(new PointDirection(new Point(currX, currY), direction));
		if (map.getFoodMap()[currY][currX]) {
			foodEaten++;
			map.getFoodMap()[currY][currX] = false;
		}
		if (currActions == MAX_ACTIONS || foodEaten == map.getFoodNum())
			done = true;
	}

	public void right() {
		direction = (direction + RIGHT) % CONST_NUM;
		action();
	};

	public void left() {
		direction = (direction + LEFT) % CONST_NUM;
		action();
	};

	public boolean isFoodAhead() {
		return (direction % 2 == 0 && map.getFoodMap()[Math.floorMod((currY + direction - 1), map.getHeight())][currX])
				|| (direction % 2 == 1 && map.getFoodMap()[currY][Math.floorMod((currX + direction - 2), map.getHeight())]);
	};

	public boolean isDone() {
		return done;
	}
	
	public Tree getTree() {
		return tree;
	}

	@Override
	public int compareTo(Eater o) {
		return Double.compare(fitness, o.fitness);
	}

}
