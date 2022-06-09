package com.pathfinder.racetrack.view;

import com.google.gson.Gson;
import com.pathfinder.racetrack.model.GameEngine;
import com.pathfinder.racetrack.model.RTSaveFile;
import com.pathfinder.racetrack.model.exceptions.NotAValidTrackException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

/**
 * This class represents the logic of the start menu
 */
public class StartMenuController {
    @FXML
    private Button newGame;
    @FXML
    private Button loadGame;
    @FXML
    private Button tracks;
    @FXML
    private Button rules;
    @FXML
    private Button quitGame;
    @FXML
    private Button credits;
    @FXML
    private Button viewHighScore;

    /**
     * Initialize method which sets the handlers
     */
    @FXML
    public void initialize() {
        setHandlers();
    }

    /**
     * Sets handlers on events by pushing buttons
     */
    private void setHandlers() {
        newGame.setOnAction(e -> handleNewGame());
        loadGame.setOnAction(e -> handleLoadGame());
        tracks.setOnAction(e -> handleTracks());
        rules.setOnAction(e -> handleRules());
        quitGame.setOnAction(e -> handleQuitGame());
        credits.setOnAction(e -> handleCredits());
        viewHighScore.setOnAction(e -> handleHighScore());
    }

    /**
     * Handler to switch to the options menu scene
     */
    public void handleNewGame() {
        SceneController.setOptionsMenuScene();
    }

    /**
     * Handler to load a saved game
     */
    public void handleLoadGame() {
        File selectedFile = RTSaveFileChooser.showLoadWindow();

        if (null != selectedFile) {
            Gson gson = new Gson();
            GameEngine gameEngine;

            try (Reader reader = new FileReader(selectedFile)) {
                RTSaveFile rtSaveFile = gson.fromJson(reader, RTSaveFile.class);
                rtSaveFile.fixGSONColorTypeLoss();
                gameEngine = rtSaveFile.getGameEngine();
                SceneController.initPlayingFieldScene(gameEngine);
                SceneController.setPlayingFieldScene();
            } catch (IOException e) {
                ExceptionDialog.showDialog("The track is not installed on your system.", e);
            } catch (NullPointerException e) {
                ExceptionDialog.showDialog("Can not load game. The game mode is not available on your system.", e);
            } catch (NotAValidTrackException e) {
                ExceptionDialog.showDialog("The image of your track is not valid.", e);
            }
        }
    }

    /**
     * Handler to switch to the tracks menu scene
     */
    public void handleTracks() {
        SceneController.setTracksMenuScene();
    }

    /**
     * Handler to show the game rules
     */
    public void handleRules() {
        SceneController.setRulesScene();
    }

    /**
     * Handler to quit the game
     */
    public void handleQuitGame() {
        Platform.exit();
        System.exit(0);
    }

    /**
     * Handler to show credits
     */
    public void handleCredits() {
        SceneController.setCreditsScene();
    }

    /**
     * Handler to switch to the high score board scene
     */
    public void handleHighScore() {
        SceneController.setHighScoreScene();
    }

}