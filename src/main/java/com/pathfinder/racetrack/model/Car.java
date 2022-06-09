package com.pathfinder.racetrack.model;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a car in the game
 */
public class Car {
    private static final int X_CORNER_LEFT = 0;
    private static final int Y_CORNER_LEFT = 0;
    private static final int X_NUMBER_OF_NEIGHBOURS = 1;
    private static final int Y_NUMBER_OF_NEIGHBOURS = 1;
    private final ArrayList<Velocity> velocityHistory;
    private ArrayList<Coordinate> route;
    private Color color;
    private boolean crashed;
    private boolean offTrack;

    /**
     * Create an instance of the car with position (0,0) and a specified color
     *
     * @param color The color of the car
     */
    public Car(Color color) {
        this.color = color;
        route = new ArrayList<>();
        velocityHistory = new ArrayList<>();
        velocityHistory.add(new Velocity(X_CORNER_LEFT, Y_CORNER_LEFT));
    }

    /**
     * Create an instance of the car with specified a position and color
     *
     * @param color The color of the car
     * @param x     x-axis
     * @param y     y-axis
     */
    public Car(int x, int y, Color color) {
        this.color = color;
        route = new ArrayList<>();
        route.add(new Coordinate(x, y));
        velocityHistory = new ArrayList<>();
        velocityHistory.add(new Velocity(X_CORNER_LEFT, Y_CORNER_LEFT));
    }

    /**
     * Initialise the position of the car.
     *
     * @param pos Where the car is placed
     * @return False if the car already has a position
     */
    public boolean initPosition(Coordinate pos) {
        if (route.isEmpty()) {
            route.add(pos);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Get the current velocity of the car
     *
     * @return Current velocity of the car
     */
    public Velocity getCurrentVelocity() {
        int numberOfVelocities = velocityHistory.size() - 1;
        return velocityHistory.get(numberOfVelocities);
    }

    /**
     * Get the highest reached speed of the car
     *
     * @return Highest reached velocity
     */
    public Velocity getMaxVelocity() {
        Velocity maxVelocity = getCurrentVelocity();

        for (Velocity toCompare : velocityHistory) {
            if (toCompare.getLength() > maxVelocity.getLength()) {
                maxVelocity = toCompare;
            }
        }

        return maxVelocity;
    }

    /**
     * Empties the route of the car
     */
    public void resetRoute() {
        route = new ArrayList<>();
    }

    /**
     * Calculates the next base point by vector addition. Velocity and the position are added together.
     *
     * @return Base point where the player lands in the next move
     */
    public Coordinate getNextBasePoint() {
        Velocity currentVelocity = getCurrentVelocity();
        int x = getPosition().getX() + currentVelocity.getX();
        int y = getPosition().getY() + currentVelocity.getY();
        return new Coordinate(x, y);
    }

    /**
     * Get the route of the car
     *
     * @return List of coordinates
     */
    public List<Coordinate> getRoute() {
        return route;
    }

    /**
     * Get the number of moves the player already has made.
     * The initial placement is subtracted, so only moves count.
     *
     * @return Number of moves done
     */
    public int getNumberOfMoves() {
        return route.size() - 1;
    }

    /**
     * Get the color of the car
     *
     * @return Color of the car
     */
    public Color getColor() {
        return color;
    }

    /**
     * Set the color of the car
     *
     * @param color Color to set
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Resets the velocity of the car to zero
     */
    public void resetVelocity() {
        velocityHistory.add(new Velocity(0, 0));
    }

    /**
     * Set the velocity of the car
     *
     * @param velocity Velocity of the car
     */
    public void setVelocity(Velocity velocity) {
        velocityHistory.add(velocity);
    }

    /**
     * Update the Velocity by subtracting the current position by the previous one.
     */
    public void updateVelocity() {
        Coordinate currentPos = getPosition();
        Coordinate lastPos = route.get(getNumberOfMoves() - 1);

        int vX = currentPos.getX() - lastPos.getX();
        int vY = currentPos.getY() - lastPos.getY();

        Velocity currentVelocity = getCurrentVelocity();
        currentVelocity.setX(vX);
        currentVelocity.setY(vY);
    }

    /**
     * Get the position of car's current position
     *
     * @return Current Position of the Car
     */
    public Coordinate getPosition() {
        return route.get(getNumberOfMoves());
    }

    /**
     * Move the car to a new Position
     *
     * @param coordinate Where to move to
     */
    public void moveTo(Coordinate coordinate) {

        route.add(coordinate);
        updateVelocity();
    }

    /**
     * Get the previous position of the car,
     * if one exists, otherwise it will return the current position.
     *
     * @return Previous position
     */
    public Coordinate getLastPosition() {
        int count = getNumberOfMoves();
        if (count < 1) {
            return getPosition();
        } else {
            return route.get(count - 1);
        }
    }

    /**
     * Get the value if the car has crashed
     *
     * @return True if it has crashed, false otherwise
     */
    public boolean hasCrashed() {
        return crashed;
    }

    /**
     * Set crash property
     *
     * @param crashed True if it has crashed, false otherwise
     */
    public void setCrashed(boolean crashed) {
        this.crashed = crashed;
    }

    /**
     * Check if the car is off track
     *
     * @return True if the car is off-track, false otherwise
     */
    public boolean isOffTrack() {
        return offTrack;
    }

    /**
     * Set off-track property
     *
     * @param offTrack True if the car is offtrack, false otherwise
     */
    public void setOffTrack(boolean offTrack) {
        this.offTrack = offTrack;
    }

    /**
     * Get an array of neighbour coordinates (index starting top left and ending bottem left)
     * base coordinate included
     *
     * @return An array of neighbour coordinates, with the base coordinate
     */
    public List<Coordinate> getValidMoves() {
        int x = getNextBasePoint().getX();
        int y = getNextBasePoint().getY();

        int yBottomOffset = -Y_NUMBER_OF_NEIGHBOURS;
        int xRightOffset = -X_NUMBER_OF_NEIGHBOURS;

        ArrayList<Coordinate> result = new ArrayList<>();

        for (int i = Y_NUMBER_OF_NEIGHBOURS; i >= yBottomOffset; i--) {
            for (int j = X_NUMBER_OF_NEIGHBOURS; j >= xRightOffset; j--) {
                int x2 = x - j;
                int y2 = y - i;
                result.add(new Coordinate(x2, y2));
            }
        }
        return result;
    }
}
