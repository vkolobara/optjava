package hr.fer.zemris.optjava.gp;

import hr.fer.zemris.optjava.gp.tree.Tree;

public class Eater {

	private static final int UP = 0;
	private static final int LEFT = 1;
	private static final int DOWN = 2;
	private static final int RIGHT = 3;

	private static final int CONST_NUM = 4;

	private static final int MAX_ACTIONS = 600;
	private int currActions;
	private Tree tree;
	private int currX, currY;
	private AntMap map;
	private int direction;
	private boolean done;
	private int foodEaten;
	private double fitness;

	public Eater(Tree tree) {
		direction = RIGHT;
		done = false;
		currX = currY = 0;
		currActions = 0;
		fitness = 0;
		foodEaten = 0;
		this.tree = tree.duplicate();
	}

	public void move() {
		if (direction % 2 == 0) {
			currY = (currY + direction - 1) % map.getHeight();
		} else {
			currX = (currX + direction - 2) % map.getWidth();
		}
		action();

	};

	private void action() {
		currActions++;
		if (map.getFoodMap()[currY][currX]) {
			foodEaten++;
			map.getFoodMap()[currY][currX] = false;
		}
		if (currActions == MAX_ACTIONS)
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
		return (direction % 2 == 0 && map.getFoodMap()[(currY + direction - 1) % map.getHeight()][currX])
				|| (direction % 2 == 1 && map.getFoodMap()[currY][(currX + direction - 2) % map.getHeight()]);
	};

	public boolean isDone() {
		return done;
	}

}
