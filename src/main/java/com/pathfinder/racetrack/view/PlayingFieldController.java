package com.pathfinder.racetrack.view;

import com.pathfinder.racetrack.model.Car;
import com.pathfinder.racetrack.model.Coordinate;
import com.pathfinder.racetrack.model.GameEngine;
import com.pathfinder.racetrack.model.Player;
import com.pathfinder.racetrack.model.exceptions.AllPossibleTurnsOutOfCanvasException;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.File;
import java.util.List;

/**
 * Controller of the actual game
 */
public class PlayingFieldController {
    private static final int INDEX_AFTER_LOGO = 1;
    private final GameEngine gameEngine;
    private List<int[]> suggestionPixels;
    private VBox playerList;
    @FXML
    private VBox vbox;
    @FXML
    private HBox hbox;
    @FXML
    private Canvas map;
    @FXML
    private Canvas mapSuggestions;
    @FXML
    private Canvas mapRoute;
    @FXML
    private ImageView mapTrack;
    @FXML
    private TextArea textOutput;
    @FXML
    private Button btnReset;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnMainMenu;

    public PlayingFieldController(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
    }

    /**
     * This method gets called when javafx loads the fxml
     */
    @FXML
    public void initialize() throws AllPossibleTurnsOutOfCanvasException {
        textOutput.appendText("Welcome to RaceTrack! - BETA, INTERNAL USE ONLY\n");
        showPlayerName();
        MapPainter.drawGrid(map);
        MapPainter.drawTrack(gameEngine.getTrack(), mapTrack);
        suggestionPixels = MapPainter.drawSuggestion(mapSuggestions, gameEngine.getCurrentCar());
        initCars();
        initPlayerIcons();
        setHandlers();
        Jukebox.playFanfare();
    }

    private void initPlayerIcons() {
        List<Player> players = gameEngine.getPlayers();
        playerList = new VBox();
        playerList.setPadding(new Insets(9, 9, 9, 9));
        playerList.setSpacing(9);

        for (Player player : players) {
            Image playerIcon = new Image(getClass().getResourceAsStream("/icons/user.png"));

            ImageView imageView = new ImageView(playerIcon);
            imageView.setFitHeight(15);
            imageView.setPreserveRatio(true);

            StackPane imageContainer = new StackPane();
            imageContainer.setPadding(new Insets(9, 9, 9, 9));
            imageContainer.getChildren().add(imageView);

            Label playerName = new Label(player.getName());
            playerName.setStyle("-fx-font-weight: bold");
            playerName.setId(player.getName() + player.getId());
            playerName.setPadding(new Insets(9, 18, 9, 9));
            playerName.setGraphic(imageContainer);

            playerList.getChildren().add(playerName);
        }

        vbox.getChildren().add(INDEX_AFTER_LOGO, playerList);
        updatePlayerListView();
    }

    /**
     * Draws all cars on to the map
     */
    private void initCars() {
        for (Car car : gameEngine.getAllCars()) {
            Coordinate previousCoord = null;
            for (Coordinate coordinate : car.getRoute()) {
                MapPainter.drawCar(map, coordinate, car.getColor());
                if (previousCoord != null) {
                    MapPainter.drawMove(mapRoute, car.getColor(), previousCoord, coordinate);
                }
                previousCoord = coordinate;
            }
        }
    }

    /**
     * Adds onAction handel to buttons and EventFilter to the VBox so that it can be played
     * with the keyboard
     */
    private void setHandlers() {
        btnMainMenu.setOnAction(e -> SceneController.setStartMenuScene());

        hbox.addEventHandler(KeyEvent.KEY_PRESSED, ke -> {
            String input = ke.getText();
            char singleInput = input.charAt(0);
            if (Character.isDigit(singleInput)) {
                handlePlayerAction(Character.getNumericValue(singleInput) - 1);
            }
        });


        map.setOnMouseClicked(e -> handleClickCanvasEvent(e.getX(), e.getY()));

        btnSave.setOnAction(e -> handleClickSaveGame());

        btnReset.setOnAction(e -> handleClickResetGame());
    }

    /**
     * Handles the reset action
     */
    protected void handleClickResetGame() {
        gameEngine.resetGame();
        MapPainter.clearCanvas(map);
        MapPainter.clearCanvas(mapSuggestions);
        MapPainter.clearCanvas(mapRoute);
        MapPainter.drawGrid(map);
        MapPainter.drawTrack(gameEngine.getTrack(), mapTrack);
        initCars();
        prepareNextPlayerMove(gameEngine.getCurrentCar());
    }

    /**
     * Handles the save game action
     */
    protected void handleClickSaveGame() {
        File selectedFile = RTSaveFileChooser.showSaveWindow();

        if (null != selectedFile) {
            gameEngine.saveGame(selectedFile);
        } else {
            ExceptionDialog.showDialog("Please select a valid directory", new IllegalArgumentException());
        }
    }

