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
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;
import info.gridworld.actor.*;

import java.awt.Color;
import java.util.ArrayList;

/**
 * A <code>CrabCritter</code> looks at a limited set of neighbors when it eats and moves.
 * <br />
 * This class is not tested on the AP CS A and AB exams.
 */
public class KingCrab extends CrabCritter
{
    public KingCrab()
    {
        super();
    }

    
    /*
        Keep the actors have got away from the kingcrab.
     */
    public void processActors(List<Actor> actors)
    {
        Location kingLoc = getLocation();
        for (Actor a : actors)
        {
            Location preLoc = a.getLocation();
            int newDir = preLoc.getDirectionToward(kingLoc) + Location.HALF_CIRCLE;
            a.setDirection(newDir);
            a.act();
            Location folLoc = a.getLocation();
            if (preLoc.equals(folLoc) && !(a instanceof Flower) && !(a instanceof Rock) && (a != this)) {
                a.removeSelfFromGrid();
            }
        }
    }

}
