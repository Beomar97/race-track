package com.pathfinder.racetrack.view;

import com.pathfinder.racetrack.model.GameEngine;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

public class SceneTest extends ApplicationTest {
    private static final String TITLE = "RaceTrack";

    /**
     * Will be called with {@code @Before} semantics, i. e. before each test method.
     */
    @Override
    public void start(Stage stage) {
        SceneController.setStage(stage);
        SceneController.setTitle(TITLE);
        SceneController.setHandlers();
    }

    /**
     * Throw exception if SceneController object is initialized by SceneController class
     */
    @Test(expected = IllegalStateException.class)
    public void createSceneControllerObject(){
        SceneController sceneController = new SceneController();
    }

    /**
     * Tests if StartMenu is initialized as a scene
     */
    @Test
    public
    void shouldInitStartMenu() {
        assertNotNull(SceneController.getStartMenuScene());
    }

    /**
     * Tests if OptionsMenu is initialized as a scene
     */
    @Test
    public void shouldInitOptionsMenu() {
        assertNotNull(SceneController.getOptionsMenuScene());
        assertNotNull(SceneController.getOptionsMenuController());
    }

    /**
     * Tests if HighScoreMenu is initialized as a scene
     */
    @Test
    public void shouldInitHighScoreMenu() {
        assertNotNull(SceneController.getHighScoreScene());
        assertNotNull(SceneController.getHighScoreSceneController());
    }

    /**
     * Tests if PlayingField is initialized as a scene
     * Throws an exception because of not existing game engine
     */
    @Test(expected = IllegalStateException.class)
    public void shouldInitPlayingField() {
        GameEngine gameEngine = mock(GameEngine.class);
        SceneController.initPlayingFieldScene(gameEngine);
    }

    /**
     * Tests if TrackMenu is initialized as a scene
     */
    @Test
    public void shouldInitTrackMenu() {
        assertNotNull(SceneController.getTracksMenuScene());
        assertNotNull(SceneController.getTracksMenuSceneController());
    }

    /**
     * Tests if Rules is initialized as a scene
     */
    @Test
    public void shouldInitRules() {
        assertNotNull(SceneController.getRulesScene());
    }

    /**
     * Tests if Credits is initialized as a scene
     */
    @Test
    public void shouldInitCredits() {
        assertNotNull(SceneController.getCreditsScene());
    }
}
