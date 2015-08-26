package info.gridworld.maze;
import info.gridworld.actor.*;
import info.gridworld.actor.Bug;
import info.gridworld.actor.Flower;
import info.gridworld.grid.*;

import java.awt.Color;
import java.util.*;
import java.util.Random;
import java.util.Stack;

import javax.swing.JOptionPane;

/**
 * A <code>MazeBug</code> can find its way in a maze. <br />
 * The implementation of this class is testable on the AP CS A and AB exams.
 */
public class MazeBug extends Bug {
	public Location next;
	public Location last;
	public boolean isEnd = false;
	public Stack<Location> tracedLocation = new Stack<Location>();
	public Integer stepCount = 0;
	boolean hasShown = false;//final message has been shown

	/**
	 * Constructs a box bug that traces a square of a given side length
	 * 
	 * @param length
	 *            the side length
	 */
	public MazeBug() {
		setColor(Color.GREEN);
		last = getLocation();
	    isEnd = false;
	}

	/**
	 * Moves to the next location of the square.
	 */
	public void act() {
		boolean willMove = canMove();
		if (isEnd == true) {
		//to show step count when reach the goal		
			if (hasShown == false) {
				String msg = stepCount.toString() + " steps";
				JOptionPane.showMessageDialog(null, msg);
				hasShown = true;
			}
		} else if (willMove) {
			move();
			//increase step count when move 
			stepCount++;
		} else if (!willMove) {
			traceback();
			//increase step count when move
			stepCount++;
		}
	}

	/**
	 * Tests whether this bug can move forward into a location that is empty or
	 * contains a flower.
	 * 
	 * @return true if this bug can move.
	 */
	public boolean canMove() {
		Grid<Actor> gr = getGrid();
		Random r = new Random();
		ArrayList<Location> nextLoc = canMoveLocations();
		if (nextLoc.size() == 0) {
			return false;
		} else if (nextLoc.size() == 1) {
			next = nextLoc.get(0);
			tracedLocation.push(getLocation());
			return true;
		} else {
			tracedLocation.push(getLocation());
			// create a random num of [0, nextLoc.size())
			int n2 = r.nextInt(nextLoc.size());
			next = nextLoc.get(n2);
			return true;
		}
	}
	/**
	 * Moves the bug forward, putting a flower into the location it previously
	 * occupied.
	 */
	public void move() {
		Grid<Actor> gr = getGrid();
		if (gr == null)
			return;
		Location loc = getLocation();
		if (gr.isValid(next)) {
			last = loc;
			setDirection(getLocation().getDirectionToward(next));
			moveTo(next);
		} else {
			removeSelfFromGrid();
		}
		Flower flower = new Flower(getColor());
		flower.putSelfInGrid(gr, loc);
	}


	/**
		*find the can move locations as an arraylist
		*use for the canMove method
		*
		* @return the arraylist of the locations can move
	*/
	public ArrayList<Location> canMoveLocations() {
		ArrayList<Location> result = new ArrayList<Location>();
		Grid<Actor> gr = getGrid();
		int[] varRow = {-1, 0, 1, 0};
		int[] varCol = {0, 1, 0, -1};
		Location loc = getLocation();
		for (int i = 0; i < 4; ++i) {
			Location temp = new Location(loc.getRow() + varRow[i], loc.getCol() + varCol[i]);
			if (gr.isValid(temp)) {
				if (gr.get(temp) instanceof Rock && gr.get(temp).getColor().equals(Color.RED)) {
					result.clear();
					result.add(temp);
					isEnd = true;
					return result;
				} else if (gr.get(temp) == null) {
					result.add(temp);
				}
			}
		}
		return result;
	}

	public void traceback() {
		Grid<Actor> gr = getGrid();
		next = tracedLocation.pop();
		last = getLocation();

		if (gr == null) {
			return;
		}
		if (gr.isValid(next)) {
			setDirection(getLocation().getDirectionToward(next));
			moveTo(next);
		} else {
			removeSelfFromGrid();
		}
		Flower flower = new Flower(Color.BLUE);
		flower.putSelfInGrid(gr, last);
	}
}
