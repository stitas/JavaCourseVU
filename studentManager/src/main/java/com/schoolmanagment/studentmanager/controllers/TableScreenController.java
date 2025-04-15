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

public abstract class TableScreenController extends ChildScreenController{
    @FXML
    protected TextField filterField;

    @FXML
    protected Button addBtn, updateBtn;

    protected abstract void applyFilter();
    public abstract void onAddBtnClick() throws IOException;
    public abstract void onUpdateBtnClick() throws IOException;
}
