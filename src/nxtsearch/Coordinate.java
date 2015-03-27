package nxtsearch;

import java.util.*;

public class Coordinate {
	public int x, y;

	public Coordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Checks if two coordinates are the same
	 * 
	 * @return true if they are or false if they are not the same
	 */
	@Override
	public boolean equals(Object o) {
		Coordinate c = (Coordinate) o;
		return x == c.x && y == c.y;
	}
}
