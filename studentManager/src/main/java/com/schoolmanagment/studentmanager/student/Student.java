package com.schoolmanagment.studentmanager.student;

import com.schoolmanagment.studentmanager.data.Course;

public abstract class Student {
    private static int incrementer = 0;
    private final int id;
    private final String firstName;
    private final String lastName;
    private final int age;
    private final Course course;

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

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public Course getCourse() {
        return course;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "ID: " + id + " firstName: " + firstName + " lastName: " + lastName + " age: " + age + " course: " + course.getName() + "\n";
    }
}
