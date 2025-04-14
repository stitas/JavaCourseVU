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

    public List<Group> getStudentGroups(Student student){
        List<Group> groups = new ArrayList<>();

        for(Enrollment enrollment : enrollmentList){
            if(enrollment.getStudent().equals(student)){
                groups.add(enrollment.getGroup());
            }
        }

        return groups;
    }

}
