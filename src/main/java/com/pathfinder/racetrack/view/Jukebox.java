package com.pathfinder.racetrack.view;

import javafx.scene.media.AudioClip;

import java.net.URL;

/**
 * Plays fun sound effects
 */
public class Jukebox {
    private static final String FANFARE_AUDIO = "/audio/fanfare.mp3";
    private static final String JUMP_AUDIO = "/audio/jump.mp3";
    private static final String CRASH_AUDIO = "/audio/crash.mp3";

    Jukebox() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Plays a ceremonial tune
     */
    public static void playFanfare() {
        URL audioURL = Jukebox.class.getResource(FANFARE_AUDIO);
        AudioClip audioClip = new AudioClip(audioURL.toString());
        audioClip.play();
    }

    /**
     * Plays a popping sound
     */
    public static void playJump() {
        URL audioURL = Jukebox.class.getResource(JUMP_AUDIO);
        AudioClip audioClip = new AudioClip(audioURL.toString());
        audioClip.play();
    }

    /**
     * Plays the sound of a car crashing
     */
    public static void playCrash() {
        URL audioURL = Jukebox.class.getResource(CRASH_AUDIO);
        AudioClip audioClip = new AudioClip(audioURL.toString());
        audioClip.play();
    }
}
