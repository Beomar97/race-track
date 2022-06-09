package com.pathfinder.racetrack.view;

import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

public class ExceptionDialogTest extends ApplicationTest {

    @Override
    public void start(Stage stage) {
        ExceptionDialog.showDialog("Test", new IllegalStateException());
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowException() {
        new ExceptionDialog();
    }

    @Test
    public void showDialog() {
        press(KeyCode.ENTER);
    }
}