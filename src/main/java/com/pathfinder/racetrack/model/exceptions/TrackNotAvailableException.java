package com.pathfinder.racetrack.model.exceptions;

/**
 * Exception when a track cannot be found
 */
public class TrackNotAvailableException extends Exception {

    /**
     * Constructor for the exception only with a message
     *
     * @param message error message to be thrown
     */
    public TrackNotAvailableException(String message) {
        super(message);
    }

    /**
     * Constructor for the exception additionally with a throwable cause
     *
     * @param message error message to be thrown
     * @param cause cause of error to be thrown
     */
    public TrackNotAvailableException(String message, Throwable cause) {
        super(message, cause);
    }

}
