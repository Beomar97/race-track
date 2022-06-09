package com.pathfinder.racetrack.view;

import javafx.stage.FileChooser;

import java.io.File;

/**
 * Dialog to choose files or paths
 */
public class RTSaveFileChooser {

    RTSaveFileChooser() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Initializes the file chooser to be used
     *
     * @return a file chooser to be used
     */
    private static FileChooser initFileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("RaceTrack - Save Session");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("RaceTrack File (*.rtsave)", "*.rtsave");
        fileChooser.getExtensionFilters().add(extFilter);
        File defaultDirectory = new File(System.getProperty("user.home"));
        fileChooser.setInitialDirectory(defaultDirectory);
        fileChooser.setInitialFileName("racetrack_session.rtsave");
        return fileChooser;
    }

    /**
     * Shows the file chooser for saving a game session
     *
     * @return file chooser for saving a game session
     */
    public static File showSaveWindow() {
        FileChooser fileChooser = initFileChooser();
        return fileChooser.showSaveDialog(null);
    }

    /**
     * Shows the file chooser for loading a game session
     *
     * @return file chooser for loading a game session
     */
    public static File showLoadWindow() {
        FileChooser fileChooser = initFileChooser();
        return fileChooser.showOpenDialog(null);
    }
}
