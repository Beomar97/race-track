package com.pathfinder.racetrack.model;

import com.pathfinder.racetrack.model.exceptions.NotAValidTrackException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TrackTest extends ApplicationTest {
    private Track track;

    @Before
    public void setUp() throws IOException, NotAValidTrackException {
        track = new Track("Test Track", new File("src/test/resources/tracks/Track_01.png"), 25, 25);
    }

    @Test
    public void onTrack() {
        assertFalse(track.onTrack(new Coordinate(0, 0)));
        assertFalse(track.onTrack(new Coordinate(25,25)));
        assertTrue(track.onTrack(new Coordinate(2, 12)));
        assertFalse(track.onTrack(new Coordinate(-1,-1)));
        assertFalse(track.onTrack(new Coordinate(26,26)));
    }

    @Test
    public void finishLineLocation() {
        Assert.assertEquals(1, track.getFinishLine().getP().getX());
        Assert.assertEquals(13, track.getFinishLine().getP().getY());
        Assert.assertEquals(3, track.getFinishLine().getQ().getX());
        Assert.assertEquals(13, track.getFinishLine().getQ().getY());
    }

    @Test
    public void crossFinishLine() {
        assertTrue(track.hasCrossedFinishLine(new Coordinate(1,13), new Coordinate(1,13)));
        assertTrue(track.hasCrossedFinishLine(new Coordinate(2,13), new Coordinate(2,13)));
        assertTrue(track.hasCrossedFinishLine(new Coordinate(3,13), new Coordinate(3,13)));
        assertFalse(track.hasCrossedFinishLine(new Coordinate(0,13), new Coordinate(0,13)));
        assertFalse(track.hasCrossedFinishLine(new Coordinate(4,13), new Coordinate(4,13)));
        assertFalse(track.hasCrossedFinishLine(new Coordinate(0,12), new Coordinate(0,12)));
        assertFalse(track.hasCrossedFinishLine(new Coordinate(0,13), new Coordinate(4,13)));
        assertFalse(track.hasCrossedFinishLine(new Coordinate(0,14), new Coordinate(4,14)));
        assertTrue(track.hasCrossedFinishLine(new Coordinate(0,5), new Coordinate(3,17)));
    }

    @Test
    public void startingPoints() {
        List<Coordinate> startingPointsFoundOnTrack = track.getStartPoints();
        List<Coordinate> expectedStartingPoints = new ArrayList<Coordinate>();
        List<Coordinate> expectedNotStartingPoints = new ArrayList<Coordinate>();
        expectedStartingPoints.add(new Coordinate(2,13));
        expectedStartingPoints.add(new Coordinate(3,13));

        expectedNotStartingPoints.add(new Coordinate(1,13));
        expectedNotStartingPoints.add(new Coordinate(4,13));

        for (int i = 0; i < expectedStartingPoints.size(); i++) {
            assertTrue(expectedStartingPoints.get(i).equals(startingPointsFoundOnTrack.get(i)));
        }

    }
}