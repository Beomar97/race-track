package com.pathfinder.racetrack.model;

import com.pathfinder.racetrack.model.exceptions.NotAValidTrackException;
import com.pathfinder.racetrack.model.exceptions.TrackNotAvailableException;
import com.pathfinder.racetrack.view.MapPainter;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

/**
 * Class holding all existing tracks
 */
public class ExistingTracks {
    private static final Logger logger = LoggerFactory.getLogger(ExistingTracks.class);

    private static ArrayList<Track> tracks = new ArrayList<>();

    private ExistingTracks() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Initializes all existing tracks and adds them to the list of existing tracks
     * at the startup of the game
     *
     * @param trackDirectory path to the directory holding all tracks
     */
    public static void initTracks(String trackDirectory) {
        File folder = new File(trackDirectory);
        FilenameFilter pngFilter = (dir, name) -> name.toLowerCase().endsWith(".png");
        File[] files = folder.listFiles(pngFilter);
        if (files != null) {
            for (File file : files) {
                if (file.isFile() && !file.getName().equals("Track_Template.png")) {
                    try {
                        tracks.add(new Track(FilenameUtils.removeExtension(file.getName()), file, MapPainter.getCountBoxesX(), MapPainter.getCountBoxesY()));
                    } catch (FileNotFoundException | NotAValidTrackException e) {
                        logger.error("Error occurred during init of existing track '{}'!", file.getName(), e);
                    }
                }
            }
        } else {
            logger.error("Error occured during loading of tracks from folder '{}'!", folder.getName());
        }
    }

    /**
     * Adds a track to the existing tracks from a file
     *
     * @param validTrack A valid track to be added
     */
    public static void addTrack(Track validTrack) {
        tracks.add(validTrack);
    }

    /**
     * Removes a track from the existing tracks by name
     *
     * @param nameOfTrack name of the track to be removed
     */
    public static void removeTrackByName(String nameOfTrack) {
        tracks.remove(tracks.stream()
                .filter(track -> track.getName().equals(nameOfTrack))
                .findFirst()
                .orElse(null));
    }

    /**
     * Get a track by its name from the existing tracks list
     *
     * @param nameOfTrack name of the track to be returned
     * @return the track with the correct name
     * @throws TrackNotAvailableException if no track with that name can be found
     */
    public static Track getTrackByName(String nameOfTrack) throws TrackNotAvailableException {
        for (Track track : tracks) {
            if (track.getName().equals(nameOfTrack)) {
                return track;
            }
        }
        throw new TrackNotAvailableException("Track " + nameOfTrack + " not found");
    }

    /**
     * Getter for the list with all existing tracks
     *
     * @return a list with currently all existing tracks
     */
    public static List<Track> getTracks() {
        return tracks;
    }

    /**
     * Clears the list holding all existing tracks
     */
    public static void clear() {
        tracks = new ArrayList<>();
    }
}
