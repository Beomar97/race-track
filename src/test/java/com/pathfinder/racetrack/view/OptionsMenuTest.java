package com.pathfinder.racetrack.view;

import com.pathfinder.racetrack.model.ExistingTracks;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.junit.Assert;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.*;

public class OptionsMenuTest extends ApplicationTest {
    private static final Color PLAYER1SETCOLOR = Color.RED;
    private static final Color PLAYER2SETCOLOR = Color.BLUE;
    private static final Color PLAYER3SETCOLOR = Color.GREEN;
    private static final Color PLAYER4SETCOLOR = Color.YELLOW;
    private static final String PLAYER1NAME = "Tester";
    private static final String COLORERROR = "Change Color!";
    private static final String PLAYERERROR = "Fill in at least one name!";
    OptionsMenuController omcSpy;
    SceneController sceneControllerMock;

    /**
     * Will be called with {@code @Before} semantics, i. e. before each test method.
     */
    @Override
    public void start(Stage stage) {
        ExistingTracks.initTracks("src/test/resources/tracks");
        this.sceneControllerMock = mock(SceneController.class);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/OptionsMenu.fxml"));
        omcSpy = spy(new OptionsMenuController());
        loader.setController(omcSpy);
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
     * Tests if players are initialized
     */
    @Test
    public void shouldInitPlayers() {
        assertEquals(4, omcSpy.getPlayers().size());
    }

    /**
     * Tests if default colors are initialized
     */
    @Test
    public void shouldInitColors() {
        assertEquals(PLAYER1SETCOLOR, omcSpy.getPlayers().get(0).getCar().getColor());
        assertEquals(PLAYER2SETCOLOR, omcSpy.getPlayers().get(1).getCar().getColor());
        assertEquals(PLAYER3SETCOLOR, omcSpy.getPlayers().get(2).getCar().getColor());
        assertEquals(PLAYER4SETCOLOR, omcSpy.getPlayers().get(3).getCar().getColor());
    }

    /**
     * Tests if tracks are initialized and default track exists
     */
    @Test
    public void shouldInitTracks() {
        assertEquals("Monaco", omcSpy.getTracks().get(0).getName());
    }

    /**
     * Tests if game modes are initialized
     */
    @Test
    public void shouldInitModes() {
        assertEquals(omcSpy.getGoKartRadioButton(), omcSpy.getRadioBtnGroup().getSelectedToggle());
    }

    /**
     * Throws an exception when saving a name because there is no player with index 4
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void shouldThrowIndexOutOfBoundExceptionWhenChangingName() {
        omcSpy.handleSaveName(false, 4, PLAYER1NAME);
    }

    /**
     * Tests if a color can be saved with the handleSaveColor method
     */
    @Test
    public void shouldSaveColor() {
        omcSpy.handleSaveColor(0, Color.PINK);
        assertEquals(Color.PINK, omcSpy.getPlayers().get(0).getCar().getColor());
        omcSpy.handleSaveColor(1, Color.RED);
        assertEquals(Color.RED, omcSpy.getPlayers().get(1).getCar().getColor());
        omcSpy.handleSaveColor(2, Color.GHOSTWHITE);
        assertEquals(Color.GHOSTWHITE, omcSpy.getPlayers().get(2).getCar().getColor());
        omcSpy.handleSaveColor(3, Color.MEDIUMVIOLETRED);
        assertEquals(Color.MEDIUMVIOLETRED, omcSpy.getPlayers().get(3).getCar().getColor());
    }

    /**
     * Throws an exception when saving a color because there is no player with index 4
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void shouldThrowIndexOutOfBoundExceptionWhenChangingColor() {
        omcSpy.handleSaveColor(4, Color.RED);
    }


    /**
     * Tests if all modes can be selected and if it is correctly saved
     */
    @Test
    public void shouldChoseMode() {
        omcSpy.getFormulaOneRadioButton().setSelected(true);
        assertEquals(omcSpy.getFormulaOneRadioButton(), omcSpy.getRadioBtnGroup().getSelectedToggle());
        omcSpy.getBobbyCarRadioButton().setSelected(true);
        assertEquals(omcSpy.getBobbyCarRadioButton(), omcSpy.getRadioBtnGroup().getSelectedToggle());
        omcSpy.getGoKartRadioButton().setSelected(true);
        assertEquals(omcSpy.getGoKartRadioButton(), omcSpy.getRadioBtnGroup().getSelectedToggle());
    }

    /**
     * Tests if saved name is in the players array
     */
    @Test
    public void shouldSaveName() {
        omcSpy.handleSaveName(false, 0, "Test1");
        assertEquals("Test1", omcSpy.getPlayers().get(0).getName());
        omcSpy.handleSaveName(false, 1, "Test2");
        assertEquals("Test2", omcSpy.getPlayers().get(1).getName());
        omcSpy.handleSaveName(false, 2, "Test3");
        assertEquals("Test3", omcSpy.getPlayers().get(2).getName());
        omcSpy.handleSaveName(false, 3, "Test4");
        assertEquals("Test4", omcSpy.getPlayers().get(3).getName());
    }

    /**
     * Tests if error is displayed when method showError(int playerIndex, String errorMessage) is called
     */
    @Test
    public void shouldShowError() {
        omcSpy.showError(0, "Error!");
        assertEquals("Error!", omcSpy.getPlayer1Error().getText());
        omcSpy.showError(1, "Error!");
        assertEquals("Error!", omcSpy.getPlayer2Error().getText());
        omcSpy.showError(2, "Error!");
        assertEquals("Error!", omcSpy.getPlayer3Error().getText());
        omcSpy.showError(3, "Error!");
        assertEquals("Error!", omcSpy.getPlayer4Error().getText());

        // Clean up
        omcSpy.showError(0, "");
        omcSpy.showError(1, "");
        omcSpy.showError(2, "");
        omcSpy.showError(3, "");
    }

    /**
     * Tests if error is displayed when same colored is selected or forbidden color
     */
    @Test
    public void shouldShowErrorOnPlayer1() {
        omcSpy.handleSaveColor(0, Color.BLUE);
        assertEquals(COLORERROR, omcSpy.getPlayer1Error().getText());

        assertEquals(false, omcSpy.getCheckedColor());
        omcSpy.handleStartGame();
        verify(omcSpy, never()).handleGameMode();
        verify(omcSpy, never()).createGame();

        // Clean up
        omcSpy.handleSaveColor(0, PLAYER1SETCOLOR);
        omcSpy.handleSaveColor(0, Color.WHITE);
        assertEquals(COLORERROR, omcSpy.getPlayer1Error().getText());
        assertEquals(false, omcSpy.getCheckedColor());
        verify(omcSpy, never()).handleGameMode();
        verify(omcSpy, never()).createGame();

        // Clean up
        omcSpy.handleSaveColor(0, PLAYER1SETCOLOR);
    }

    /**
     * Tests if game has not started because no player name is given and checks error
     */
    @Test
    public void shouldNotStartGameNoName() {
        omcSpy.handleStartGame();
        assertFalse(omcSpy.checkPlayers());
        assertEquals(PLAYERERROR, omcSpy.getPlayer1Error().getText());
        verify(omcSpy, never()).handleGameMode();
        verify(omcSpy, never()).createGame();

        // Clean up
        omcSpy.showError(0, "");
    }

    /**
     * Tests if a normal game can be played and verifies if certain methods are called
     */
    @Test
    public void shouldStartGameWithName() {
        Assert.assertNull(SceneController.getPlayingFieldScene());
        omcSpy.handleSaveName(false, 0, "Test");
        Platform.runLater(() -> omcSpy.handleStartGame());

        Platform.runLater(() -> Assert.assertNotNull(SceneController.getPlayingFieldScene()));
        assertEquals(4, omcSpy.getPlayers().size());
    }

}