    /**
     * Maps the mouse input to a button/suggestion the user selected
     *
     * @param x x-axis on the canvass
     * @param y y-axis on the canvas
     */
    protected void handleClickCanvasEvent(double x, double y) {
        int buttonRadius = (int) MapPainter.getSuggestionButtonRadius();

        for (int i = 0; i < suggestionPixels.size(); i++) {
            int xButton = suggestionPixels.get(i)[0];
            int yButton = suggestionPixels.get(i)[1];

            boolean pointXIsInButton = xButton - buttonRadius < x && x < xButton + buttonRadius;
            boolean pointYIsInButton = yButton - buttonRadius < y && y < yButton + buttonRadius;

            if (pointXIsInButton && pointYIsInButton) {
                handlePlayerAction(i);
                break;
            }
        }
    }

    /**
     * Passes the move the player made to the controller and updates the GUI
     *
     * @param btnArrayIndex Button number the player pressed
     */
    public void handlePlayerAction(int btnArrayIndex) {
        if (!gameEngine.getGameEnded()) {
            Player playerCurrent = gameEngine.getCurrentPlayer();
            Car carCurrent = gameEngine.getCurrentCar();
            Car carNext = gameEngine.getNextCar();

            gameEngine.doGameTurn(btnArrayIndex);

            drawPlayerMove(carCurrent);
            printPlayerText(carCurrent, playerCurrent);

            if (!gameEngine.getGameEnded()) {
                prepareNextPlayerMove(carNext);
            } else {
                textOutput.appendText("Should show high score now\n");
                gameEngine.saveHighScore();
                SceneController.setHighScoreSceneWithGameEngineAndMode(gameEngine.getTrack().getName(),gameEngine.getClass().getSimpleName());
            }
        } else {
            textOutput.appendText("game done\n");
            gameEngine.saveHighScore();
            SceneController.setHighScoreSceneWithGameEngineAndMode(gameEngine.getTrack().getName(),gameEngine.getClass().getSimpleName());
        }
    }

    /**
     * Generates text output to the corresponding status of the player and car
     *
     * @param carCurrent    For which car the text should be printed
     * @param playerCurrent For whom the text should be printed
     */
    private void printPlayerText(Car carCurrent, Player playerCurrent) {
        if (carCurrent.hasCrashed()) {
            Jukebox.playCrash();
            textOutput.appendText("Oh no! You crashed 😱\n");
        } else {
            Jukebox.playJump();
        }

        if (carCurrent.isOffTrack()) {
            textOutput.appendText("You left the track! 🛤\n");
        }

        if (playerCurrent.getRetired()) {
            textOutput.appendText("Game over for you! 🤦‍\n");
        }
    }

    /**
     * Draws the delta of a move to the map
     *
     * @param carCurrent The car to be drawn and updated
     */
    private void drawPlayerMove(Car carCurrent) {
        MapPainter.drawMove(mapRoute, carCurrent.getColor(), carCurrent.getLastPosition(), carCurrent.getPosition());
        MapPainter.drawCar(map, carCurrent);
    }

    /**
     * Prepares the view for the next player
     *
     * @param carNext The car to be drawn and updated
     */
    private void prepareNextPlayerMove(Car carNext) {
        showPlayerName();
        MapPainter.clearCanvas(mapSuggestions);
        try {
            suggestionPixels = MapPainter.drawSuggestion(mapSuggestions, carNext);
        } catch (AllPossibleTurnsOutOfCanvasException e) {
            gameEngine.retireCurrentPlayer();
            gameEngine.getCurrentCar().setCrashed(true);
            if (!gameEngine.getGameEnded() && gameEngine.getNumberOfActivePlayers() > 0) {
                handlePlayerAction(5);
                gameEngine.setNextPlayer();
            } else {
                //Completely over
                gameEngine.setGameEnded(true);
                textOutput.appendText("game done, last player left canvas\n");
                gameEngine.saveHighScore();
                SceneController.setHighScoreSceneWithGameEngineAndMode(gameEngine.getTrack().getName(),gameEngine.getClass().getSimpleName());
            }
        }
        updatePlayerListView();
    }

    private void updatePlayerListView() {
        Player player = gameEngine.getCurrentPlayer();
        String nextPlayerLabelId = player.getName() + player.getId();
        Car car = player.getCar();
        Color newColor = car.getColor().brighter().brighter().desaturate().desaturate();
        String playerCSSColor = newColor.toString().substring(2);

        for (Node node : playerList.getChildren()) {
            if (node.getId().equals(nextPlayerLabelId)) {
                node.setStyle("-fx-background-color: #" + playerCSSColor);
            } else {
                node.setStyle("-fx-background-color: transparent;");
            }
        }
    }

    /**
     * Prints the player Name
     */
    private void showPlayerName() {
        String playerName = gameEngine.getCurrentPlayer().getName();
        textOutput.appendText("\n--- " + playerName + ": It's your turn ---\n");
    }
}
