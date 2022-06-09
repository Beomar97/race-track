package com.pathfinder.racetrack.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * Controller of the rules fxml-view
 */
public class RulesController {

    @FXML
    private Button btnMainMenu;

    /**
     * Initialize method which sets up the handlers
     */
    @FXML
    public void initialize() {
        setHandlers();
    }

    /**
     * Sets handlers on events from the buttons
     */
    private void setHandlers() {
        btnMainMenu.setOnAction(e -> SceneController.setStartMenuScene());
    }
}
