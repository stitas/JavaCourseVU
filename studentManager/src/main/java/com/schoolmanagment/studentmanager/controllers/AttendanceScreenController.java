package com.schoolmanagment.studentmanager.controllers;

import com.schoolmanagment.studentmanager.data.Attendance;
import com.schoolmanagment.studentmanager.helpers.AttendancePDFExporter;
import com.schoolmanagment.studentmanager.managers.AttendanceManager;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.Pair;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class AttendanceScreenController extends ChildScreenController {
    @FXML
    private TableView<Attendance> table;

    @FXML
    private TableColumn<Attendance, Integer> groupIdCol, studentIdCol;

    @FXML
    private TableColumn<Attendance, LocalDate> dateCol;

    @FXML
    private TableColumn<Attendance, String> attendanceCol;

    @FXML
    private TextField groupFilterField;

    @FXML
    private TextField studentFilterField;

    @FXML
    private Button savePDFBtn;

    private FilteredList<Attendance> filteredData;

    public AttendanceScreenController() {
        super("/com/schoolmanagment/studentmanager/index.fxml");
    }

    @FXML
    public void initialize() {
        AttendanceManager attendanceManager = AttendanceManager.getInstance();

        filteredData = attendanceManager.getAttendanceFilteredList();

        filteredData.forEach(e -> System.out.println(e.getStudent()));

        groupIdCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getGroup().getId()).asObject());
        studentIdCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getStudent().getId()).asObject());
        dateCol.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getDate()));
        attendanceCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().didAttend() ? "Attended" : "Did not attend"));

        table.setItems(filteredData);

        groupFilterField.textProperty().addListener(e -> applyFilter());
        studentFilterField.textProperty().addListener(e -> applyFilter());
    }

    public void onSavePDFBtnClick() {
        Optional<Pair<LocalDate, LocalDate>> result = DateRangeDialog.showDateRangeDialog();

        result.ifPresent(range -> {
            LocalDate start = range.getKey();
            LocalDate end = range.getValue();

            AttendanceManager attendanceManager = AttendanceManager.getInstance();

            List<Attendance> attendanceList = attendanceManager.getAttendanceByDate(start, end);

            AttendancePDFExporter.exportPDF(attendanceList);
        });
    }

    private void applyFilter() {
        String groupText = groupFilterField.getText();
        String studentText = studentFilterField.getText();

        filteredData.setPredicate(attendance -> {
            boolean matchesGroup = true;
            boolean matchesStudent = true;

            if(!groupText.isEmpty()){
                try {
                    int groupId = Integer.parseInt(groupText);

                    matchesGroup = attendance.getGroup().getId() == groupId;
                }
                catch (NumberFormatException e){
                    matchesGroup = false;
                }
            }

            if(!studentText.isEmpty()){
                try {
                    int studentId = Integer.parseInt(studentText);

                    matchesStudent = attendance.getStudent().getId() == studentId;
                } catch (NumberFormatException e) {
                    matchesStudent = false;
                }
            }

            return matchesStudent && matchesGroup;
        });
    }

}
