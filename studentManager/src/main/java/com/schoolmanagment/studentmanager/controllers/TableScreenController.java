package com.schoolmanagment.studentmanager.controllers;

import com.schoolmanagment.studentmanager.student.Student;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public abstract class TableScreenController {
    @FXML
    protected TableView<Student> table;

    @FXML
    protected TextField filterField;

    @FXML
    protected Button backBtn, addBtn, updateBtn;

    protected FilteredList<Student> filteredData;

    /** Go back to index screen on click **/
    public void onBackBtnClick() throws IOException {
        Parent studentScene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/schoolmanagment/studentmanager/index.fxml")));

        // Get current stage
        Stage currentStage = (Stage) backBtn.getScene().getWindow();

        currentStage.setScene(new Scene(studentScene));
    }

    public abstract void onAddBtnClick() throws IOException;
    public abstract void onUpdateBtnClick() throws IOException;
}
