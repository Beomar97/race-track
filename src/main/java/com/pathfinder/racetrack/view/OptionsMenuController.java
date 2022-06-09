package com.pathfinder.racetrack.view;

import com.pathfinder.racetrack.model.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.apache.commons.io.FilenameUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class represents the logic of the options menu
 */
public class OptionsMenuController {
    private static final int MAX_PLAYERS = 4;
    private static final String COLOR_ERROR_MESSAGE = "Change Color!";
    private static final String PLAYER_ERROR_MESSAGE = "Fill in at least one name!";
    private static final Color PLAYER_1_SET_COLOR = Color.RED;
    private static final Color PLAYER_2_SET_COLOR = Color.BLUE;
    private static final Color PLAYER_3_SET_COLOR = Color.GREEN;
    private static final Color PLAYER_4_SET_COLOR = Color.YELLOW;
    private static final Color[] FORBIDDEN_COLORS = {Color.WHITE, Color.BLACK, Color.web("#C5C5C5")};
    private static final String GO_KART_MODE_DESCRIPTION = "Continue with minimum velocity and no acceleration until the track is reached again.";
    private static final String FORMULA_ONE_MODE_DESCRIPTION = "Retire from the race.";
    private static final String BOBBY_CAR_MODE_DESCRIPTION = "Position reset to last valid point and velocity will be zero";

    private final ArrayList<Player> players;

    private Boolean checkedColor = false;
    private GameEngine gameEngine;

    private int selectedTrackId;
    private List<Track> tracks;

    @FXML
    private Button btnMainMenu;
    @FXML
    private Button btnStartGame;

    @FXML
    private TextField player1Text;
    @FXML
    private ColorPicker player1Color;
    @FXML
    private Text player1Error;

    @FXML
    private TextField player2Text;
    @FXML
    private ColorPicker player2Color;
    @FXML
    private Text player2Error;

    @FXML
    private TextField player3Text;
    @FXML
    private ColorPicker player3Color;
    @FXML
    private Text player3Error;

    @FXML
    private TextField player4Text;
    @FXML
    private ColorPicker player4Color;
    @FXML
    private Text player4Error;

    @FXML
    private ToggleGroup radioBtnGroup;
    @FXML
    private RadioButton goKartRadioButton;
    @FXML
    private RadioButton formulaOneRadioButton;
    @FXML
    private RadioButton bobbyCarRadioButton;

    @FXML
    private Text trackNameOptions;
    @FXML
    private Button btnBackOptions;
    @FXML
    private ImageView imageTrackLeftOptions;
    @FXML
    private ImageView imageTrackChosenOptions;
    @FXML
    private ImageView imageTrackRightOptions;
    @FXML
    private Button btnForwardOptions;

    /**
     * This constructor creates an option menu object which will be used for
     * handling the setting of the game
     *
     */
    public OptionsMenuController() {
        this.tracks = new ArrayList<>();
        this.players = new ArrayList<>();
    }

    /**
     * Initialize method which the resources and handlers for the game
     */
    @FXML
    public void initialize() {
        initTracks();
        initPlayers();
        initColors();
        initModes();
        setHandlers();
    }

    /**
     * This method gets all existing tracks and preselects a track per default
     */
    public void initTracks() {
        tracks = ExistingTracks.getTracks();
        handleChangeTrack(0);
    }

    /**
     * This method initializes four players
     */
    private void initPlayers() {
        for (int i = 0; i < MAX_PLAYERS; i++) {
            players.add(new Player(i, "", Color.WHITE));
        }
    }

    /**
     * This method sets a default color per player
     */
    private void initColors() {
        player1Color.setValue(PLAYER_1_SET_COLOR);
        player2Color.setValue(PLAYER_2_SET_COLOR);
        player3Color.setValue(PLAYER_3_SET_COLOR);
        player4Color.setValue(PLAYER_4_SET_COLOR);
        handleSaveColor(0, player1Color.getValue());
        handleSaveColor(1, player2Color.getValue());
        handleSaveColor(2, player3Color.getValue());
        handleSaveColor(3, player4Color.getValue());
    }

    /**
     * This method initializes the game modes
     */
    private void initModes() {
        goKartRadioButton.setSelected(true);

        setUpTooltip(goKartRadioButton, 0);
        setUpTooltip(formulaOneRadioButton, 1);
        setUpTooltip(bobbyCarRadioButton, 2);
    }

    /**
     * Setups the tooltip for the radio button of the game mode
     *
     * @param radioButton radio button for which the tooltip should be created
     * @param index       identifier which game mode the radio button is for
     */
    private void setUpTooltip(RadioButton radioButton, int index) {
        Tooltip tooltip;
        ImageView imageView;
        if (index == 0) {
            tooltip = new Tooltip(GO_KART_MODE_DESCRIPTION);
            imageView = new ImageView(new Image("icons/gokart.png"));
        } else if (index == 1) {
            tooltip = new Tooltip(FORMULA_ONE_MODE_DESCRIPTION);
            imageView = new ImageView(new Image("icons/formulaone.png"));
        } else {
            tooltip = new Tooltip(BOBBY_CAR_MODE_DESCRIPTION);
            imageView = new ImageView(new Image("icons/bobbycar.png"));
        }
        tooltip.setShowDelay(Duration.millis(50));
        tooltip.setGraphic(imageView);
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(50);
        radioButton.setTooltip(tooltip);
    }

