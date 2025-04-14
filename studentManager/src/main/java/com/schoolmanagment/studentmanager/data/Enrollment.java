package com.schoolmanagment.studentmanager.data;

import com.schoolmanagment.studentmanager.student.Student;

public class Enrollment {
    private final Student student;
    private final Group group;

    public Enrollment(Student student, Group group){
        this.student = student;
        this.group = group;
    }

    public Student getStudent() {
        return student;
    }

    public Group getGroup() {
        return group;
    }
}
