package com.pathfinder.racetrack.model;

import com.pathfinder.racetrack.model.exceptions.NotAValidTrackException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class LineTest extends ApplicationTest {
    private static final int RANDOMMULTIPLIER = 100;
    private Line line0;
    private Line lineH;
    private Line lineV;
    private Line lineN;
    private Line linePN;
    private Line lineR;
    private int aX;
    private int aY;
    private int bX;
    private int bY;

    @Before
    public void setUp() throws IOException, NotAValidTrackException {
        line0 = new Line(new Coordinate(0,0), new Coordinate(0, 0));
        lineH = new Line(new Coordinate(25, 13), new Coordinate(50, 13));
        lineV = new Line(new Coordinate(10, 17), new Coordinate(10, 80));
        lineN = new Line(new Coordinate(30, 45), new Coordinate(1,23));
        linePN = new Line(new Coordinate(15, 54), new Coordinate(-14,32));

        aX = (int) Math.random() * RANDOMMULTIPLIER;
        aY = (int) Math.random() * RANDOMMULTIPLIER;
        bX = (int) Math.random() * RANDOMMULTIPLIER;
        bY = (int) Math.random() * RANDOMMULTIPLIER;

        lineR = new Line(new Coordinate(aX, aY), new Coordinate(bX, bY));
    }

    @Test
    public void vertical() {
        assertTrue(line0.isVertical());
        assertFalse(lineH.isVertical());
        assertTrue(lineV.isVertical());
        assertFalse(lineN.isVertical());
    }

    @Test
    public void horizontal() {
        assertTrue(line0.isHorizontal());
        assertTrue(lineH.isHorizontal());
        assertFalse(lineV.isHorizontal());
        assertFalse(lineN.isHorizontal());
    }
    @Test
    public void gradient() {
        Assert.assertEquals(Integer.MAX_VALUE, line0.getGradient(), 0);
        Assert.assertEquals(0, lineH.getGradient(), 0);
        Assert.assertEquals(Integer.MAX_VALUE, lineV.getGradient(), 0);
        Assert.assertEquals(0.7586206896551724, lineN.getGradient(), 0.001);
    }

    @Test
    public void xRange() {
        assertTrue(line0.isInXRange(0));
        assertFalse(line0.isInXRange(-0.999));
        assertFalse(line0.isInXRange(0.001));

        assertTrue(lineH.isInXRange(25));
        assertTrue(lineH.isInXRange(50));
        assertFalse(lineH.isInXRange(24.9));
        assertFalse(lineH.isInXRange(50.001));

        assertTrue(lineV.isInXRange(10));
        assertFalse(lineV.isInXRange(9.99));
        assertFalse(lineV.isInXRange(10.001));

        assertTrue(lineN.isInXRange(1));
        assertTrue(lineN.isInXRange(30));
        assertFalse(lineN.isInXRange(0.999));
        assertFalse(lineN.isInXRange(30.001));
    }

    @Test
    public void yRange() {
        assertTrue(line0.isInYRange(0));
        assertFalse(line0.isInXRange(-0.999));
        assertFalse(line0.isInXRange(0.001));

        assertTrue(lineH.isInYRange(13));
        assertFalse(lineH.isInYRange(12.999));
        assertFalse(lineN.isInYRange(13.001));

        assertTrue(lineV.isInYRange(17));
        assertTrue(lineV.isInYRange(80));
        assertFalse(lineV.isInYRange(16.999));
        assertFalse(lineV.isInYRange(80.001));

        assertTrue(lineN.isInYRange(23));
        assertTrue(lineN.isInYRange(45));
        assertFalse(lineN.isInYRange(22.999));
        assertFalse(lineN.isInYRange(45.001));
    }

    @Test
    public void parallel() {
        assertFalse(lineV.isParallelTo(lineH));
        assertFalse(lineH.isParallelTo(lineV));
        assertFalse(lineV.isParallelTo(lineN));
        assertFalse(lineN.isParallelTo(lineV));
        assertFalse(lineH.isParallelTo(lineN));
        assertFalse(lineN.isParallelTo(lineH));

        assertTrue(lineN.isParallelTo(linePN));
        assertTrue(linePN.isParallelTo(lineN));
    }
}
