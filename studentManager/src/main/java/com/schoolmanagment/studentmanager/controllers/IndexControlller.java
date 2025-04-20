package com.schoolmanagment.studentmanager.controllers;

import com.schoolmanagment.studentmanager.helpers.StudentDataCSVManager;
import com.schoolmanagment.studentmanager.managers.StudentManager;
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

    @FXML
    protected void onGroupsBtnClick() throws IOException {
        Parent groupsScene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/schoolmanagment/studentmanager/groupsScreen.fxml")));

        // Get current stage
        Stage currentStage = (Stage) groupsBtn.getScene().getWindow();

        currentStage.setScene(new Scene(groupsScene));
    }

    @FXML
    protected void onAttendanceBtnClick() throws IOException {
        Parent groupsScene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/schoolmanagment/studentmanager/attendanceScreen.fxml")));

        // Get current stage
        Stage currentStage = (Stage) groupsBtn.getScene().getWindow();

        currentStage.setScene(new Scene(groupsScene));
    }

    @FXML
    protected void onImportCSVBtnClick() {
        StudentDataCSVManager.importCSV();
    }

    @FXML
    protected void onExportCSVBtnClick() {
        StudentManager studentManager = StudentManager.getInstance();

        try {
            StudentDataCSVManager.exportCSV(studentManager.getStudents());
        }
        catch (IOException e){
            System.out.println("Failed to export CSV IOEXCEPTION");
        }
    }
}
