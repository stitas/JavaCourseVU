package com.schoolmanagment.studentmanager.student;

import com.schoolmanagment.studentmanager.data.Course;

public class UndergraduateStudent extends Student{
    private int year;

    public UndergraduateStudent(String firstName, String lastName, int age, Course course, int year) {
        super(firstName, lastName, age, course);
        this.year = year;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
