package com.schoolmanagment.studentmanager.controllers;

import com.schoolmanagment.studentmanager.data.Course;
import com.schoolmanagment.studentmanager.managers.StudentManager;
import com.schoolmanagment.studentmanager.student.MasterStudent;
import com.schoolmanagment.studentmanager.student.Student;
import com.schoolmanagment.studentmanager.student.UndergraduateStudent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class UpdateStudentScreenController extends StudentOperationScreensController {
    private Student student;
    private boolean isUndergraduate;

    @FXML
    private Label customFieldLabel;

    @FXML
    private TextField customField;

    @FXML
    private Button studentDeleteBtn;

    public void initData(Student student){
        this.student = student;

        firstNameField.textProperty().set(student.getFirstName());
        lastNameField.textProperty().set(student.getLastName());
        ageField.textProperty().set(Integer.toString(student.getAge()));
        courseCombo.setValue(student.getCourse());

        if(student instanceof UndergraduateStudent uStud){
            customFieldLabel.setText("Year: ");
            isUndergraduate = true;

            customField.textProperty().set(Integer.toString(uStud.getYear()));
        }
        else if(student instanceof MasterStudent mStud){
            customFieldLabel.setText("Research area: ");
            isUndergraduate = false;

            customField.textProperty().set(mStud.getResearchArea());
        }
    }

    @FXML
    @Override
    protected void initialize() {
        errorText.setVisible(false);
    }

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

        // Set custom variable for each class seperately
        if(isUndergraduate){
            int year = getNumberInput(customField);

            if(year != -1){
                UndergraduateStudent uStud = (UndergraduateStudent) student;
                uStud.setYear(year);
            }
            else {
                errorText.setVisible(true);
                return;
            }
        }
        else {
            String researchArea = getTextInput(customField);

            if(!researchArea.isEmpty()){
                MasterStudent mStud = (MasterStudent) student;
                mStud.setResearchArea(researchArea);
            }
        }

        StudentManager studentManager = StudentManager.getInstance();

        studentManager.updateStudent(student, firstName, lastName, age, course);

        Parent studentScene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(previousScreen)));

        // Get current stage
        Stage currentStage = (Stage) studentOperationBtn.getScene().getWindow();

        currentStage.setScene(new Scene(studentScene));

    }

    public void onStudentDeleteBtnClick() throws IOException {
        StudentManager studentManager = StudentManager.getInstance();

        studentManager.removeStudent(student);

        Parent studentScene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(previousScreen)));

        // Get current stage
        Stage currentStage = (Stage) studentOperationBtn.getScene().getWindow();

        currentStage.setScene(new Scene(studentScene));
    }
}
