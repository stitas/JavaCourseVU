package com.schoolmanagment.studentmanager.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

public abstract class TableScreenController extends ChildScreenController{
    @FXML
    protected TextField filterField;

    @FXML
    protected Button addBtn, updateBtn;

    public TableScreenController(String previousScreen) {
        super(previousScreen);
    }

    protected abstract void applyFilter();
    public abstract void onAddBtnClick() throws IOException;
    public abstract void onUpdateBtnClick() throws IOException;
}
