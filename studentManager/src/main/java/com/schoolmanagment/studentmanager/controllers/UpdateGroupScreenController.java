package com.schoolmanagment.studentmanager.controllers;

import com.schoolmanagment.studentmanager.data.Group;
import com.schoolmanagment.studentmanager.managers.EnrollmentManager;
import com.schoolmanagment.studentmanager.managers.GroupManager;
import com.schoolmanagment.studentmanager.student.Student;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class UpdateGroupScreenController extends ChildScreenController{
    Group group;
    List<Student> studentsInGroup, studentsNotInGroup;

    @FXML
    private TextField nameField;

    @FXML
    private ListView<Student> studentListView, groupListView;

    @FXML
    private Button removeStudentGroupBtn, addStudentGroupBtn, updateStudentGroupBtn;

    @FXML
    private Text errorText;

    public UpdateGroupScreenController() {
        super("/com/schoolmanagment/studentmanager/groupsScreen.fxml");
    }

    @FXML
    public void initialize() {
        errorText.setVisible(false);
    }

    public void initData(Group group) {
        this.group = group;

        EnrollmentManager enrollmentManager = EnrollmentManager.getInstance();

        this.studentsInGroup = enrollmentManager.getStudents(group);
        this.studentsNotInGroup = enrollmentManager.getAllStudentsNotInGroup(group);

        nameField.textProperty().set(group.getName());
        studentListView.setItems(FXCollections.observableList(studentsNotInGroup));
        groupListView.setItems(FXCollections.observableList(studentsInGroup));
    }

    @FXML
    public void onRemoveStudentGroupBtnClick() {
        removeSelectedStudent();
    }

    @FXML
    public void onAddStudentGroupBtnClick() {
        addSelectedStudent();
    }

    @FXML
    public void onUpdateStudentGroupBtnClick() throws IOException {
        String name = nameField.getText();

        if(name.isEmpty()){
            errorText.setVisible(true);
            return;
        }

        GroupManager groupManager = GroupManager.getInstance();

        groupManager.updateGroup(group, name);

        // Enroll new students to group
        EnrollmentManager enrollmentManager = EnrollmentManager.getInstance();
        List<Student> studentsNotInGroup1 = enrollmentManager.getAllStudentsNotInGroup(group);

        for(Student stud : groupListView.getItems()){
            if(studentsNotInGroup1.contains(stud)){
                enrollmentManager.enrollStudent(stud, group);
            }
        }

        // Delete old students from group
        List<Student> studentsInGroup1 = enrollmentManager.getStudents(group);

        for(Student stud : studentListView.getItems()){
            if(studentsInGroup1.contains(stud)){
                enrollmentManager.removeStudent(stud, group);
            }
        }

        Parent studentScene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(previousScreen)));

        // Get current stage
        Stage currentStage = (Stage) updateStudentGroupBtn.getScene().getWindow();

        currentStage.setScene(new Scene(studentScene));
    }

    private void addSelectedStudent() {
        Student selected = studentListView.getSelectionModel().getSelectedItem();

        if(selected != null){
            studentsNotInGroup.remove(selected);
            studentsInGroup.add(selected);
        }

        studentListView.setItems(FXCollections.observableList(studentsNotInGroup));
        groupListView.setItems(FXCollections.observableList(studentsInGroup));
    }

    private void removeSelectedStudent() {
        Student selected = groupListView.getSelectionModel().getSelectedItem();

        if(selected != null){
            studentsInGroup.remove(selected);
            studentsNotInGroup.add(selected);
        }

        studentListView.setItems(FXCollections.observableList(studentsNotInGroup));
        groupListView.setItems(FXCollections.observableList(studentsInGroup));
    }
}
