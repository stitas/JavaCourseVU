package com.schoolmanagment.studentmanager.managers;

import com.schoolmanagment.studentmanager.data.Attendance;
import com.schoolmanagment.studentmanager.student.Student;

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
}
