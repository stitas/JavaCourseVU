package com.schoolmanagment.studentmanager.data;

import com.schoolmanagment.studentmanager.student.Student;

import java.time.LocalDate;

public class Attendance {
    private final Student student;
    private final LocalDate date;
    private final Group group;
    private boolean attended;

    public Attendance(Student student, LocalDate date, Group group, boolean attended){
        this.student = student;
        this.date = date;
        this.group = group;
        this.attended = attended;
    }

    public Student getStudent() {
        return student;
    }

    public LocalDate getDate() {
        return date;
    }

    public Group getGroup() {
        return group;
    }

    public boolean didAttend() {
        return  attended;
    }

    public void setAttended(boolean attended) {
        this.attended = attended;
    }
}
