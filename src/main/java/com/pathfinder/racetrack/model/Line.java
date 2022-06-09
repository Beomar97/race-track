package com.pathfinder.racetrack.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a connection between to coordinates and provides methods to perform calculations on it.
 */
public class Line {
    private static final Logger logger = LoggerFactory.getLogger(Line.class);
    private final Coordinate p;
    private final Coordinate q;

    /**
     * Create an instance of a line
     * @param p Coordinate that represents one end of the line
     * @param q Coordinate that represents the other end of the line
     */
    public Line(Coordinate p, Coordinate q) {
        if (p.getX() <= q.getX()) {
            this.p = p;
            this.q = q;
        } else {
            this.p = q;
            this.q = p;
        }
    }

    /**
     * Calculates the y component to a given x component of a coordinate lying on the line
     * @param x The x component
     * @return  The y component belonging to x
     */
    public double getYAt(double x) {
        return getGradient() * x + p.getY();
    }

    /**
     * Calculates and returns the y offset at x = 0 of the linear function that corresponds to the line. Keep in mind,
     * that the line does not necessarily have to cross x = 0.
     * @return the y component of the coordinate x = 0 is crossed.
     */
    public double getYAtX0() {
        return -1 * getGradient() * p.getX() + p.getY();
    }

    /**
     * Calculates and return the gradient of the linear function that corresponds to the line.
     * @return The gradient or if the line is vertical Integer.MAX_VALUE
     */
    public double getGradient() {
        double gradient;

        if (isVertical()) {
            gradient = Integer.MAX_VALUE;
            logger.warn("Gradient of vertical line requested. Approximating with {}", gradient);
        } else {
            gradient = ((double) p.getY() - (double) q.getY()) / ((double) p.getX() - (double) q.getX());
        }
        return gradient;
    }

    /**
     * Getter for one end of the line
     * @return  Coordinate at one end of the line
     */
    public Coordinate getP() {
        return p;
    }

    /**
     * Getter for the other end of the line
     * @return  Coordinate at the other end of the line
     */
    public Coordinate getQ() {
        return q;
    }

    /**
     * Check if the line is vertical or not
     * @return  true if the line is vertical, else false
     */
    public boolean isVertical() {
        return p.getX() == q.getX();
    }

    /**
     * Check if the line is horizontal or not
     * @return  true if the line is horizontal, else false
     */
    public boolean isHorizontal() {
        return p.getY() == q.getY();
    }

    /**
     * Check if the given value x is between or equal to the x components of the two coordinates that represent the line
     * @param x The given value that needs to checked
     * @return  true if x is between or equal to the x components of the start and end point, else false
     */
    public boolean isInXRange(double x) {
        logger.debug("Checking if {} is in x Range of {}", x, this);
        return p.getX() <= x && x <= q.getX();
    }

    /**
     * Check if the given value y is between or equal to the y components of the two coordinates that represent the line
     * @param y the given value that needs to checked
     * @return  true if y is between or equal to the y components of the start and end point, else false
     */
    public boolean isInYRange(double y) {
        logger.debug("Checking if {} is in y Range of {}", y, this);
        if (y == p.getY()) {
            return true;
        }
        if (y < p.getY()) {
            return q.getY() <= y;
        } else {
            return q.getY() >= y;
        }
    }

    /**
     * Check if a coordinate is on the line
     * @param cross The Coordinate in question
     * @return  true if the provided coordinate is on the line, else false
     */
    private boolean isOnLine(Coordinate cross) {
        logger.debug("Checking if {} is on {}", cross, this);
        return isInXRange(cross.getX()) && isInYRange(cross.getY());
    }

    /**
     * Check if the current line crosses the provided line
     * @param g the provided line
     * @return  true if the current line crosses g, else false
     */
    public boolean crosses(Line g) {
        logger.debug("Checking if {}} crosses {}", this, g);
        if (isParallelTo(g)) {
            return false;
        } else {
            Coordinate cross = crossesAt(g);
            return isOnLine(cross) && g.isOnLine(cross);
        }
    }

    /**
     * Calculates and returns the Coordinates, where the linear functions that corresponds to the lines, cross.
     * @param g the provided line
     * @return  the coordinate where the linear functions of the two lines cross
     */
    private Coordinate crossesAt(Line g) {
        double crossX = (getYAtX0() - g.getYAtX0()) / (g.getGradient() - getGradient());
        logger.debug("Cross found at x = {}", crossX);
        int intCrossX = (int) Math.round(crossX);
        int intCrossY;
        if (this.isVertical() && !g.isVertical()) {
            intCrossY = (int) Math.round(g.getYAt(crossX));
        } else {
            intCrossY = (int) Math.round(getYAt(intCrossX));
        }
        logger.debug("y is at {}", intCrossY);
        return new Coordinate( intCrossX, intCrossY);
    }

    /**
     * Check if the current line is parallel to the provided line
     * @param g the provided line
     * @return  true if both lines are parallel, else false
     */
    public boolean isParallelTo(Line g) {
        if (isVertical() && g.isVertical()) {
            return true;
        } else {
            return getGradient() == g.getGradient();
        }
    }

    /**
     * Calculates and returns the length of the line.
     * @return  The distance between the start and endpoint of the line
     */
    public double getLength() {
        return Math.sqrt( Math.pow((double) p.getY() - (double) q.getY(), 2) + Math.pow((double) q.getX() - (double) p.getX(), 2));
    }

    /**
     * Calculates and returns coordinate in the middle of the line
     * @return  the coordinate in the middle of the line
     */
    public Coordinate getMiddle() {
        if (isVertical()) {
            return new Coordinate(p.getX(), ((q.getY() + p.getY()) / 2));
        } else {
            return new Coordinate(((q.getX() + p.getX()) / 2), (int) getYAt((q.getX() + p.getX()) / 2.0));
        }
    }

    /**
     * Calculates all Coordinates that are on the line
     * @return  A list that contains all coordinates, that are on the line
     */
    public List<Coordinate> getAllIntCoordinates() {
        List<Coordinate> list = new ArrayList<>();
        if (isVertical()) {
            for (int i = p.getY(); i <= q.getY(); i++) {
                list.add(new Coordinate(p.getX(), i));
            }
        } else {
            for (int i = p.getX(); i <= q.getX(); i++) {

                list.add(new Coordinate(i, (int) getYAt(i)));
            }
        }
        return list;
    }
}
