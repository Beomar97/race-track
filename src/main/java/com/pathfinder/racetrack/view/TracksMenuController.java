package com.pathfinder.racetrack.view;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.xmp.XmpDirectory;
import com.pathfinder.racetrack.model.ExistingTracks;
import com.pathfinder.racetrack.model.Track;
import com.pathfinder.racetrack.model.exceptions.DeleteDefaultTrackException;
import com.pathfinder.racetrack.model.exceptions.NotAValidTrackException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;

/**
 * Class representing the logic for the Tracks Menu
 */
public class TracksMenuController {
    private static final Logger logger = LoggerFactory.getLogger(TracksMenuController.class);

    private static final String[] DEFAULT_TRACKS = {
            "The Donut",
            "The Misty Forest",
            "Rainbow Hills",
            "Monaco"
    };
    private static final String DEFAULT_TRACK_DESCRIPTION = "Pretty nice track you got there my friend!";
    private static final Path DEFAULT_TRACK_DIRECTORY = Paths.get(System.getProperty("user.home"), "RaceTrack", "tracks");
    private int selectedTrackId;
    private List<Track> tracks;

    @FXML
    private Text trackNameTracks;

    @FXML
    private Button btnBackTracks;
    @FXML
    private ImageView imageTrackLeftTracks;
    @FXML
    private ImageView imageTrackChosenTracks;
    @FXML
    private ImageView imageTrackRightTracks;
    @FXML
    private Button btnForwardTracks;

    @FXML
    private Button btnDeleteTrack;
    @FXML
    private Text trackDescription;
    @FXML
    private Button btnLoadNewTrack;
    @FXML
    private Button btnMainMenu;

    /**
     * Initialize method which sets up the handlers and the listView with the tracks
     */
    @FXML
    public void initialize() {
        setHandlers();
        resetImageSelection();
    }

    /**
     * Sets handlers on events from the listView and buttons
     */
    private void setHandlers() {
        btnBackTracks.setOnAction(e -> handleChangeTrack(-1));
        btnForwardTracks.setOnAction(e -> handleChangeTrack(1));
        btnDeleteTrack.setOnAction(e -> handleDeleteTrack());
        btnLoadNewTrack.setOnAction(e -> handleLoadNewTrack());
        btnMainMenu.setOnAction(e -> SceneController.setStartMenuScene());
    }

    /**
     * Handles the updates when the tracks get changes in the cover flow
     *
     * @param direction change value of the direction (right -1 or left +1)
     */
    public void handleChangeTrack(int direction) {

        String selectedTrackName;
        selectedTrackId += direction;

        switch (tracks.size()) {
            case 0:
                Image trackTemplate = new Image(getClass().getResource("/tracks/Track_Template.png").toExternalForm());
                trackNameTracks.setText("No tracks found!");
                imageTrackChosenTracks.setImage(trackTemplate);
                trackDescription.setText("No tracks could have been found! Please add tracks to the game!");
                selectedTrackId = 0;
                break;
            case 1:
                selectedTrackId = 0;
                selectedTrackName = tracks.get(selectedTrackId).getName();
                updateTrackName(selectedTrackName);
                updateTrackImage(selectedTrackId, selectedTrackId, selectedTrackId);
                updateTrackDescription(selectedTrackName);
                selectedTrackId = 0;
                break;
            default:
                selectedTrackId = getValidTracksId(tracks.size(), selectedTrackId);
                int leftTrackId = getValidTracksId(tracks.size(), selectedTrackId - 1);
                int rightTrackId = getValidTracksId(tracks.size(), selectedTrackId + 1);
                selectedTrackName = tracks.get(selectedTrackId).getName();
                updateTrackName(selectedTrackName);
                updateTrackImage(selectedTrackId, leftTrackId, rightTrackId);
                updateTrackDescription(selectedTrackName);

        }
    }

    /**
     * Returns a valid track id from existing tracks
     *
     * @param tracksSize      size of tracks
     * @param selectedTrackId id of the selected track
     * @return valid id for the selected track
     */
    private int getValidTracksId(int tracksSize, int selectedTrackId) {
        if (selectedTrackId < 0) {
            return tracksSize - 1;
        } else if (selectedTrackId >= tracksSize) {
            return 0;
        }
        return selectedTrackId;
    }

    /**
     * Handles the deletion of the selected track from the cover flow
     */
    public void handleDeleteTrack() {
        String nameOfSelectedTrack = tracks.get(selectedTrackId).getName();
        if (!isDefaultTrack(nameOfSelectedTrack)) {
            File trackFile = getTrackFileOfSelectedTrack(nameOfSelectedTrack);
            try {
                Files.deleteIfExists(trackFile.toPath());
                ExistingTracks.removeTrackByName(nameOfSelectedTrack);
                resetImageSelection();
            } catch (IOException e) {
                ExceptionDialog.showDialog("Couldn't complete action! Error occurred during track deletion...", e);
            }
        } else {
            ExceptionDialog.showDialog("The deletion of default tracks is prohibited!",
                    new DeleteDefaultTrackException("Trying to delete a default track..."));
        }
    }

