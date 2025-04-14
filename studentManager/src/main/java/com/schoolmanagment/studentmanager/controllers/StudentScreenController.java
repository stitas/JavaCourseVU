package com.schoolmanagment.studentmanager.controllers;

import com.schoolmanagment.studentmanager.data.MasterCourse;
import com.schoolmanagment.studentmanager.data.UndergraduateCourse;
import com.schoolmanagment.studentmanager.managers.StudentManager;
import com.schoolmanagment.studentmanager.student.MasterStudent;
import com.schoolmanagment.studentmanager.student.Student;
import com.schoolmanagment.studentmanager.student.UndergraduateStudent;
import javafx.beans.Observable;
import javafx.beans.binding.ObjectExpression;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class StudentScreenController {
    @FXML
    public TableView<Student> studentTable;
    public TableColumn<Student, Integer> idCol, ageCol;
    public TableColumn<Student, String> firstNameCol, lastNameCol, degreeCol, courseCol;

    @FXML
    private TextField filterField;

    @FXML
    private Button backBtn, addStudentBtn, updateStudentBtn;

    FilteredList<Student> filteredData;

    @FXML
    private void initialize(){
        StudentManager studentManager = StudentManager.getInstance();
        studentManager.addStudent(new UndergraduateStudent("Titas", "Stongvila", 19, UndergraduateCourse.SOFTWARE_SYSTEMS, 1));
        studentManager.addStudent(new MasterStudent("Monstras", "Monstrauskas", 15, MasterCourse.COMPUTER_MODELING, "Robotics"));

        filteredData = studentManager.getStudentsFilteredList();

        studentTable.setItems(filteredData);

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
}
