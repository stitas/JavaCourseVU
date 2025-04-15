package com.schoolmanagment.studentmanager.controllers;

import com.schoolmanagment.studentmanager.data.Course;
import com.schoolmanagment.studentmanager.data.MasterCourse;
import com.schoolmanagment.studentmanager.data.UndergraduateCourse;
import com.schoolmanagment.studentmanager.managers.StudentManager;
import com.schoolmanagment.studentmanager.student.MasterStudent;
import com.schoolmanagment.studentmanager.student.UndergraduateStudent;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class AddStudentScreenController extends ChildScreenController implements IForm {
    @FXML
    private TextField firstNameField, lastNameField, ageField;

    @FXML
    private RadioButton undergraduateRadio, masterRadio;

    @FXML
    private ComboBox<Course> courseCombo;

    @FXML
    private Button studentAddBtn;

    @FXML
    private Text errorText;

    private final ToggleGroup radioGroup = new ToggleGroup();
    RadioButton selectedRadio;

    @FXML
    private void initialize() {
        pushPreviousScreen();
        undergraduateRadio.setToggleGroup(radioGroup);
        masterRadio.setToggleGroup(radioGroup);
        radioGroup.selectedToggleProperty().addListener(e -> onRadioChange());
        undergraduateRadio.setSelected(true);
        errorText.setVisible(false);
    }

    public void onStudentAddBtnClick() throws IOException {
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
            studentManager.addStudent(new MasterStudent(firstName, lastName, age, course, ""));
        }

        Parent studentScene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(previousScreen)));

        // Get current stage
        Stage currentStage = (Stage) studentAddBtn.getScene().getWindow();

        currentStage.setScene(new Scene(studentScene));
        
    }

    private void onRadioChange() {
        selectedRadio = (RadioButton) radioGroup.getSelectedToggle();
        List<Course> enumValues = new ArrayList<>();

        if(selectedRadio == undergraduateRadio){
            enumValues = Arrays.asList(UndergraduateCourse.values());
        }
        else {
            enumValues = Arrays.asList(MasterCourse.values());
        }

        courseCombo.setItems(FXCollections.observableList(enumValues));

        // Displays normal text instead of enum text
        courseCombo.setConverter(new StringConverter<Course>() {
            @Override
            public String toString(Course course) {
                return course != null ? course.getName() : "";
            }

            @Override
            public Course fromString(String string) {
                return null;
            }
        });
    }


    /** Gets String from input. Returns -1 if input is not String **/
    @Override
    public String getTextInput(TextField nameField) {
        String text;

        try {
            if(nameField.getText().isEmpty()){
                return "";
            }
            else {
                text = nameField.getText();
            }

        } catch (Exception e){
            return "";
        }

        if(!text.matches("^[a-zA-Z ]+$")){
            return "";
        }

        return text;
    }

    /** Gets number from input. Returns -1 if input is not number or is less than 0 **/
    @Override
    public int getNumberInput(TextField ageField) {
        int num;

        try {
            if(ageField.getText().isEmpty()){
                return -1;
            }
            else {
                num = Integer.parseInt(ageField.getText());
            }

        } catch (Exception e){
            return -1;
        }

        if(num < 0 || num > 110){
            return -1;
        }

        return num;
    }

    @Override
    protected void pushPreviousScreen() {
        previousScreen = "/com/schoolmanagment/studentmanager/studentScreen.fxml";
    }

}
