/*
    This class represents the velocity of a car. It as a x and y coordinate.
    @author (Marvin Tseng)
 */

package com.pathfinder.racetrack.model;

/**
 * Represents Velocity as a Vector
 */
public class Velocity extends Vector {

    /**
     * Constructor for a new velocity object
     *
     * @param x x value for the new velocity
     * @param y y value for the new velocity
     */
    public Velocity(int x, int y) {
        super(x, y);
    }

    /**
     * Gets the length travelled for the velocity
     *
     * @return length travelled as a double value
     */
    public double getLength() {
        double x2 = Math.pow(getX(), 2);
        double y2 = Math.pow(getY(), 2);
        return Math.sqrt(x2 + y2);
    }
}
