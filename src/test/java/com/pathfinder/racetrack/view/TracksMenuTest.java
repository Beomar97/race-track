package com.pathfinder.racetrack.view;

import com.pathfinder.racetrack.model.ExistingTracks;
import com.pathfinder.racetrack.model.Track;
import com.pathfinder.racetrack.model.exceptions.NotAValidTrackException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;
import org.testfx.framework.junit.ApplicationTest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import static org.mockito.Mockito.*;

public class TracksMenuTest extends ApplicationTest {

    TracksMenuController tracksMenuControllerSpy;

    @Mock
    SceneController sceneControllerMock;

    @FXML
    private Text trackName;
    @FXML
    private Button btnDeleteTrack;
    @FXML
    private Button btnLoadNewTrack;
    @FXML
    private Button btnMainMenu;
    @FXML
    private ImageView trackImage;
    @FXML
    private ListView<String> listView;
    @FXML
    private Text trackDescription;

    @Override
    public void start(Stage stage) {
        ExistingTracks.initTracks("src/test/resources/tracks");
        sceneControllerMock = mock(SceneController.class);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/TracksMenu.fxml"));
        tracksMenuControllerSpy = spy(new TracksMenuController());
        loader.setController(tracksMenuControllerSpy);
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
    public void eventHandleShouldBeCalled() {
        doNothing().when(tracksMenuControllerSpy).handleChangeTrack(-1);
        clickOn("#btnBackTracks");
        verify(tracksMenuControllerSpy, atLeast(1)).handleChangeTrack(-1);

        doNothing().when(tracksMenuControllerSpy).handleChangeTrack(1);
        clickOn("#btnForwardTracks");
        verify(tracksMenuControllerSpy, atLeast(1)).handleChangeTrack(1);

        doNothing().when(tracksMenuControllerSpy).handleDeleteTrack();
        clickOn("#btnDeleteTrack");
        verify(tracksMenuControllerSpy, atLeast(1)).handleDeleteTrack();

        doNothing().when(tracksMenuControllerSpy).handleLoadNewTrack();
        clickOn("#btnLoadNewTrack");
        verify(tracksMenuControllerSpy, atLeast(1)).handleLoadNewTrack();
    }

    @Test
    public void isDefaultTrack() {
        Assert.assertTrue(tracksMenuControllerSpy.isDefaultTrack("The Donut"));
        Assert.assertFalse(tracksMenuControllerSpy.isDefaultTrack("Test Track"));
    }

    @Test
    public void handleChangeTrack() throws NotAValidTrackException, FileNotFoundException {
        ExistingTracks.clear();
        tracksMenuControllerSpy.setTracks(new ArrayList<Track>());


        tracksMenuControllerSpy.handleChangeTrack(1);
        Assert.assertEquals(0, tracksMenuControllerSpy.getSelectedTrackId());

        Track t = new Track("Test Track", new File("src/test/resources/tracks/Track_01.png"), 25, 25);
        ArrayList<Track> tl = new ArrayList<>();
        tl.add(t);
        tracksMenuControllerSpy.setTracks(new ArrayList<Track>());
        tracksMenuControllerSpy.handleChangeTrack(1);
        Assert.assertEquals(0, tracksMenuControllerSpy.getSelectedTrackId());
    }
}
