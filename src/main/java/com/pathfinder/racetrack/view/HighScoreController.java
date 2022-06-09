package com.pathfinder.racetrack.view;


import com.pathfinder.racetrack.model.ExistingTracks;
import com.pathfinder.racetrack.model.Player;
import com.pathfinder.racetrack.model.RTSaveFile;
import com.pathfinder.racetrack.model.Track;
import com.pathfinder.racetrack.util.AlphaComparer;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Controller of the high score fxml-view
 */
public class HighScoreController {
    private static final Path DEFAULT_HIGH_SCORE_DIRECTORY = Paths.get(System.getProperty("user.home"), "RaceTrack", "high_score");
    private static final String DEFAULT_GAME_MODE = "GoKartEngine";
    private static final String DEFAULT_EMPTY_HIGH_SCORE_MSG = "No high score available for this mode and track!";
    private final ObservableList<Player> sortedPlayerList;
    private int chosenTrackId;

    @FXML
    private Text textTrackName;
    @FXML
    private Button btnBack;
    @FXML
    private ImageView imageTrackLeft;
    @FXML
    private ImageView imageTrackChosen;
    @FXML
    private ImageView imageTrackRight;
    @FXML
    private Button btnForward;

    @FXML
    private TableView<Player> tblViewHighScore;
    @FXML
    private TableColumn<Player, Integer> tblClmRank;
    @FXML
    private TableColumn<Player, String> tblClmName;
    @FXML
    private TableColumn<Player, Integer> tblClmNbrMoves;

    @FXML
    private Button btnBobbyCar;
    @FXML
    private Button btnFormulaOne;
    @FXML
    private Button btnGoKart;


    @FXML
    private Button btnMainMenu;

    /**
     * Creates a class to control the high score scene
     */
    public HighScoreController() {
        this.sortedPlayerList = FXCollections.observableArrayList();
    }

    /**
     * Initializes the default scene
     */
    @FXML
    public void initialize() {
        setHandlers();
        initTableView();
        handleChangeTrack(0);
    }

    /**
     * Sets all handlers on user input actions
     */
    private void setHandlers() {

        btnBack.setOnAction(e -> handleChangeTrack(-1));
        btnForward.setOnAction(e -> handleChangeTrack(1));

        btnBobbyCar.setOnAction(e -> handleReadTrackHighScore("BobbyCarEngine"));
        btnFormulaOne.setOnAction(e -> handleReadTrackHighScore("FormulaOneEngine"));
        btnGoKart.setOnAction(e -> handleReadTrackHighScore(DEFAULT_GAME_MODE));

        btnMainMenu.setOnAction(e -> SceneController.setStartMenuScene());
    }

    /**
     * Handler to change the track UI
     * @param direction direction in which the direction of the cover flow should go
     */
    public void handleChangeTrack(int direction) {
        int leftTrackId;
        int rightTrackId;
        List<Track> tracks = ExistingTracks.getTracks();
        int tracksSize = tracks.size();

        chosenTrackId += direction;

        switch (tracksSize) {
            case 0:
                Image noTrack = new Image(getClass().getResource("/tracks/Track_Template.png").toExternalForm());
                imageTrackChosen.setImage(noTrack);
                textTrackName.setText("No tracks found!");
                break;
            case 1:
                chosenTrackId = 0;
                imageTrackChosen.setImage(tracks.get(chosenTrackId).getBitmap());
                textTrackName.setText(tracks.get(chosenTrackId).getName());
                handleReadTrackHighScore(DEFAULT_GAME_MODE);
                break;
            default:
                chosenTrackId = getValidTracksId(tracksSize, chosenTrackId);
                leftTrackId = getValidTracksId(tracksSize, chosenTrackId - 1);
                rightTrackId = getValidTracksId(tracksSize, chosenTrackId + 1);
                imageTrackLeft.setImage(tracks.get(leftTrackId).getBitmap());
                imageTrackChosen.setImage(tracks.get(chosenTrackId).getBitmap());
                textTrackName.setText(tracks.get(chosenTrackId).getName());
                handleReadTrackHighScore(DEFAULT_GAME_MODE);
                imageTrackRight.setImage(tracks.get(rightTrackId).getBitmap());
        }
    }

