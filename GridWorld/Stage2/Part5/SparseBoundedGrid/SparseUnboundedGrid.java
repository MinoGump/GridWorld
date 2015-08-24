/* 
 * AP(r) Computer Science GridWorld Case Study:
 * Copyright(c) 2002-2006 College Entrance Examination Board 
 * (http://www.collegeboard.com).
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
 * @author Alyce Brady
 * @author APCS Development Committee
 * @author Cay Horstmann
 */

import info.gridworld.grid.*;
import java.util.ArrayList;

import java.util.*;

/**
 * An <code>UnboundedGrid</code> is a rectangular grid with an unbounded number of rows and
 * columns. <br />
 * The implementation of this class is testable on the AP CS AB exam.
 */
@SuppressWarnings("unchecked")
public class SparseUnboundedGrid<E> extends AbstractGrid<E>
{
    private Object[][] occupantMap;
    private int d;

    /**
     * Constructs an empty unbounded grid.
     */
    public SparseUnboundedGrid()
    {
        d = 16;
        occupantMap = new Object[d][d];
    }

    public int getNumRows()
    {
        return -1;
    }

    public int getNumCols()
    {
        return -1;
    }

    public boolean isValid(Location loc)
    {
        return loc.getRow() >= 0 && loc.getCol() >= 0;
    }

    public ArrayList<Location> getOccupiedLocations()
    {
        ArrayList<Location> theLocations = new ArrayList<Location>();

        // Look at all grid locations.
        for (int r = 0; r < d; r++)
        {
            for (int c = 0; c < d; c++)
            {
                // If there's an object at this location, put it in the array.
                Location loc = new Location(r, c);
                if (get(loc) != null) {
                    theLocations.add(loc);
                }
            }
        }
        return theLocations;
    }

    public E get(Location loc)
    {
        if (!isValid(loc)) {
            throw new IllegalArgumentException("Location " + loc
                    + " is not valid");
        }
        if (loc.getRow() >= d || loc.getCol() >= d) {
            return null;
        }
        return (E) occupantMap[loc.getRow()][loc.getCol()];
    }

    public E put(Location loc, E obj)
    {
        if (!isValid(loc)) {
            throw new IllegalArgumentException("Location " + loc
                    + " is not valid");
        }
        if (obj == null) {
            throw new IllegalArgumentException("obj == null");
        }

        // Add the object to the grid.
        E oldOccupant = get(loc);
        if (loc.getRow() >= d || loc.getCol() >= d) {
            changeSize(loc);
        }

        occupantMap[loc.getRow()][loc.getCol()] = obj;
        return oldOccupant;
    }

    public E remove(Location loc)
    {
        if (!isValid(loc)) {
            throw new IllegalArgumentException("Location " + loc
                    + " is not valid");
        }
        
        if (loc.getCol() >= d || loc.getRow() >= d) {
            return null;
        }

        // Remove the object from the grid.
        E r = get(loc);
        occupantMap[loc.getRow()][loc.getCol()] = null;
        return r;
    }

    public void changeSize(Location loc) {
        int oldD = d;
        while (loc.getRow() >= d || loc.getCol() >= d) {
            d *= 2;
        }
        Object[][] temp = new Object[d][d];
        for (int r = 0; r < oldD; r++) {
            for (int c = 0; c < oldD; c++) {
                temp[r][c] = occupantMap[r][c];
            }
        }
        occupantMap = temp;
        return ;
    }
}
