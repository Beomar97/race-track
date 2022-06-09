package com.pathfinder.racetrack.model;

import com.pathfinder.racetrack.model.exceptions.NotAValidTrackException;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * Variation of the game RaceTrack, after a car crashed the player retires from the game
 */
public class FormulaOneEngine extends GameEngine {
    /**
     * Default way to initialise the game
     *
     * @param players List of players
     * @param track   Where the game is carried
     */
    public FormulaOneEngine(List<Player> players, Track track) {
        super(players, track);
    }

    /**
     * Initialise the game from a rtSave file
     *
     * @param rtSaveFile Location of the file
     * @throws FileNotFoundException   When the rtSave file was not found
     * @throws NotAValidTrackException When the track was not valid
     */
    public FormulaOneEngine(RTSaveFile rtSaveFile) throws FileNotFoundException, NotAValidTrackException {
        super(rtSaveFile);
    }

    /**
     * Describes what happens when the car crashes
     */
    void carCrashes() {
        retireCurrentPlayer();
        getCurrentCar().setCrashed(true);
    }

    /**
     * Describes what happens when the car leaves the track
     *
     * @param offTrackPoint Coordinate where the car leaves the track
     */
    void carLeavesTrack(Coordinate offTrackPoint) {
        retireCurrentPlayer();
        getCurrentCar().setOffTrack(true);
    }
}
