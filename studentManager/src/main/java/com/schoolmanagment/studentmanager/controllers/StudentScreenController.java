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
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;

import java.io.IOException;

public class StudentScreenController extends TableScreenController{
    @FXML
    public TableColumn<Student, Integer> idCol, ageCol;
    public TableColumn<Student, String> firstNameCol, lastNameCol, degreeCol, courseCol;

    FilteredList<Student> filteredData;

    @FXML
    private void initialize(){
        StudentManager studentManager = StudentManager.getInstance();
        studentManager.addStudent(new UndergraduateStudent("Titas", "Stongvila", 19, UndergraduateCourse.SOFTWARE_SYSTEMS, 1));
        studentManager.addStudent(new MasterStudent("Monstras", "Monstrauskas", 15, MasterCourse.COMPUTER_MODELING, "Robotics"));

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

    private void applyFilter() {
        String text = filterField.getText();

        filteredData.setPredicate(student -> {
            String fullName = student.getFirstName() + " " + student.getLastName();
            return fullName.toLowerCase().startsWith(text.toLowerCase()) ||
                    student.getLastName().toLowerCase().startsWith(text.toLowerCase());
        });
    }

    @Override
    public void onAddBtnClick() throws IOException {
        Stage newStage = new Stage();

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/schoolmanagment/studentmanager/addStudentScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        newStage.setTitle("Add Student");
        newStage.setScene(scene);
        newStage.show();
    }

    @Override
    public void onUpdateBtnClick() throws IOException {

    }
}
