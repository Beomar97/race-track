package com.pathfinder.racetrack.model.exceptions;

/**
 * Exception for deleting default tracks
 */
public class DeleteDefaultTrackException extends Exception {

    /**
     * Constructor for the exception only with a message
     *
     * @param message error message to be thrown
     */
    public DeleteDefaultTrackException(String message) {
        super(message);
    }

    /**
     * Constructor for the exception additionally with a throwable cause
     *
     * @param message error message to be thrown
     * @param cause cause of error to be thrown
     */
    public DeleteDefaultTrackException(String message, Throwable cause) {
        super(message, cause);
    }

}
