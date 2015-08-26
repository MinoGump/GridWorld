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
	public Integer[] udlr = new Integer[4];
	public Integer max = 0;
	boolean hasShown = false;//final message has been shown
	private boolean firstDetect;

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
	    	udlr[0] = udlr[1] = udlr[2] = udlr[3] = max = 1;
	    	firstDetect = true;
	}

	/**
	 * Moves to the next location of the square.
	 */
	public void act() {
		if (firstDetect) {
			firstDetect = false;
			Grid<Actor> gr = getGrid();
			ArrayList<Location> grLocs = gr.getOccupiedLocations();
			if (grLocs.size() != 0) {
				for (Location l : grLocs) {
					if ((gr.get(l) instanceof Rock) && (gr.get(l).getColor().equals(Color.RED))) {
						if (l.getRow() > getLocation().getRow()) {
							udlr[2] += 30;
						} else {
							udlr[0] += 30;
						}
						if (l.getCol() > getLocation().getCol()) {
							udlr[1] += 30;
						} else {
							udlr[3] += 30;
						}
					} 
				}
			}
		}
		System.out.println("\"" + udlr[0] + ", " + udlr[1] + "," + udlr[2] + "," + udlr[3] + "\"");
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
		// the max probable direrction.
		ArrayList<Location> nextLoc = canMoveLocations();
		int dir = 0;
		if (nextLoc.size() == 0) {
			return false;
		} else {
			tracedLocation.push(getLocation());
			// create a random num of [0, nextLoc.size())
			int i, tempMax = 0, tempDir = 0;
			for (i = 0; i < nextLoc.size(); i++) {
				dir = getLocation().getDirectionToward(nextLoc.get(i)) / 90;
				if (tempMax < udlr[dir]) {
					tempMax = udlr[dir];
					tempDir = i;
				}
			}
			System.out.println("tempMax : " + tempMax);
			next = nextLoc.get(tempDir);
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
			if (next.getRow() == last.getRow() && next.getCol() - last.getCol() == 1) {
				udlr[1]++;
			} else if (next.getRow() == last.getRow() && last.getCol() - next.getCol() == 1) {
				udlr[3]++;
			} else if (next.getRow() - last.getRow() == 1 && next.getCol() == last.getCol()) {
				udlr[2]++;
			} else if (last.getRow() - next.getRow() == 1 && next.getCol() == last.getCol()) {
				udlr[0]++;
			}
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
			if (next.getRow() == last.getRow() && next.getCol() - last.getCol() == 1) {
				udlr[1]--;
			} else if (next.getRow() == last.getRow() && last.getCol() - next.getCol() == 1) {
				udlr[3]--;
			} else if (next.getRow() - last.getRow() == 1 && next.getCol() == last.getCol()) {
				udlr[2]--;
			} else if (last.getRow() - next.getRow() == 1 && next.getCol() == last.getCol()) {
				udlr[0]--;
			}
		} else {
			removeSelfFromGrid();
		}
		Flower flower = new Flower(Color.BLUE);
		flower.putSelfInGrid(gr, last);
	}
}
