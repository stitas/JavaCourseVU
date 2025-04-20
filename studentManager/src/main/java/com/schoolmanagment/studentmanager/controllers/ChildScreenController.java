package com.schoolmanagment.studentmanager.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public abstract class ChildScreenController {
    @FXML
    protected Button backBtn;

    String previousScreen;

    public ChildScreenController(String previousScreen){
        this.previousScreen = previousScreen;
    }

    /** Go back to index screen on click **/
    public void onBackBtnClick() throws IOException {
        Parent studentScene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(previousScreen)));

        // Get current stage
        Stage currentStage = (Stage) backBtn.getScene().getWindow();

        currentStage.setScene(new Scene(studentScene));
    }
}
