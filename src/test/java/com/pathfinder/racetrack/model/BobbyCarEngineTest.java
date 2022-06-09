package com.pathfinder.racetrack.model;

import javafx.scene.paint.Color;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class BobbyCarEngineTest {
    ArrayList<Player> players;
    private Track trackMock;

    @Before
    public void init() {
        trackMock = mock(Track.class);
        ArrayList<Coordinate> startingPoints = new ArrayList<>();
        startingPoints.add(new Coordinate(20, 20));
        startingPoints.add(new Coordinate(21, 21));
        startingPoints.add(new Coordinate(22, 21));
        startingPoints.add(new Coordinate(23, 21));
        when(trackMock.getStartPoints()).thenReturn(startingPoints);

        players = new ArrayList<>();
        Player playerOne = mock(Player.class);
        when(playerOne.getCar()).thenReturn(new Car(10, 10, Color.RED));
        Player playerTwo = mock(Player.class);
        when(playerTwo.getCar()).thenReturn(new Car(10, 10, Color.RED));
        players.add(playerOne);
        players.add(playerTwo);
    }

    @Test
    public void carLeavesTrackShouldLeaveTrack() {
        GameEngine bobbyCarEngine = spy(new BobbyCarEngine(players, trackMock));
        Coordinate offTrack = new Coordinate(100, 100);
        bobbyCarEngine.applyGameRules(offTrack);
        verify(bobbyCarEngine, times(1)).carLeavesTrack(Mockito.any());
    }

    @Test
    public void bobbyCarCrashes() {
        players.remove(1);
        GameEngine bobbyCarEngine = new BobbyCarEngine(players, trackMock);
        bobbyCarEngine.carCrashes();
        assertTrue(bobbyCarEngine.getCurrentPlayer().getCar().hasCrashed());
    }
}