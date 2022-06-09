package com.pathfinder.racetrack.model;

import com.pathfinder.racetrack.model.exceptions.NotAValidTrackException;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * Variation of the game RaceTrack, after a crash the position of the car resets to the last valid one.
 */

public class BobbyCarEngine extends GameEngine {
    /**
     * Default way to initialise the game
     *
     * @param players List of players
     * @param track   Where the game is carried
     */
    public BobbyCarEngine(List<Player> players, Track track) {
        super(players, track);
    }

    /**
     * Initialise the game from a rtSave file
     *
     * @param rtSaveFile Location of the file
     * @throws FileNotFoundException   When the rtSave file was not found
     * @throws NotAValidTrackException When the track was not valid
     */
    public BobbyCarEngine(RTSaveFile rtSaveFile) throws FileNotFoundException, NotAValidTrackException {
        super(rtSaveFile);
    }

    /**
     * Applies the game rules to the game and takes the necessary actions
     *
     * @param playerMove Where the car wants to move
     */
    @Override
    public void applyGameRules(Coordinate playerMove) {
        Car car = getCurrentCar();

        if (!pointIsOnTrack(playerMove)) {
            playerMove = car.getPosition();
            carLeavesTrack(playerMove);
        } else {
            car.setOffTrack(false);
        }

        if (checkPointIsOccupied(playerMove)) {
            carCrashes();
        } else {
            car.setCrashed(false);
            car.moveTo(playerMove);
        }

        if (crossedFinishLine(car)) {
            carFinished();
        }
    }

    /**
     * Describes what happens when the car crashes
     */
    @Override
    void carCrashes() {
        getCurrentCar().resetVelocity();
        getCurrentCar().setCrashed(true);
    }

    /**
     * Describes what happens when the car leaves the track
     *
     * @param offTrackPoint Coordinate where the car leaves the track
     */
    @Override
    void carLeavesTrack(Coordinate offTrackPoint) {
        getCurrentCar().resetVelocity();
        getCurrentCar().setOffTrack(false);
    }
}
