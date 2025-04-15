package com.schoolmanagment.studentmanager.controllers;

import javafx.scene.control.TextField;

public interface IForm {
    String getTextInput(TextField nameField);
    int getNumberInput(TextField ageField);
}
