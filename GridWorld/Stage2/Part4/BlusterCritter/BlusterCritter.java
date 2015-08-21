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

import info.gridworld.actor.Actor;
import info.gridworld.actor.Critter;
import info.gridworld.grid.Location;
import info.gridworld.grid.Grid;
import info.gridworld.actor.Flower;

import java.util.ArrayList;
import java.awt.Color;

/**
 * A <code>ChameleonCritter</code> takes on the color of neighboring actors as
 * it moves through the grid. <br />
 * The implementation of this class is testable on the AP CS A and AB exams.
 */
public class BlusterCritter extends Critter
{
    // the c variable
    private int courage;
    private static final double DARKENING_FACTOR = 0.05;

    /*
        A new constructor with c variable
    */
    public BlusterCritter(int c) {
        super();
        courage = c;
    }

    /*
        Get the two steps' location
    */
    public ArrayList<Actor> getActors()
    {
        ArrayList<Actor> result = new ArrayList<Actor>();
        Grid gr = getGrid();
        Location origin_loc = getLocation();
        int row = origin_loc.getRow();
        int col = origin_loc.getCol();
        for (int i = -2; i <= 2; i++) {
            for (int j = -2; j <= 2; j++) {
                Location loc = new Location(row+i, col+j);
                if (row+i != 0 || col+j != 0) {
                    if (gr.isValid(loc)) {
                        Actor a = getGrid().get(loc);
                        if (a instanceof Critter) {
                            result.add(a);
                        }
                    }
                }
            }
        }
        return result;
    }

    /*
        Color changed.
    */
    public void processActors(ArrayList<Actor> actors)
    {
        if (actors.size() < courage) {
            Color c = getColor();
            int red, green, blue;
            if (c.getRed() * (1 + DARKENING_FACTOR) > 255) {
                red = 255;
            } else {
                red = (int) (c.getRed() * (1 + DARKENING_FACTOR));
            }
            if (c.getBlue() * (1 + DARKENING_FACTOR) > 255) {
                blue = 255;
            } else {
                blue = (int) (c.getBlue() * (1 + DARKENING_FACTOR));
            }
            if (c.getGreen() * (1 + DARKENING_FACTOR) > 255) {
                green = 255;
            } else {
                green = (int) (c.getGreen() * (1 + DARKENING_FACTOR));
            }

            setColor(new Color(red, green, blue));

        } else {
            Color c = getColor();
            int red = (int) (c.getRed() * (1 - DARKENING_FACTOR));
            int green = (int) (c.getGreen() * (1 - DARKENING_FACTOR));
            int blue = (int) (c.getBlue() * (1 - DARKENING_FACTOR));

            setColor(new Color(red, green, blue));
        }
    }
}
