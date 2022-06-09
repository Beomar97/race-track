package com.pathfinder.racetrack.model;

import com.pathfinder.racetrack.model.exceptions.NotAValidTrackException;
import com.pathfinder.racetrack.model.exceptions.TrackNotAvailableException;
import org.junit.*;
import org.testfx.framework.junit.ApplicationTest;

import java.io.File;
import java.io.IOException;

public class ExistingTracksTest extends ApplicationTest {
    Track track;

    @Before
    public void setUp() throws IOException, NotAValidTrackException {
        track = new Track("Test Track", new File("src/test/resources/tracks/Track_01.png"), 25, 25);
    }
    
    @After
    public void tearDown() {
        ExistingTracks.clear();
    }

    @Test
    public void addTrack() throws TrackNotAvailableException {
        ExistingTracks.addTrack(track);
        Assert.assertEquals(track, ExistingTracks.getTrackByName("Test Track"));
    }

    @Test(expected = TrackNotAvailableException.class)
    public void getTrack() throws TrackNotAvailableException {
        ExistingTracks.getTrackByName("");
    }

    //@Ignore
    @Test(expected = TrackNotAvailableException.class)
    public void removeTrack() throws TrackNotAvailableException {
        ExistingTracks.addTrack(track);
        ExistingTracks.removeTrackByName("Test Track");
        ExistingTracks.getTrackByName("Test Track");
    }

}