    /**
     * This method initializes all handlers and the corresponding event
     */
    private void setHandlers() {
        player1Text.focusedProperty().addListener((obs, oldVal, focus) -> handleSaveName(focus, 0, player1Text.getText()));
        player2Text.focusedProperty().addListener((obs, oldVal, focus) -> handleSaveName(focus, 1, player2Text.getText()));
        player3Text.focusedProperty().addListener((obs, oldVal, focus) -> handleSaveName(focus, 2, player3Text.getText()));
        player4Text.focusedProperty().addListener((obs, oldVal, focus) -> handleSaveName(focus, 3, player4Text.getText()));
        player1Color.setOnAction(e -> handleSaveColor(0, player1Color.getValue()));
        player2Color.setOnAction(e -> handleSaveColor(1, player2Color.getValue()));
        player3Color.setOnAction(e -> handleSaveColor(2, player3Color.getValue()));
        player4Color.setOnAction(e -> handleSaveColor(3, player4Color.getValue()));

        btnBackOptions.setOnAction(e -> handleChangeTrack(-1));
        btnForwardOptions.setOnAction(e -> handleChangeTrack(1));

        btnStartGame.setOnAction(e -> handleStartGame());
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
                trackNameOptions.setText("No tracks found!");
                imageTrackChosenOptions.setImage(trackTemplate);
                break;
            case 1:
                selectedTrackId = 0;
                selectedTrackName = tracks.get(selectedTrackId).getName();
                updateTrackName(selectedTrackName);
                updateTrackImage(selectedTrackId, selectedTrackId, selectedTrackId);
                break;
            default:
                selectedTrackId = getValidTracksId(tracks.size(), selectedTrackId);
                int leftTrackId = getValidTracksId(tracks.size(), selectedTrackId - 1);
                int rightTrackId = getValidTracksId(tracks.size(), selectedTrackId + 1);
                selectedTrackName = tracks.get(selectedTrackId).getName();
                updateTrackName(selectedTrackName);
                updateTrackImage(selectedTrackId, leftTrackId, rightTrackId);
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
     * Updates the display of the name for the currently selected track
     *
     * @param nameOfSelectedTrack name of the selected track
     */
    private void updateTrackName(String nameOfSelectedTrack) {
        trackNameOptions.setText(nameOfSelectedTrack);
    }

    /**
     * Updates the display of the preview images for the currently selected track and it's neighbours
     *
     * @param selectedTrackId id of the selected track and it's neighbours
     */
    private void updateTrackImage(int selectedTrackId, int leftTrackId, int rightTrackId) {
        imageTrackChosenOptions.setImage(tracks.get(selectedTrackId).getBitmap());
        imageTrackLeftOptions.setImage(tracks.get(leftTrackId).getBitmap());
        imageTrackRightOptions.setImage(tracks.get(rightTrackId).getBitmap());
    }

    /**
     * Gets the track object for the currently selected track
     *
     * @param nameOfSelectedTrack name of the selected track
     * @return Returns the file object of the currently selected track
     */
    private Track getTrackObjectOfSelectedTrack(String nameOfSelectedTrack) {
        return Objects.requireNonNull(ExistingTracks.getTracks().stream()
                .filter(track -> FilenameUtils.removeExtension(track.getName()).equals(nameOfSelectedTrack))
                .findFirst()
                .orElse(null));
    }

    /**
     * This handler saves a name which is typed in by the user and resets an error if no name is defined on any player
     *
     * @param focus       Needs the focus variable value to check if the field is focused or not
     * @param playerIndex Needs the player index to know the name to be changed on which player
     * @param name        Needs the new name of the player
     */
    protected void handleSaveName(boolean focus, int playerIndex, String name) {
        if (!focus) {
            if (playerIndex < players.size() && playerIndex >= 0) {
                Player player = players.get(playerIndex);
                player.setName(name);
                players.set(playerIndex, player);
                if (player1Error.getText().equals(PLAYER_ERROR_MESSAGE)) {
                    showError(0, "");
                }
            } else {
                throw new IndexOutOfBoundsException("The chosen index is not valid!");
            }
        }

    }

    /**
     * This handler saves the color that the user has chosen for a specific players car
     *
     * @param playerIndex Needs the index of the player of which the cars color should be changed
     * @param color       Needs the new defined color
     */
    protected void handleSaveColor(int playerIndex, Color color) {
        if (playerIndex < players.size() && playerIndex >= 0) {
            Player player = players.get(playerIndex);
            player.getCar().setColor(color);
            players.set(playerIndex, player);
            checkColor(playerIndex);
        } else {
            throw new IndexOutOfBoundsException("The chosen index is not valid!");
        }
    }

    /**
     * This handler checks which game mode is chosen by the user
     */
    protected void handleGameMode() {
        final Toggle selectedToggle = radioBtnGroup.getSelectedToggle();
        if (selectedToggle.equals(goKartRadioButton)) {
            gameEngine = new GoKartEngine(players, getTrackObjectOfSelectedTrack(tracks.get(selectedTrackId).getName()));
        } else if (selectedToggle.equals(formulaOneRadioButton)) {
            gameEngine = new FormulaOneEngine(players, getTrackObjectOfSelectedTrack(tracks.get(selectedTrackId).getName()));
        } else {
            gameEngine = new BobbyCarEngine(players, getTrackObjectOfSelectedTrack(tracks.get(selectedTrackId).getName()));
        }
    }

    /**
     * This handler starts a new game
     */
    protected void handleStartGame() {
        if (Boolean.TRUE.equals(checkedColor) && checkPlayers()) {
            handleGameMode();
            createGame();
        }
    }

    /**
     * This method checks if a name is typed in to a players name field
     *
     * @return Boolean if the check has passed
     */
    public boolean checkPlayers() {
        players.removeIf(player -> player.getName().isEmpty());
        if (players.isEmpty()) {
            initPlayers();
            initColors();
            showError(0, PLAYER_ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    /**
     * This method checks if the same color for a car is chosen twice
     *
     * @param playerIndex Needs an index of a player of which the color should be checked
     */
    protected void checkColor(int playerIndex) {
        Color checkPlayerColor = players.get(playerIndex).getCar().getColor();
        boolean isSpecifiedColorValid = true;
        int j = 0;
        while (j < players.size() && isSpecifiedColorValid) {
            Color proofPlayerColor = players.get(j).getCar().getColor();
            if (j != playerIndex && checkPlayerColor.equals(proofPlayerColor)) {
                isSpecifiedColorValid = false;
            }
            j++;
        }

        for (Color forbiddenColor : FORBIDDEN_COLORS) {
            if (checkPlayerColor.equals(forbiddenColor)) {
                isSpecifiedColorValid = false;
                break;
            }
        }

        if (isSpecifiedColorValid) {
            checkedColor = true;
            showError(playerIndex, "");
        } else {
            checkedColor = false;
            showError(playerIndex, COLOR_ERROR_MESSAGE);
        }
    }

    /**
     * This method shows an error on the corresponding players error field
     *
     * @param playerIndex  Needs a player index of which an error should be displayed
     * @param errorMessage Needs an error message which will be shown on the corresponding player
     */
    protected void showError(int playerIndex, String errorMessage) {
        if (playerIndex == 0) {
            player1Error.setText(errorMessage);
        } else if (playerIndex == 1) {
            player2Error.setText(errorMessage);
        } else if (playerIndex == 2) {
            player3Error.setText(errorMessage);
        } else {
            player4Error.setText(errorMessage);
        }
    }

    /**
     * This method creates a new playing field and switches the scene to the playing field scene
     */
    protected void createGame() {
        SceneController.initPlayingFieldScene(gameEngine);
        SceneController.setPlayingFieldScene();
    }

    /**
     * Get tracks
     * @return list of tracks
     */
    protected List<Track> getTracks() {
        return tracks;
    }

    /**
     * Get players
     * @return list of playyers
     */
    protected ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * Get the state of the checked color
     * @return if the color is valid
     */
    protected Boolean getCheckedColor() {
        return checkedColor;
    }

    /**
     * Get the error message for player 1
     * @return error message
     */
    protected Text getPlayer1Error() {
        return player1Error;
    }

    /**
     * Get the error message for player 2
     * @return error message
     */
    protected Text getPlayer2Error() {
        return player2Error;
    }

    /**
     * Get the error message for player 3
     * @return error message
     */
    protected Text getPlayer3Error() {
        return player3Error;
    }

    /**
     * Get the error message for player 4
     * @return error message
     */
    protected Text getPlayer4Error() {
        return player4Error;
    }

    /**
     * Get radio button group
     * @return radio button group
     */
    protected ToggleGroup getRadioBtnGroup() {
        return radioBtnGroup;
    }

    /**
     * Get the radio button for go kart game engine
     * @return radio button for go kart game engine
     */
    protected RadioButton getGoKartRadioButton() {
        return goKartRadioButton;
    }

    /**
     * Get the radio button for formula one game engine
     * @return radio button for formula one game engine
     */
    protected RadioButton getFormulaOneRadioButton() {
        return formulaOneRadioButton;
    }

    /**
     * Get the radio button for bobby car game engine
     * @return radio button for bobby car game engine
     */
    protected RadioButton getBobbyCarRadioButton() {
        return bobbyCarRadioButton;
    }
}