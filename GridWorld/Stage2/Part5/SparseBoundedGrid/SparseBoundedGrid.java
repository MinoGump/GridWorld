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

import info.gridworld.grid.Grid;
import info.gridworld.grid.AbstractGrid;
import info.gridworld.grid.Location;
import java.util.ArrayList;

/**
 * A <code>BoundedGrid</code> is a rectangular grid with a finite number of
 * rows and columns. <br />
 * The implementation of this class is testable on the AP CS AB exam.
 */
public class SparseBoundedGrid<E> extends AbstractGrid<E>
{

	private SparseGridNode[] occupantMap;
	private int col, row;

    /**
     * Constructs an empty bounded grid with the given dimensions.
     * (Precondition: <code>rows > 0</code> and <code>cols > 0</code>.)
     * @param rows number of rows in BoundedGrid
     * @param cols number of columns in BoundedGrid
     */
    public SparseBoundedGrid(int rows, int cols)
    {
        if (rows <= 0) {
            throw new IllegalArgumentException("rows <= 0");
        }
        if (cols <= 0) {
            throw new IllegalArgumentException("cols <= 0");
        }
        row = rows;
        col = cols;
        occupantMap = new SparseGridNode[rows];
    }

    public int getNumRows()
    {
        return row;
    }

    public int getNumCols()
    {
        // Note: according to the constructor precondition, numRows() > 0, so
        // theGrid[0] is non-null.
        return col;
    }

    public boolean isValid(Location loc)
    {
        return 0 <= loc.getRow() && loc.getRow() < getNumRows()
                && 0 <= loc.getCol() && loc.getCol() < getNumCols();
    }

    public ArrayList<Location> getOccupiedLocations()
    {
        ArrayList<Location> theLocations = new ArrayList<Location>();

        // Look at all grid locations.
        for (int r = 0; r < getNumRows(); r++)
        {
            SparseGridNode rowNode = occupantMap[r];
            while (rowNode != null) {
                Location loc = new Location(r, rowNode.getCol());
                theLocations.add(loc);
                rowNode = rowNode.getNext();
            }
        }

        return theLocations;
    }

    @SuppressWarnings("unchecked")
    public E get(Location loc)
    {
        if (!isValid(loc)) {
            throw new IllegalArgumentException("Location " + loc
                    + " is not valid");
        }
        SparseGridNode rowNode = occupantMap[loc.getRow()];
        while (rowNode != null) {
            if (rowNode.getCol() == loc.getCol()) {
                return (E)(rowNode.getOccupant());
            }
            rowNode = rowNode.getNext();
        }
        return null; // unavoidable warning
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
        E oldOccupant = remove(loc);
        SparseGridNode rowNode = occupantMap[loc.getRow()];
        occupantMap[loc.getRow()] = new SparseGridNode(obj, loc.getCol(), rowNode);
        return oldOccupant;
    }

    public E remove(Location loc)
    {
        if (!isValid(loc)) {
            throw new IllegalArgumentException("Location " + loc
                    + " is not valid");
        }
        
        // Remove the object from the grid.
        E r = get(loc);

        if (r == null) {
            return null;
        }

        SparseGridNode rowNode = occupantMap[loc.getRow()];
        if (rowNode != null) {
            if (rowNode.getCol() == loc.getCol()) {
                occupantMap[loc.getRow()] = rowNode.getNext();
            } else {
                SparseGridNode nextNode = rowNode.getNext();
                while (nextNode != null && nextNode.getCol() != loc.getCol()) {
                    nextNode = nextNode.getNext();
                    rowNode = rowNode.getNext();
                }
                if (nextNode != null) {
                    rowNode.setNext(nextNode.getNext());
                }
            }
        }
        return r;
    }
}

