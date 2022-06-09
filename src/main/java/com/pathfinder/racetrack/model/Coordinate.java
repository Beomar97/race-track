package com.pathfinder.racetrack.model;

/**
 * Represents a Coordinate as a Vector
 */
public class Coordinate extends Vector {
    /**
     * Constructor for instancing a new coordinate
     *
     * @param x the position of the coordinate on the x-axis
     * @param y the position of the coordinate on the y-axis
     */
    public Coordinate(int x, int y) {
        super(x, y);
    }

    /**
     * Displays the position of the coordinate as a String
     *
     * @return
     */
    @Override
    public String toString() {
        return getX() + "/" +getY();
    }

    /**
     * Compares two coordinates with eachother
     *
     * @param o the ccordinate to be compared with
     * @return boolean value if the coordinate was the same or not
     */
    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else {
            return this.hashCode() == o.hashCode();
        }
    }

    /**
     * Displays the hashcode value for the coordinate
     *
     * @return hashcode value as a String
     */
    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}
