package com.pathfinder.racetrack.model;

import com.pathfinder.racetrack.RaceTrack;
import com.pathfinder.racetrack.model.exceptions.NotAValidTrackException;
import com.pathfinder.racetrack.view.MapPainter;
import javafx.scene.paint.Color;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class RTSaveFileTest {
    ArrayList<Player> players = new ArrayList<>();
    private String gameMode;
    private Track track;
    private int currentPlayerId;

    @Before
    public void init() {
        gameMode = "com.pathfinder.racetrack.model.BobbyCarEngine";
        try {
            track = new Track("The Donut", new File(RaceTrack.getConfigFolder() + "/tracks/The Donut.png"), MapPainter.getCountBoxesX(), MapPainter.getCountBoxesY());
        } catch (FileNotFoundException | NotAValidTrackException e) {
            e.printStackTrace();
        }

        currentPlayerId = 1;

        Player playerOne = new Player(0, "Hans", Color.RED);
        Player playerTwo = new Player(1, "Peter", Color.BLUE);
        Player playerThree = new Player(2, "Karl", Color.BLUE);
        players.add(playerOne);
        players.add(playerTwo);
        players.add(playerThree);
    }

    @Test
    public void shouldSaveGameAsJSON() {
        String homeDir = System.getProperty("user.home") + "/racktrack_save_test.rtsave";
        File expected = new File(getClass().getResource("/json/racktrack_save_test.rtsave").getPath());
        File output = new File(homeDir);

        RTSaveFile rtSaveFile = new RTSaveFile(gameMode, currentPlayerId, track.getName(), players);
        rtSaveFile.saveGame(output);

        assertEquals("The Donut", rtSaveFile.getTrackName());
        assertEquals(gameMode, rtSaveFile.getGameMode());
        assertTrue(expected.exists());
    }

    @Test
    public void getGameEngine() throws FileNotFoundException, NotAValidTrackException {
        RTSaveFile rtSaveFile = new RTSaveFile("com.pathfinder.racetrack.model.BobbyCarEngine", currentPlayerId, track.getName(), players);
        assertTrue(rtSaveFile.getGameEngine() instanceof BobbyCarEngine);

        rtSaveFile = new RTSaveFile("com.pathfinder.racetrack.model.FormulaOneEngine", currentPlayerId, track.getName(), players);
        assertTrue(rtSaveFile.getGameEngine() instanceof FormulaOneEngine);

        rtSaveFile = new RTSaveFile("com.pathfinder.racetrack.model.GoKartEngine", currentPlayerId, track.getName(), players);
        assertTrue(rtSaveFile.getGameEngine() instanceof GoKartEngine);

        rtSaveFile = new RTSaveFile("asdf", currentPlayerId, track.getName(), players);
        assertNull(rtSaveFile.getGameEngine());
    }

    @Test
    public void fixGSONColorTypeLoss() {
        RTSaveFile rtSaveFile = new RTSaveFile("com.pathfinder.racetrack.model.BobbyCarEngine", currentPlayerId, track.getName(), players);
        rtSaveFile.fixGSONColorTypeLoss();
        for (Player player : rtSaveFile.getPlayers()) {
            assertTrue(player.getCar().getColor() instanceof Color);
        }
    }
}