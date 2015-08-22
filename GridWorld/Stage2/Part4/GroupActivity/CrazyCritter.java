/* 
 * AP(r) Computer Science GridWorld Case Study:
 * Copyright(c) 2005-2006 Cay S. Horstmann (http://horstmann.com)
 *
 * This code is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * @author Chris Nevison
 * @author Barbara Cloud Wells
 * @author Cay Horstmann
 */

import info.gridworld.actor.*;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

import java.awt.Color;
import java.util.ArrayList;

/**
 * A <code>CrabCritter</code> looks at a limited set of neighbors when it eats and moves.
 * <br />
 * This class is not tested on the AP CS A and AB exams.
 */
public class CrazyCritter extends Critter
{
    private static final double DARKENING_FACTOR = 220;

    public CrazyCritter()
    {
        setColor(Color.RED);
    }

     public CrazyCritter(Color c)
    {
        setColor(c);
    }

    /*
        Lighten the flower have got.
     */
    public void processActors(ArrayList<Actor> actors)
    {
        for (Actor a : actors)
        {
            if (!(a instanceof Rock) && !(a instanceof Critter) && !(a instanceof Flower)) {
                a.removeSelfFromGrid();
            } else if ((a instanceof Flower)) {
                lighten(a);
            }
        }
    }

    /**
     * @return list of empty locations immediately to the right and to the left
     */
    public ArrayList<Location> getMoveLocations()
    {
        ArrayList<Location> locs = new ArrayList<Location>();
        int[] dirs =
            { 45, -45, 135, -135 };
        for (Location loc : getLocationsInDirections(dirs)) {
            if (getGrid().get(loc) == null || getGrid().get(loc) instanceof Rock) {
                locs.add(loc);
            }
        }

        return locs;
    }

    /**
     * If the crab critter doesn't move, it randomly turns left or right.
     */
    public void makeMove(Location loc)
    {
        if (loc.equals(getLocation())) {
            return ;
        }
        int dir = getLocation().getDirectionToward(loc);
        setDirection(dir);
        if (getGrid().get(loc) instanceof Rock) {
            setDirection(getDirection() + 180);
        } else {
            moveTo(loc);
        }
    }
    
    /**
     * Finds the valid adjacent locations of this critter in different
     * directions.
     * @param directions - an array of directions (which are relative to the
     * current direction)
     * @return a set of valid locations that are neighbors of the current
     * location in the given directions
     */
    public ArrayList<Location> getLocationsInDirections(int[] directions)
    {
        ArrayList<Location> locs = new ArrayList<Location>();
        Grid gr = getGrid();
        Location loc = getLocation();
    
        for (int d : directions)
        {
            Location neighborLoc = loc.getAdjacentLocation(getDirection() + d);
            if (gr.isValid(neighborLoc)) {
                locs.add(neighborLoc);
            }
        }
        return locs;
    }

    public void lighten(Actor a) {
        Color c = getColor();

        int red = ((int) (c.getRed() + DARKENING_FACTOR) > 255) ? 255 : (int) (c.getRed() + DARKENING_FACTOR);
        int green = ((int) (c.getGreen() + DARKENING_FACTOR) > 255) ? 255 : (int) (c.getGreen() + DARKENING_FACTOR);
        int blue = ((int) (c.getBlue() + DARKENING_FACTOR) > 255) ? 255 : (int) (c.getBlue() + DARKENING_FACTOR);

        a.setColor(new Color(red, green, blue));
    }


}
