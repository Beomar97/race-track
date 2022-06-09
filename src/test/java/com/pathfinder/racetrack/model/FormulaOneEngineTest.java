package com.pathfinder.racetrack.model;

import javafx.scene.paint.Color;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class FormulaOneEngineTest {
    Track trackMock;

    @Before
    public void init() {
        trackMock = mock(Track.class);
        ArrayList<Coordinate> startingPoints = new ArrayList<>();
        startingPoints.add(new Coordinate(20, 20));
        startingPoints.add(new Coordinate(21, 21));
        startingPoints.add(new Coordinate(22, 21));
        startingPoints.add(new Coordinate(23, 21));
        when(trackMock.getStartPoints()).thenReturn(startingPoints);
    }

    @Test
    public void carCrashesShouldRetirePlayer() {
        ArrayList<Player> players = new ArrayList<>();

        Player playerOne = mock(Player.class);
        when(playerOne.getCar()).thenReturn(new Car(10, 10, Color.RED));
        Player playerTwo = mock(Player.class);
        when(playerTwo.getCar()).thenReturn(new Car(20, 20, Color.RED));
        players.add(playerOne);
        players.add(playerTwo);

        FormulaOneEngine formulaOneEngine = spy(new FormulaOneEngine(players, trackMock));

        formulaOneEngine.applyGameRules(formulaOneEngine.getNextPlayer().getCar().getPosition());
        verify(formulaOneEngine, times(1)).carCrashes();
    }

    @Test
    public void carLeavesTrackShouldRetirePlayer() {
        ArrayList<Player> players = new ArrayList<>();

        Player playerOne = mock(Player.class);
        when(playerOne.getCar()).thenReturn(new Car(0, 0, Color.RED));
        players.add(playerOne);

        Coordinate movesTo = new Coordinate(1, 1);

        FormulaOneEngine formulaOneEngine = spy(new FormulaOneEngine(players, trackMock));
        formulaOneEngine.applyGameRules(movesTo);
        verify(formulaOneEngine, times(1)).carLeavesTrack(movesTo);
    }

    @Test
    public void f1CarCrashes() {
        ArrayList<Player> players = new ArrayList<>();
        Player playerOne = mock(Player.class);
        when(playerOne.getCar()).thenReturn(new Car(10, 10, Color.RED));
        players.add(playerOne);
        GameEngine f1Engine = new FormulaOneEngine(players, trackMock);
        f1Engine.carCrashes();
        assertTrue(f1Engine.getCurrentPlayer().getCar().hasCrashed());
    }
}