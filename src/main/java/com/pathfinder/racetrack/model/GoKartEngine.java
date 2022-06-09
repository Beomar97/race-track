package com.pathfinder.racetrack.model;

import com.pathfinder.racetrack.model.exceptions.NotAValidTrackException;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * Variation of the game RaceTrack, after a crash the car continues with the minimum velocity and no acceleration
 * until the track is reached again
 */
public class GoKartEngine extends GameEngine {

    /**
     * Default way to initialise the game
     *
     * @param players List of players
     * @param track   Where the game is carried
     */
    public GoKartEngine(List<Player> players, Track track) {
        super(players, track);
    }

    /**
     * Initialise the game from a rtSave file
     *
     * @param rtSaveFile Location of the file
     * @throws FileNotFoundException   When the rtSave file was not found
     * @throws NotAValidTrackException When the track was not valid
     */
    public GoKartEngine(RTSaveFile rtSaveFile) throws FileNotFoundException, NotAValidTrackException {
        super(rtSaveFile);
    }

    /**
     * Describes what happens when the car crashes
     */
    void carCrashes() {
        getCurrentCar().resetVelocity();
        getCurrentCar().setCrashed(true);
    }

    /**
     * Describes what happens when the car leaves the track
     *
     * @param offTrackPoint Coordinate where the car leaves the track
     */
    void carLeavesTrack(Coordinate offTrackPoint) {
        getCurrentCar().resetVelocity();
        getCurrentCar().setOffTrack(true);
    }
}
