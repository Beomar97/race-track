package com.pathfinder.racetrack;


import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.io.File;
import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class RaceTrackTest extends ApplicationTest {
    private static final String USER_HOME_DIR = System.getProperty("user.home");
    private static final String CONFIG_DIR = "/RaceTrack";
    private static final String TRACK_DIR = "/tracks";
    private static final String ZIP_DIR = "/tracks.zip";
    private static File configFolder;
    private static File trackFolder;
    private static File zipFileDestination;
    private RaceTrack raceTrack;

    @Override
    public void start(Stage stage) {
        raceTrack = spy(new RaceTrack());
        configFolder = new File(USER_HOME_DIR + CONFIG_DIR);
        trackFolder = new File(USER_HOME_DIR + CONFIG_DIR + TRACK_DIR);
        zipFileDestination = new File(USER_HOME_DIR + CONFIG_DIR + TRACK_DIR + ZIP_DIR);

        raceTrack.start(stage);
    }

    @Test
    public void launchGame() {
        verify(raceTrack, atLeastOnce()).initExistingTracks();
    }

    @Test
    public void getConfigFolder() {
        assertEquals(configFolder.getPath(), RaceTrack.getConfigFolder().getPath());
    }

    @Test
    public void setupTracksFolder() {
        raceTrack.setupTracksFolder(TRACK_DIR + ZIP_DIR, zipFileDestination, configFolder);
        assertTrue(trackFolder.exists());
        assertTrue(Objects.requireNonNull(trackFolder.list()).length > 0);
    }
}
