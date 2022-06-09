package com.pathfinder.racetrack.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.Test;
import org.mockito.Mock;
import org.testfx.framework.junit.ApplicationTest;

import java.io.IOException;

import static org.mockito.Mockito.*;

public class StartMenuTest extends ApplicationTest {
    StartMenuController smcSpy;
    @Mock
    SceneController sceneController;

    /**
     * Will be called with {@code @Before} semantics, i. e. before each test method.
     */
    @Override
    public void start(Stage stage) {
        sceneController = mock(SceneController.class);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/StartMenu.fxml"));
        smcSpy = spy(new StartMenuController());
        loader.setController(smcSpy);
        Parent root;
        try {
            root = loader.load();
            stage.setTitle("RaceTrack TEST");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Tests if every button works and behaves as expected
     */
    @Test
    public void eventHandleShouldBeCalled() {
        doNothing().when(smcSpy).handleNewGame();
        doNothing().when(smcSpy).handleTracks();
        doNothing().when(smcSpy).handleQuitGame();
        doNothing().when(smcSpy).handleLoadGame();
        doNothing().when(smcSpy).handleRules();
        doNothing().when(smcSpy).handleCredits();
        doNothing().when(smcSpy).handleHighScore();
        clickOn("#newGame");
        verify(smcSpy, atLeast(1)).handleNewGame();
        clickOn("#loadGame");
        verify(smcSpy, atLeast(1)).handleLoadGame();
        clickOn("#tracks");
        verify(smcSpy, atLeast(1)).handleTracks();
        clickOn("#rules");
        verify(smcSpy, atLeast(1)).handleRules();
        clickOn("#quitGame");
        verify(smcSpy, atLeast(1)).handleQuitGame();
        clickOn("#credits");
        verify(smcSpy, atLeast(1)).handleCredits();
        clickOn("#viewHighScore");
        verify(smcSpy, atLeast(1)).handleHighScore();
    }
}