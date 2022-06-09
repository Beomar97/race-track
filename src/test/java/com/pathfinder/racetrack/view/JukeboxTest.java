package com.pathfinder.racetrack.view;

import org.junit.Test;

public class JukeboxTest {

    @Test(expected = IllegalStateException.class)
    public void shouldThrowException() {
        new Jukebox();
    }

}