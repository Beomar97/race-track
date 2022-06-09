package com.pathfinder.racetrack;

import com.pathfinder.racetrack.model.ExistingTracks;
import com.pathfinder.racetrack.view.ExceptionDialog;
import com.pathfinder.racetrack.view.SceneController;
import javafx.application.Application;
import javafx.stage.Stage;
import net.lingala.zip4j.ZipFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

/**
 * Sets up the game
 */
public class RaceTrack extends Application {
    private static final String TITLE = "RaceTrack";
    private static final String USER_HOME_DIR = System.getProperty("user.home");
    private static final String CONFIG_DIR = "/RaceTrack";
    private static final String TRACK_DIR = "/tracks";
    private static final String HIGH_SCORE_DIR = "/high_score";
    private static final String ZIP_DIR = "/tracks.zip";
    private static final File configFolder = new File(USER_HOME_DIR + CONFIG_DIR);
    private static final File trackFolder = new File(USER_HOME_DIR + CONFIG_DIR + TRACK_DIR);
    private static final File zipFileDestination = new File(USER_HOME_DIR + CONFIG_DIR + TRACK_DIR + ZIP_DIR);
    private static final File highScoreFolder = new File(USER_HOME_DIR + CONFIG_DIR + HIGH_SCORE_DIR);

    /**
     * Method which gets called by the Main class
     */
    public static void main() {
        new RaceTrack();
        launch();
    }

    /**
     * Get the config folder location
     *
     * @return Location of the config folder
     */
    public static File getConfigFolder() {
        return configFolder;

    }

    /**
     * Initializes the tracks folder in the users home directory if necessary
     *
     * @param zipInResources all default tracks in a zip archive
     * @param zipDestination destination for the zip archive with all default tracks to be moved to
     * @param zipUnpackDestination destination for the zip archive with all default tracks to be unpacked to
     */
    void setupTracksFolder(String zipInResources, File zipDestination, File zipUnpackDestination) {
        InputStream zipStreamFromJar = getClass().getResourceAsStream(zipInResources);

        if (!zipDestination.exists()) {
            try {
                Files.copy(zipStreamFromJar, zipDestination.toPath());
                new ZipFile(zipDestination.getPath()).extractAll(zipUnpackDestination.getPath());
            } catch (IOException e) {
                ExceptionDialog.showDialog("The tracks zip file could not be copied. Make sure your home directory is writable.", e);
            } catch (NullPointerException e) {
                ExceptionDialog.showDialog("The tracks zip file could not be found. If the error persists try reinstalling RaceTrack.", e);
            }
        }
    }

    /**
     * Initializes all existing tracks in the track directory in the users home directory
     */
    void initExistingTracks() {
        ExistingTracks.initTracks(USER_HOME_DIR + CONFIG_DIR + TRACK_DIR);
    }

    /**
     * Initialize all GUI scenes and display the main menu
     *
     * @param stage
     */
    @Override
    public void start(Stage stage) {
        configFolder.mkdir();
        trackFolder.mkdir();
        setupTracksFolder(TRACK_DIR + ZIP_DIR, zipFileDestination, configFolder);
        initExistingTracks();

        if (!configFolder.exists() && !configFolder.isDirectory()) {
            configFolder.mkdir();
        }
        if (!highScoreFolder.exists() && !highScoreFolder.isDirectory()) {
            highScoreFolder.mkdir();
        }

        SceneController.setStage(stage);
        SceneController.setTitle(TITLE);
        SceneController.setHandlers();
        SceneController.initStartMenuScene();
        SceneController.initOptionsMenuScene();
        SceneController.initHighScoreScene();
        SceneController.initTracksMenuScene();
        SceneController.initRulesScene();
        SceneController.initCreditsScene();
        SceneController.setStartMenuScene();
    }
}