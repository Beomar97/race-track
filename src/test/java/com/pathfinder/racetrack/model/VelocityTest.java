package com.pathfinder.racetrack.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class VelocityTest {

    @Test
    public void calcLength() {
        Velocity velocity1 = new Velocity(8, 4);
        assertEquals(8.94427191, velocity1.getLength(), 0.001);
        Velocity velocity2 = new Velocity(102, 45);
        assertEquals(111.485425056, velocity2.getLength(), 0.001);
        Velocity velocity3 = new Velocity(1, 1);
        assertEquals(1.4142135, velocity3.getLength(), 0.001);
    }
}