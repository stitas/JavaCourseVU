package com.schoolmanagment.studentmanager.managers;

import com.schoolmanagment.studentmanager.data.Enrollment;
import com.schoolmanagment.studentmanager.data.Group;
import com.schoolmanagment.studentmanager.student.Student;

import java.util.ArrayList;
import java.util.List;

public class EnrollmentManager {
    private static EnrollmentManager instance;
    List<Enrollment> enrollmentList;

    private EnrollmentManager(){
        enrollmentList = new ArrayList<>();
    }

    public static EnrollmentManager getInstance(){
        if(instance == null){
            instance = new EnrollmentManager();
        }

        return instance;
    }

    public void enrollStudent(Student student, Group group){
        enrollmentList.add(new Enrollment(student, group));
    }

    public void removeStudent(Student student, Group group){
        enrollmentList.removeIf(enrollment -> enrollment.getStudent().equals(student) && enrollment.getGroup().equals(group));
    }

    public List<Student> getStudents(Group group){
        List<Student> students = new ArrayList<>();

        for(Enrollment enrollment : enrollmentList){
            if(enrollment.getGroup().equals(group)){
                students.add(enrollment.getStudent());
            }
        }

        return students;
    }

    public int getStudentCount(Group group){
        int count = 0;

        for(Enrollment enrollment : enrollmentList){
            if(enrollment.getGroup().equals(group)){
                count++;
            }
        }

        return count;
    }

}
