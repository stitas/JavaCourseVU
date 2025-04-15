package com.schoolmanagment.studentmanager.controllers;

import com.schoolmanagment.studentmanager.data.Group;
import com.schoolmanagment.studentmanager.managers.EnrollmentManager;
import com.schoolmanagment.studentmanager.managers.GroupManager;
import com.schoolmanagment.studentmanager.managers.StudentManager;
import com.schoolmanagment.studentmanager.student.Student;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;

public class GroupScreenController extends TableScreenController{
    @FXML
    protected TableView<Group> table;
    public TableColumn<Group, Integer> idCol, studentCountCol;
    public TableColumn<Group, String> nameCol;

    FilteredList<Group> filteredData;

    @FXML
    private void initialize(){
        pushPreviousScreen();

        EnrollmentManager enrollmentManager = EnrollmentManager.getInstance();
        GroupManager groupManager = GroupManager.getInstance();

        groupManager.addGroup(new Group("Discrete mathematics"));

        filteredData = groupManager.getGroupFilteredList();

        idCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        studentCountCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(enrollmentManager.getStudentCount(cellData.getValue())).asObject());
        nameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));

        table.setItems(filteredData);
    }

    @Override
    protected void applyFilter() {
        String text = filterField.getText();

        filteredData.setPredicate(group -> group.getName().toLowerCase().startsWith(text.toLowerCase()));
    }

    @Override
    public void onAddBtnClick() throws IOException {

    }

    @Override
    public void onUpdateBtnClick() throws IOException {

    }

    @Override
    protected void pushPreviousScreen() {
        previousScreen = "/com/schoolmanagment/studentmanager/index.fxml";
    }
}
