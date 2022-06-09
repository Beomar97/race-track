package com.pathfinder.racetrack.model.exceptions;

/**
 * Exception for invalid Tracks
 */
public class NotAValidTrackException extends Exception {

    /**
     * Constructor for the exception only with a message
     *
     * @param message error message to be thrown
     */
    public NotAValidTrackException(String message) {
        super(message);
    }

    /**
     * Constructor for the exception additionally with a throwable cause
     *
     * @param message error message to be thrown
     * @param cause cause of error to be thrown
     */
    public NotAValidTrackException(String message, Throwable cause) {
        super(message, cause);
    }

}