    /**
     * Looks if the track id is valid
     * @param trackMax maximum track number
     * @param chosenId chosen track id
     * @return a valid track Id
     */
    private int getValidTracksId(int trackMax, int chosenId) {
        if (chosenId < 0) {
            return trackMax - 1;
        } else if (chosenId == trackMax) {
            return 0;
        }
        return chosenId;
    }

    /**
     * Sets the handler which reads a CSV file with help from the RTSaveFile class
     * @param gameMode game mode which will be read from a given track
     */
    private void handleReadTrackHighScore(String gameMode) {
        Track track = ExistingTracks.getTracks().get(chosenTrackId);
        Path trackHighScorePath = DEFAULT_HIGH_SCORE_DIRECTORY.resolve(track.getName());
        File trackHighScoreDir = trackHighScorePath.toFile();

        if (trackHighScoreDir.exists()) {
            Path trackHighScoreFilePath = DEFAULT_HIGH_SCORE_DIRECTORY.resolve(track.getName()).resolve(gameMode + ".csv");
            File trackHighScoreFile = trackHighScoreFilePath.toFile();
            if (trackHighScoreFile.exists()) {
                RTSaveFile readFile = new RTSaveFile(gameMode, track.getName());
                readFile.readHighScore();
                setSortedPlayerList(readFile.getPlayers());
            } else {
                sortedPlayerList.removeAll();
                tblViewHighScore.getItems().clear();
            }
        } else {
            sortedPlayerList.removeAll();
            tblViewHighScore.getItems().clear();
        }
    }

    /**
     * Initializes the high score table
     */
    private void initTableView() {
        sortedPlayerList.addListener((ListChangeListener<Player>) c -> tblViewHighScore.setItems(sortedPlayerList));
        tblClmRank.setCellValueFactory(new PropertyValueFactory<>("rank"));
        tblClmName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tblClmNbrMoves.setCellValueFactory(new PropertyValueFactory<>("nbrOfMoves"));
        tblViewHighScore.setItems(sortedPlayerList);
        tblViewHighScore.setPlaceholder(new Label(DEFAULT_EMPTY_HIGH_SCORE_MSG));
    }

    /**
     * Sets a player list which will be sorted
     * @param players players to be set and sorted
     */
    private void setSortedPlayerList(List<Player> players) {
        sortedPlayerList.removeAll();
        tblViewHighScore.getItems().clear();
        for (Player player : players) {
            sortedPlayerList.add(player);
        }

        AlphaComparer compPlayer = new AlphaComparer();
        sortedPlayerList.sort(compPlayer);

        int rank = 1;
        for (Player player : sortedPlayerList) {
            player.setRank(rank);
            rank++;
        }
    }

    /**
     * Sets the UI to a certain track with a certain mode
     * @param trackName The name of the track that is used
     * @param gameMode The name of the game mode that is user
     */
    public void showTrackByNameAndMode(String trackName, String gameMode) {
        List<Track> tracks = ExistingTracks.getTracks();
        int index = 0;
        while (!tracks.get(index).getName().equals(trackName)) {
            index++;
        }
        chosenTrackId = index;
        handleChangeTrack(0);
        handleReadTrackHighScore(gameMode);
    }

    /**
     * Getter to return the sorted player list
     * @return sortedPlayerList Returns a list of players sorted on the amount
     * of movements on the track
     */
    public ObservableList<Player> getSortedPlayerList() {
        return sortedPlayerList;
    }

    /**
     * Getter to return the chosen Track from the UI.
     * @return chosenTrackId Returns the id of the chosen track
     */
    public int getChosenTrackId() {
        return chosenTrackId;
    }
}