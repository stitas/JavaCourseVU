package com.schoolmanagment.studentmanager.managers;

import com.schoolmanagment.studentmanager.data.Course;
import com.schoolmanagment.studentmanager.student.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import java.util.ArrayList;
import java.util.List;

public class StudentManager {
    private static StudentManager instance;
    private final List<Student> students;

    public StudentManager() {
        students = new ArrayList<>();
    }

    public static StudentManager getInstance(){
        if(instance == null){
            instance = new StudentManager();
        }

        return instance;
    }

    public void addStudent(Student student){
        students.add(student);
    }

    public FilteredList<Student> getStudentsFilteredList(){
        return new FilteredList<>(FXCollections.observableList(students));
    }

    public void removeStudent(Student student){
        students.removeIf(stud -> stud.equals(student));
    }

    public void updateStudent(Student student, String firstName, String lastName, int age, Course course){
        for(Student stud : students){
            if(stud.getId() == student.getId()){
                stud.setFirstName(firstName);
                stud.setLastName(lastName);
                stud.setAge(age);
                stud.setCourse(course);
            }
        }
    }
}
