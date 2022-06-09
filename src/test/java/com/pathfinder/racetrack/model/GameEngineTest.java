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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GameEngineTest {
    private ArrayList<Player> players;
    private Player playerOne;
    private Player playerTwo;
    private Player playerThree;
    private Track track;
    private GameEngine gameEngine;

    @Before
    public void init() {
        try {
            track = new Track("The Donut", new File(RaceTrack.getConfigFolder() + "/tracks/The Donut.png"), MapPainter.getCountBoxesX(), MapPainter.getCountBoxesY());
        } catch (FileNotFoundException | NotAValidTrackException e) {
            e.printStackTrace();
        }

        players = new ArrayList<>();
        playerOne = new Player(0, "Hans", Color.RED);
        playerTwo = new Player(1, "Peter", Color.BLUE);
        playerThree = new Player(2, "Karl", Color.BLUE);
        players.add(playerOne);
        players.add(playerTwo);
        players.add(playerThree);

        gameEngine = new GameEngine(players, track) {
            @Override
            void carCrashes() {
                throw new UnsupportedOperationException();
            }

            @Override
            void carLeavesTrack(Coordinate offTrackPoint) {
                throw new UnsupportedOperationException();
            }
        };
    }

    @Test
    public void shouldCheckOccupiedPoint() {
        ArrayList<Player> players = new ArrayList<>();
        Player playerOne = mock(Player.class);
        when(playerOne.getCar()).thenReturn(new Car(10, 10, Color.RED));
        players.add(playerOne);
        Player playerTwo = mock(Player.class);
        when(playerTwo.getCar()).thenReturn(new Car(20, 20, Color.RED));
        players.add(playerTwo);

        GameEngine gameEngine = new GameEngine(players, track) {
            @Override
            void carCrashes() {
                throw new UnsupportedOperationException();
            }

            @Override
            void carLeavesTrack(Coordinate offTrackPoint) {
                throw new UnsupportedOperationException();
            }
        };

        assertTrue(gameEngine.checkPointIsOccupied(gameEngine.getNextPlayer().getCar().getPosition()));
        assertFalse(gameEngine.checkPointIsOccupied(new Coordinate(200, 200)));
    }

    @Test
    public void shouldSetTrack() {
        gameEngine.setTrack(track);
        assertEquals(track, gameEngine.getTrack());
    }

    @Test
    public void shouldReturnTrack() {
        assertEquals(track, gameEngine.getTrack());
    }

    @Test
    public void shouldReturnAllPlayers() {
        assertEquals(players.size(), gameEngine.getPlayers().size());
    }

    @Test
    public void shouldReturnAllCars() {
        assertEquals(players.size(), gameEngine.getAllCars().size());
    }

    @Test
    public void shouldRetirePlayer() {
        Player toRetire = gameEngine.getCurrentPlayer();
        gameEngine.retireCurrentPlayer();
        assertTrue(toRetire.getRetired());
    }
}