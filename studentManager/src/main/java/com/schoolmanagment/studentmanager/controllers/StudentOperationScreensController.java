package com.schoolmanagment.studentmanager.controllers;

import com.schoolmanagment.studentmanager.data.Course;
import com.schoolmanagment.studentmanager.data.MasterCourse;
import com.schoolmanagment.studentmanager.data.UndergraduateCourse;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.util.StringConverter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class StudentOperationScreensController extends ChildScreenController {
    @FXML
    protected TextField firstNameField, lastNameField, ageField;

    @FXML
    protected RadioButton undergraduateRadio, masterRadio;

    @FXML
    protected ComboBox<Course> courseCombo;

    @FXML
    protected Button studentOperationBtn;

    @FXML
    protected Text errorText;

    protected final ToggleGroup radioGroup = new ToggleGroup();
    protected RadioButton selectedRadio;

    @FXML
    protected void initialize() {
        pushPreviousScreen();
        undergraduateRadio.setToggleGroup(radioGroup);
        masterRadio.setToggleGroup(radioGroup);
        radioGroup.selectedToggleProperty().addListener(e -> onRadioChange());
        undergraduateRadio.setSelected(true);
        errorText.setVisible(false);
    }

    protected abstract void onStudentOperationBtnClick() throws IOException;

    protected void onRadioChange() {
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
