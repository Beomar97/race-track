package com.pathfinder.racetrack.model.exceptions;

/**
 * Exception when a player soft-locks himself
 */
public class AllPossibleTurnsOutOfCanvasException extends Exception {

    /**
     * Constructor for the exception only with a message
     *
     * @param message error message to be thrown
     */
    public AllPossibleTurnsOutOfCanvasException(String message) {
        super(message);
    }
}
