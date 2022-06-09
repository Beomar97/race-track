package com.pathfinder.racetrack.model;

import org.assertj.core.error.ShouldContainOnlyDigits;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CoordinateTest extends ApplicationTest {
    Coordinate c1;
    Coordinate c2;
    Coordinate c3;

    @Before
    public void setUp() {
        c1 = new Coordinate(1,1);
        c2 = new Coordinate(1,1);
        c3 = new Coordinate(0,1);
    }

    @Test
    public void shouldBeEqual() {
        Assert.assertEquals(c1, c2);
        Assert.assertNotEquals(c1,c3);
        assertTrue(c1.equals(c2));
        assertFalse(c1.equals((c3)));
    }
}
