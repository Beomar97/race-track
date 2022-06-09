package com.pathfinder.racetrack.model;

import javafx.scene.paint.Color;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {
    private Player player;

    @Before
    public void init() {
        player = new Player(1, "Hans", new Color(1, 1, 1, 0));
    }

    @Test
    public void playerShouldInit() {
        assertEquals(1, player.getId());
        assertEquals("Hans", player.getName());
        assertFalse(player.getFinished());
    }

    @Test
    public void playerShouldFinish() {
        player.setFinished(true);
        assertTrue(player.getFinished());
    }

    @Test
    public void playerShouldSetName() {
        player.setName("asdf");
        assertEquals("asdf", player.getName());
    }

    @Test
    public void playerShouldRetire() {
        assertFalse(player.getRetired());
        player.setRetired(true);
        assertTrue(player.getRetired());
    }
}