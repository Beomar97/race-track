package com.pathfinder.racetrack.model;

import javafx.scene.paint.Color;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class GoKartEngineTest {
    private GoKartEngine goKartEngine;
    private Player playerOne;
    private Player playerTwo;
    private Player playerThree;
    private ArrayList<Player> players;
    private Track trackMock;

    @Before
    public void init() {
        players = new ArrayList<>();
        playerOne = new Player(1, "Hans", Color.RED);
        playerTwo = new Player(2, "Peter", Color.BLUE);
        playerTwo.setRetired(true);
        playerThree = new Player(2, "Müller", Color.BLUE);
        players.add(playerOne);
        players.add(playerTwo);
        players.add(playerThree);
        trackMock = mock(Track.class);
        ArrayList<Coordinate> startingPoints = new ArrayList<>();
        startingPoints.add(new Coordinate(20, 20));
        startingPoints.add(new Coordinate(21, 21));
        startingPoints.add(new Coordinate(22, 21));
        startingPoints.add(new Coordinate(23, 21));
        when(trackMock.getStartPoints()).thenReturn(startingPoints);
        goKartEngine = spy(new GoKartEngine(players, trackMock));
    }

    @Test
    public void carCrashShouldRestVelocity() {
        goKartEngine.carCrashes();
        Velocity velocity = goKartEngine.getCurrentCar().getCurrentVelocity();
        assertEquals(0, velocity.getX());
        assertEquals(0, velocity.getY());
        verify(goKartEngine, times(1)).carCrashes();
    }
}