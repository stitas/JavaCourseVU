package com.schoolmanagment.studentmanager.student;

import com.schoolmanagment.studentmanager.data.Course;

public abstract class Student {
    private static int incrementer = 0;
    private final int id;
    private String firstName;
    private String lastName;
    private int age;
    private Course course;

    public Student(String firstName, String lastName, int age, Course course) {
        this.id = incrementer;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.course = course;
        incrementer++;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
         this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "ID: " + id + " firstName: " + firstName + " lastName: " + lastName + " age: " + age + " course: " + course.getName() + "\n";
    }
}
