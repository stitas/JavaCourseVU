package com.schoolmanagment.studentmanager.controllers;

import com.schoolmanagment.studentmanager.Main;
import com.schoolmanagment.studentmanager.data.MasterCourse;
import com.schoolmanagment.studentmanager.data.UndergraduateCourse;
import com.schoolmanagment.studentmanager.managers.StudentManager;
import com.schoolmanagment.studentmanager.student.MasterStudent;
import com.schoolmanagment.studentmanager.student.Student;
import com.schoolmanagment.studentmanager.student.UndergraduateStudent;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class StudentScreenController extends TableScreenController{

    @FXML
    protected TableView<Student> table;
    public TableColumn<Student, Integer> idCol, ageCol;
    public TableColumn<Student, String> firstNameCol, lastNameCol, degreeCol, courseCol;

    FilteredList<Student> filteredData;

    @FXML
    private void initialize(){
        pushPreviousScreen();
        StudentManager studentManager = StudentManager.getInstance();

        filteredData = studentManager.getStudentsFilteredList();

        table.setItems(filteredData);

        idCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        ageCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getAge()).asObject());
        firstNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFirstName()));
        lastNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLastName()));
        degreeCol.setCellValueFactory(cellData -> new SimpleStringProperty(getDegreeString(cellData.getValue())));
        courseCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCourse().getName()));

        filterField.textProperty().addListener(e -> applyFilter());
    }

    private String getDegreeString(Student student){
        if(student instanceof UndergraduateStudent){
            return "Undergraduate";
        }
        else if (student instanceof MasterStudent){
            return "Masters";
        }
        else {
            return "-";
        }
    }

    @Override
    protected void applyFilter() {
        String text = filterField.getText();

        filteredData.setPredicate(student -> {
            String fullName = student.getFirstName() + " " + student.getLastName();
            return fullName.toLowerCase().startsWith(text.toLowerCase()) ||
                    student.getLastName().toLowerCase().startsWith(text.toLowerCase());
        });
    }

    @Override
    public void onAddBtnClick() throws IOException {
        Parent studentScene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/schoolmanagment/studentmanager/addStudentScreen.fxml")));

        // Get current stage
        Stage currentStage = (Stage) addBtn.getScene().getWindow();

        currentStage.setScene(new Scene(studentScene));
    }

    @Override
    public void onUpdateBtnClick() throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/schoolmanagment/studentmanager/updateStudentScreen.fxml"));
            Parent studentScene = loader.load();

            UpdateStudentScreenController controller = loader.getController();
            controller.initData(table.getSelectionModel().getSelectedItem());

            // Get current stage
            Stage currentStage = (Stage) addBtn.getScene().getWindow();

            currentStage.setScene(new Scene(studentScene));
        }
        // Do nothing if user has not selected student from the table
        catch (NullPointerException e) {
            return;
        }
    }

    @Override
    protected void pushPreviousScreen() {
        previousScreen = "/com/schoolmanagment/studentmanager/index.fxml";
    }
}
