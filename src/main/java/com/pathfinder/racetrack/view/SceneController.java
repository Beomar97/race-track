package com.pathfinder.racetrack.view;

import com.pathfinder.racetrack.model.GameEngine;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * This class controls the different scenes of the UI (User Interface)
 */
public class SceneController {
    private static final Logger logger = LoggerFactory.getLogger(SceneController.class);
    private static Stage stage;

    private static Scene startMenuScene;
    private static Scene optionsMenuScene;
    private static Scene highScoreScene;
    private static Scene playingFieldScene;
    private static Scene tracksMenuScene;
    private static Scene rulesScene;
    private static Scene creditsScene;

    private static OptionsMenuController optionsMenuController;
    private static HighScoreController highScoreController;
    private static TracksMenuController tracksMenuController;

    protected SceneController() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Sets the JavaFX main stage of the application
     *
     * @param stage Sets the main stage of the application
     */
    public static void setStage(Stage stage) {
        SceneController.stage = stage;
    }

    /**
     * Gets the JavaFX main stage of the application
     *
     * @return Returns the main stage of the application
     */
    public static Stage getStage() {
        return stage;
    }

    /**
     * Sets the JavaFX main stage of the application
     * @param title Title of the scene
     */
    public static void setTitle(String title) {
        SceneController.stage.setTitle(title);
    }

    /**
     * Adds action handlers as events to the game for closing the application without an exception
     */
    public static void setHandlers() {
        stage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });
    }

    /**
     * Initializes a scene with a predefined fxml file
     *
     * @param fxmlPath   FXML path in the resources directory
     * @param controller Controller which controls the logic of the scene
     * @return Returns a new created scene with a controller
     */
    protected static Scene initScene(String fxmlPath, Object controller) {
        FXMLLoader fxmlLoader = new FXMLLoader(SceneController.class.getResource(fxmlPath));
        fxmlLoader.setController(controller);
        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            ExceptionDialog.showDialog("Couldn't complete action! Error occurred during fxml load...", e);
            logger.error("Error occurred during import of fxml file '{}'!", fxmlPath, e);
        }
        return new Scene(root);
    }

    /**
     * Initializes the start menu scene
     *
     */
    public static void initStartMenuScene() {
        startMenuScene = initScene("/fxml/StartMenu.fxml", new StartMenuController());
    }

    /**
     * Initializes the options menu scene
     *
     */
    public static void initOptionsMenuScene() {
        optionsMenuController = new OptionsMenuController();
        optionsMenuScene = initScene("/fxml/OptionsMenu.fxml", optionsMenuController);
    }

    /**
     * Initializes the high score scene
     *
     */
    public static void initHighScoreScene() {
        highScoreController = new HighScoreController();
        highScoreScene = initScene("/fxml/HighScore.fxml", highScoreController);
    }

    /**
     * Initializes the playing field scene
     *
     * @param gameEngine Needs a created gameEngine to initialize the field
     */
    public static void initPlayingFieldScene(GameEngine gameEngine) {
        playingFieldScene = initScene("/fxml/PlayingField.fxml", new PlayingFieldController(gameEngine));
    }

    /**
     * Initializes the tracks menu scene
     */
    public static void initTracksMenuScene() {
        tracksMenuController = new TracksMenuController();
        tracksMenuScene = initScene("/fxml/TracksMenu.fxml", tracksMenuController);
    }

    /**
     * Initializes the rules scene
     */
    public static void initRulesScene() {
        rulesScene = initScene("/fxml/Rules.fxml", new RulesController());
    }

    /**
     * Initializes the credits scene
     */
    public static void initCreditsScene() {
        creditsScene = initScene("/fxml/Credits.fxml", new CreditsController());
    }

    /**
     * Sets a scene and shows it
     *
     * @param scene Needs a scene witch will be shown on calling the method
     */
    private static void setScene(Scene scene) {
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Sets the start menu scene
     */
    public static void setStartMenuScene() {
        setScene(startMenuScene);
    }

    /**
     * Sets the options menu scene
     */
    public static void setOptionsMenuScene() {
        initOptionsMenuScene();
        optionsMenuController.initTracks();
        setScene(optionsMenuScene);
    }

    /**
     * Sets the high score scene
     */
    public static void setHighScoreScene() {
        setScene(highScoreScene);
    }

    /**
     * Sets the high score scene
     * @param trackName Name of the track
     * @param gameMode Name of the game mode
     */
    public static void setHighScoreSceneWithGameEngineAndMode(String trackName, String gameMode) {
        highScoreController.showTrackByNameAndMode(trackName, gameMode);
        setScene(highScoreScene);
    }

    /**
     * Sets the playing field scene
     */
    public static void setPlayingFieldScene() {
        setScene(playingFieldScene);
    }

    /**
     * Sets the tracks menu scene
     */
    public static void setTracksMenuScene() {
        setScene(tracksMenuScene);
    }

    /**
     * Sets the rules scene
     */
    public static void setRulesScene() {
        setScene(rulesScene);
    }

    /**
     * Sets the credits scene
     */
    public static void setCreditsScene() {
        setScene(creditsScene);
    }


    /**
     * Get start menu scene
     * @return start menu scene
     */
    protected static Scene getStartMenuScene() {
        return startMenuScene;
    }

    /**
     * Get options menu scene
     * @return options menu scene
     */
    protected static Scene getOptionsMenuScene() {
        return optionsMenuScene;
    }

    /**
     * Get tracks menu scene
     * @return tracks menu scene
     */
    protected static Scene getTracksMenuScene() {
        return tracksMenuScene;
    }

    /**
     * Get high score scene
     * @return high score scene
     */
    protected static Scene getHighScoreScene() {
        return highScoreScene;
    }

    /**
     * Get get rules scene
     * @return rules scene
     */
    protected static Scene getRulesScene() {
        return rulesScene;
    }

    /**
     * Get credits scene
     * @return credits scene
     */
    protected static Scene getCreditsScene() {
        return creditsScene;
    }

    /**
     * Get playing field scene
     * @return playing field sScene
     */
    protected static Scene getPlayingFieldScene() {
        return playingFieldScene;
    }

    /**
     * Get options menu controller
     * @return options menu controller
     */
    protected static OptionsMenuController getOptionsMenuController() {
        return optionsMenuController;
    }

    /**
     * Get high score scene controller
     * @return high score scene controller
     */
    protected static HighScoreController getHighScoreSceneController() {
        return highScoreController;
    }

    /**
     * Get tracks menu scene controller
     * @return tracks menu scene controller
     */
    protected static TracksMenuController getTracksMenuSceneController() {
        return tracksMenuController;
    }
}
