package com.schoolmanagment.studentmanager.student;

import com.schoolmanagment.studentmanager.data.Course;

public class MasterStudent extends Student{
    private String researchArea;

    public MasterStudent(String firstName, String lastName, int age, Course course, String researchArea) {
        super(firstName, lastName, age, course);
        this.researchArea = researchArea;
    }

    public String getResearchArea() {
        return researchArea;
    }

    public void setResearchArea(String researchArea) {
        this.researchArea = researchArea;
    }
}
