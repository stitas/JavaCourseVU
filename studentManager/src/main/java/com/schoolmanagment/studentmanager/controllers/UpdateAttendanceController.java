package com.schoolmanagment.studentmanager.controllers;

import com.schoolmanagment.studentmanager.data.Attendance;
import com.schoolmanagment.studentmanager.data.Course;
import com.schoolmanagment.studentmanager.data.Group;
import com.schoolmanagment.studentmanager.managers.AttendanceManager;
import com.schoolmanagment.studentmanager.managers.EnrollmentManager;
import com.schoolmanagment.studentmanager.student.Student;
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
import java.time.LocalDate;
import java.util.Objects;

public class UpdateAttendanceController extends ChildScreenController{
    @FXML
    private DatePicker attendanceDate;

    @FXML
    private RadioButton attendedRadio;

    @FXML
    private RadioButton notAttendedRadio;

    @FXML
    private Button updateAttendanceBtn;

    @FXML
    private Text errorText;

    @FXML
    private ComboBox<Group> groupSelectField;

    private final ToggleGroup radioGroup = new ToggleGroup();
    private Student student;

    public UpdateAttendanceController() {
        super("/com/schoolmanagment/studentmanager/studentScreen.fxml");
    }

    public void initData(Student student) {
        this.student = student;

        EnrollmentManager enrollmentManager = EnrollmentManager.getInstance();

        groupSelectField.setItems(FXCollections.observableList(enrollmentManager.getStudentGroups(student)));

        groupSelectField.setConverter(new StringConverter<Group>() {
            @Override
            public String toString(Group group) {
                return group != null ? group.getName() : "";
            }

            @Override
            public Group fromString(String string) {
                return null;
            }
        });
    }

    @FXML
    public void initialize() {
        attendedRadio.setToggleGroup(radioGroup);
        notAttendedRadio.setToggleGroup(radioGroup);
        attendedRadio.setSelected(true);
        errorText.setVisible(false);
    }

    public void onUpdateAttendanceBtnClick() throws IOException {
        LocalDate date = attendanceDate.getValue();
        Group group = groupSelectField.getValue();

        if(date == null || group == null){
            errorText.setVisible(true);
            return;
        }

        AttendanceManager attendanceManager = AttendanceManager.getInstance();

        if(attendedRadio.isSelected()){
            attendanceManager.addAttendance(new Attendance(student, date, group, true));
        }
        else {
            attendanceManager.addAttendance(new Attendance(student, date, group, false));
        }

        Parent studentScene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(previousScreen)));

        // Get current stage
        Stage currentStage = (Stage) updateAttendanceBtn.getScene().getWindow();

        currentStage.setScene(new Scene(studentScene));
    }
}
