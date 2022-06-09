package com.pathfinder.racetrack.model;

/**
 * Vector with x and y coordinates
 */
public abstract class Vector {
    private final int[] xy = new int[2];

    /**
     * Constructor for a new vector
     *
     * @param x x value for the new vector
     * @param y y value for the new vector
     */
    public Vector(int x, int y) {
        xy[0] = x;
        xy[1] = y;
    }

    /**
     * Get the x-axis
     *
     * @return x-axis
     */
    public int getX() {
        return xy[0];
    }

    /**
     * Set the x-axis
     *
     * @param x x-axis
     */
    public void setX(int x) {
        xy[0] = x;
    }

    /**
     * Get the y-axis
     *
     * @return y-axis
     */
    public int getY() {
        return xy[1];
    }

    /**
     * Set the y-axis
     *
     * @param y y-axis
     */
    public void setY(int y) {
        xy[1] = y;
    }
}
