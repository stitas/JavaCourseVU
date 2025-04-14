package com.schoolmanagment.studentmanager.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class IndexControlller {
    @FXML
    private Button studentsBtn, groupsBtn, attendanceBtn;

    @FXML
    protected void onStudentBtnClick() throws IOException {
        Parent studentScene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/schoolmanagment/studentmanager/studentScreen.fxml")));

        // Get current stage
        Stage currentStage = (Stage) studentsBtn.getScene().getWindow();

        currentStage.setScene(new Scene(studentScene));
    }
}
