package com.pathfinder.racetrack.view;

import com.pathfinder.racetrack.model.GameEngine;
import com.pathfinder.racetrack.model.GoKartEngine;
import com.pathfinder.racetrack.model.Player;
import com.pathfinder.racetrack.model.Track;
import com.pathfinder.racetrack.model.exceptions.NotAValidTrackException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.junit.Assert;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import static org.mockito.Mockito.*;

public class PlayingFieldTest extends ApplicationTest {
    private PlayingFieldController pfcSpy;
    private GameEngine gameEngine;
    private SceneController sceneController;

    /**
     * Will be called with {@code @Before} semantics, i. e. before each test method.
     */
    @Override
    public void start(Stage stage) throws FileNotFoundException, NotAValidTrackException {
        ArrayList<Player> players = new ArrayList<>();
        Player hans = new Player(1, "Hans", Color.RED);
        Player peter = new Player(2, "Peter", Color.BLUE);
        players.add(hans);
        players.add(peter);

        Track track = new Track("Track_01", new File("src/test/resources/tracks/Track_01.png"), 46, 34);
        gameEngine = spy(new GoKartEngine(players, track));
        sceneController = mock(SceneController.class);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PlayingField.fxml"));
        pfcSpy = spy(new PlayingFieldController(gameEngine));
        loader.setController(pfcSpy);
        Parent root;
        try {
            root = loader.load();
            stage.setTitle("RaceTrack TEST");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void eventHandleShouldBeCalledWithKeyboard() {
        clickOn("#hbox");
        press(KeyCode.DIGIT1);
        verify(pfcSpy, times(1)).handlePlayerAction(0);
        press(KeyCode.DIGIT2);
        verify(pfcSpy, times(1)).handlePlayerAction(1);
        press(KeyCode.DIGIT3);
        verify(pfcSpy, times(1)).handlePlayerAction(2);
        press(KeyCode.DIGIT4);
        verify(pfcSpy, times(1)).handlePlayerAction(3);
        press(KeyCode.DIGIT5);
        verify(pfcSpy, times(1)).handlePlayerAction(4);
        press(KeyCode.DIGIT6);
        verify(pfcSpy, times(1)).handlePlayerAction(5);
        press(KeyCode.DIGIT7);
        verify(pfcSpy, times(1)).handlePlayerAction(6);
        press(KeyCode.DIGIT8);
        verify(pfcSpy, times(1)).handlePlayerAction(7);
        press(KeyCode.DIGIT9);
        verify(pfcSpy, times(1)).handlePlayerAction(8);
    }

    @Test
    public void shouldResetGame() {
        clickOn("#btnReset");
        verify(pfcSpy, times(1)).handleClickResetGame();
    }

    @Test
    public void shouldSaveGame() {
        clickOn("#btnSave");
        verify(pfcSpy, times(1)).handleClickSaveGame();
    }

    @Test
    public void gameShouldEnd() {
        gameEngine.setGameEnded(true);
        clickOn("#hbox");
        press(KeyCode.DIGIT1);
        verify(gameEngine, times(1)).setGameEnded(true);
        Assert.assertTrue(gameEngine.getGameEnded());

    }
}
