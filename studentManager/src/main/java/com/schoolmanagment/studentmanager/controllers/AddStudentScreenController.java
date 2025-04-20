package com.schoolmanagment.studentmanager.controllers;

import com.schoolmanagment.studentmanager.data.Course;
import com.schoolmanagment.studentmanager.managers.StudentManager;
import com.schoolmanagment.studentmanager.student.MasterStudent;
import com.schoolmanagment.studentmanager.student.UndergraduateStudent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class AddStudentScreenController extends StudentOperationScreensController {
    @Override
    public void onStudentOperationBtnClick() throws IOException {
        String firstName = getTextInput(firstNameField);
        String lastName = getTextInput(lastNameField);
        int age = getNumberInput(ageField);
        Course course = courseCombo.getSelectionModel().getSelectedItem();

        if(firstName.isEmpty() || lastName.isEmpty() || age == -1 || course == null){
            errorText.setVisible(true);
            return;
        }

        StudentManager studentManager = StudentManager.getInstance();

        if(selectedRadio == undergraduateRadio){
            studentManager.addStudent(new UndergraduateStudent(firstName, lastName, age, course, 1));
        }
        else {
            studentManager.addStudent(new MasterStudent(firstName, lastName, age, course, "-"));
        }

        Parent studentScene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(previousScreen)));

        // Get current stage
        Stage currentStage = (Stage) studentOperationBtn.getScene().getWindow();

        currentStage.setScene(new Scene(studentScene));

    }
}