    /**
     * Checks if the track is part of the default tracks
     *
     * @param nameOfSelectedTrack name of the selected track
     * @return boolean if the track is a default track or not
     */
    protected boolean isDefaultTrack(String nameOfSelectedTrack) {
        for (String defaultTrack : DEFAULT_TRACKS) {
            if (nameOfSelectedTrack.equals(defaultTrack)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Handles the loading of a new custom track into the game
     */
    public void handleLoadNewTrack() {
        Stage mainStage = SceneController.getStage();
        File selectedFile = getSelectedFileFromFileChooser(mainStage);
        if (selectedFile != null && isValidTrack(selectedFile)) {
            Path fileInTracksFolderPath = DEFAULT_TRACK_DIRECTORY.resolve(selectedFile.getName());
            File fileInTracksFolder = fileInTracksFolderPath.toFile();
            try {
                Files.copy(selectedFile.toPath(), fileInTracksFolder.toPath(), StandardCopyOption.REPLACE_EXISTING);
                ExistingTracks.addTrack(new Track(FilenameUtils.removeExtension(fileInTracksFolder.getName()), fileInTracksFolder, MapPainter.getCountBoxesX(), MapPainter.getCountBoxesY()));
                resetImageSelection();
            } catch (IOException e) {
                ExceptionDialog.showDialog("Couldn't complete action! Error occurred during track import...", e);
            } catch (NotAValidTrackException e) {
                logger.error("Error occurred during import of track '{}'!", selectedFile.getName(), e);
            }
        }
    }

    /**
     * Gets a valid track object from a file
     *
     * @param trackFile File of the track
     * @return a valid Track object or null if it's not a valid track
     */
    private boolean isValidTrack(File trackFile) {
        try {
            new Track(FilenameUtils.removeExtension(trackFile.getName()), trackFile, MapPainter.getCountBoxesX(), MapPainter.getCountBoxesY());
            return true;
        } catch (NotAValidTrackException e) {
            ExceptionDialog.showDialog("Couldn't complete action! Selected file is not a valid track!",
                    new NotAValidTrackException("Trying to load an invalid track...", e));
            return false;
        } catch (FileNotFoundException e) {
            logger.error("Error occurred during validation of track '{}'!", trackFile.getName(), e);
            return false;
        }
    }

    /**
     * Gets a selected file from the File Chooser
     *
     * @param mainStage the main stage of the application
     * @return the selected file object
     */
    protected File getSelectedFileFromFileChooser(Stage mainStage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Track File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Track Files", "*.png"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        return fileChooser.showOpenDialog(mainStage);
    }

    /**
     * Resets the selected item with the first item in the listView
     */
    private void resetImageSelection() {
        tracks = ExistingTracks.getTracks();
        handleChangeTrack(0);
    }

    /**
     * Updates the display of the name for the currently selected track
     *
     * @param nameOfSelectedTrack name of the selected track
     */
    private void updateTrackName(String nameOfSelectedTrack) {
        trackNameTracks.setText(nameOfSelectedTrack);
    }

    /**
     * Updates the display of the preview images for the currently selected track and it's neighbours
     *
     * @param selectedTrackId id of the selected track and it's neighbours
     */
    private void updateTrackImage(int selectedTrackId, int leftTrackId, int rightTrackId) {
        imageTrackChosenTracks.setImage(tracks.get(selectedTrackId).getBitmap());
        imageTrackLeftTracks.setImage(tracks.get(leftTrackId).getBitmap());
        imageTrackRightTracks.setImage(tracks.get(rightTrackId).getBitmap());
    }

    /**
     * Updates the display of the description for the currently selected track
     *
     * @param nameOfSelectedTrack name of the selected track
     */
    private void updateTrackDescription(String nameOfSelectedTrack) {
        try {
            File trackFile = getTrackFileOfSelectedTrack(nameOfSelectedTrack);
            trackDescription.setText(getXmpDescriptionMetadata(trackFile));
        } catch (IOException | ImageProcessingException e) {
            logger.error("Error occurred during update of track description of '{}'!", nameOfSelectedTrack, e);
        }
    }

    /**
     * Gets the track description from the XMP Metadata in the image file of the track
     *
     * @param trackFile File of the track that the description should be extracted from
     * @return Returns the description of the track from the metadata as String
     * @throws ImageProcessingException when the metadata couldn't be read
     * @throws IOException              when an error occurs during the file read
     */
    private String getXmpDescriptionMetadata(File trackFile) throws ImageProcessingException, IOException {
        Metadata metadata = ImageMetadataReader.readMetadata(trackFile);
        XmpDirectory xmpDirectory = metadata.getFirstDirectoryOfType(XmpDirectory.class);
        try {
            return xmpDirectory.getXmpProperties().get("dc:description[1]");
        } catch (NullPointerException e) {
            logger.info("No custom description could be found for custom track '{}'. Returning default description...", trackFile.getName());
            return DEFAULT_TRACK_DESCRIPTION;
        }
    }

    /**
     * Gets the file object for the currently selected track
     *
     * @param nameOfSelectedTrack name of the selected track
     * @return Returns the file object of the currently selected track
     */
    private File getTrackFileOfSelectedTrack(String nameOfSelectedTrack) {
        return Objects.requireNonNull(ExistingTracks.getTracks().stream()
                .filter(track -> FilenameUtils.removeExtension(track.getName()).equals(nameOfSelectedTrack))
                .findFirst()
                .orElse(null))
                .getFile();

    }

    /**
     * Getter for the list of tracks
     *
     * @return a list of all currently existing tracks
     */
    public List<Track> getTracks() {
        return tracks;
    }

    /**
     * Setter for the list of tracks
     *
     * @param tracks a list of tracks to be set
     */
    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }

    /**
     * Getter for the currently selected track id
     *
     * @return the currently selected track id
     */
    public int getSelectedTrackId() {
        return selectedTrackId;
    }

}
