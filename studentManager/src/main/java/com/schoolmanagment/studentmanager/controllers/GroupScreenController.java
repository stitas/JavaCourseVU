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
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

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

        filteredData = groupManager.getGroupFilteredList();

        idCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        studentCountCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(enrollmentManager.getStudentCount(cellData.getValue())).asObject());
        nameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));

        table.setItems(filteredData);
        filterField.textProperty().addListener(e -> applyFilter());
    }

    @Override
    protected void applyFilter() {
        String text = filterField.getText();

        filteredData.setPredicate(group -> group.getName().toLowerCase().startsWith(text.toLowerCase()));
    }

    @Override
    public void onAddBtnClick() throws IOException {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Create group");
        dialog.setHeaderText("Create group");

        GroupManager groupManager = GroupManager.getInstance();
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(res -> groupManager.addGroup(new Group(res)));
        table.setItems(groupManager.getGroupFilteredList());
    }

    @Override
    public void onUpdateBtnClick() throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/schoolmanagment/studentmanager/updateGroupScreen.fxml"));
            Parent studentScene = loader.load();

            UpdateGroupScreenController controller = loader.getController();
            controller.initData(table.getSelectionModel().getSelectedItem());

            // Get current stage
            Stage currentStage = (Stage) updateBtn.getScene().getWindow();

            currentStage.setScene(new Scene(studentScene));
        }
        // Do nothing if user has not selected student from the table
        catch (NullPointerException e) {
            e.printStackTrace();
            return;
        }
    }

    @Override
    protected void pushPreviousScreen() {
        previousScreen = "/com/schoolmanagment/studentmanager/index.fxml";
    }
}
