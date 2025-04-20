package com.schoolmanagment.studentmanager.managers;

import com.schoolmanagment.studentmanager.data.Attendance;
import com.schoolmanagment.studentmanager.student.Student;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AttendanceManager {
    private static AttendanceManager instance;
    private final List<Attendance> attendanceList;

    private AttendanceManager() {
        attendanceList = new ArrayList<>();
    }

    public static AttendanceManager getInstance() {
        if(instance == null){
            instance = new AttendanceManager();
        }

        return instance;
    }

    public void addAttendance(Attendance attendance){
        for(Attendance atdnc : attendanceList){
            // If attendance already exists update only the attendance boolean
            if(atdnc.getStudent().equals(attendance.getStudent()) && atdnc.getGroup().equals(attendance.getGroup()) && atdnc.getDate().isEqual(attendance.getDate())){
                atdnc.setAttended(attendance.didAttend());
                return;
            }
        }

        // else add new attendance to the attendance list
        attendanceList.add(attendance);
    }

    public List<Attendance> getStudentAttendance(Student student){
        List<Attendance> studentAttendanceList = new ArrayList<>();

        for(Attendance attendance : attendanceList){
            if(attendance.getStudent().equals(student)){
                studentAttendanceList.add(attendance);
            }
        }

        return studentAttendanceList;
    }

    public FilteredList<Attendance> getAttendanceFilteredList() {
        return new FilteredList<>(FXCollections.observableList(attendanceList));
    }

    public List<Attendance> getAttendanceByDate(LocalDate startDate, LocalDate endDate) {
        List<Attendance> attendanceByDate = new ArrayList<>();

        for(Attendance attendance : attendanceList){
            // If attendance date in range add to list
            if(attendance.getDate().isAfter(startDate.minusDays(1)) && attendance.getDate().isBefore(endDate.plusDays(1))){
                attendanceByDate.add(attendance);
            }
        }

        return attendanceByDate;
    }
}
