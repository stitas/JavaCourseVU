    package com.schoolmanagment.studentmanager.helpers;

import com.schoolmanagment.studentmanager.data.Course;
import com.schoolmanagment.studentmanager.data.MasterCourse;
import com.schoolmanagment.studentmanager.data.UndergraduateCourse;
import com.schoolmanagment.studentmanager.managers.StudentManager;
import com.schoolmanagment.studentmanager.student.MasterStudent;
import com.schoolmanagment.studentmanager.student.Student;
import com.schoolmanagment.studentmanager.student.UndergraduateStudent;

import java.io.*;
import java.util.List;

public class StudentDataCSVManager {
    public static void importCSV() {
        File file = FileGetter.getFile("csv", false);

        String line;
        String delimiter = ",";

        try {
            StudentManager studentManager = StudentManager.getInstance();

            BufferedReader br = new BufferedReader(new FileReader(file));

            // Skip first line because it is csv header
            br.readLine();

            // Read all rows of CSV file
            while ((line = br.readLine()) != null) {
                Student student;

                String[] values = line.split(delimiter);

                String firstName = values[0];
                String lastName = values[1];
                int age;

                try {
                    age = Integer.parseInt(values[2]);
                }
                catch (Exception e){
                    System.out.println("Failed to read age");
                    return;
                }

                String degree = values[4];

                if(degree.equals("Undergraduate")){
                    Course course;
                    int year;

                    try {
                        course = UndergraduateCourse.fromString(values[3]);
                    }
                    catch (IllegalArgumentException e) {
                        System.out.println("Failed to read course");
                        return;
                    }

                    try {
                        year = Integer.parseInt(values[5]);
                    }
                    catch (Exception e){
                        System.out.println("Invalid 'other' field");
                        return;
                    }

                    student = new UndergraduateStudent(firstName, lastName, age, course, year);
                }
                else if(degree.equals("Master")){
                    Course course;
                    String researchArea;

                    try {
                        course = MasterCourse.fromString(values[3]);
                    }
                    catch (IllegalArgumentException e) {
                        System.out.println("Failed to read course");
                        return;
                    }

                    researchArea = values[5];

                    student = new MasterStudent(firstName, lastName, age, course, researchArea);
                }
                else {
                    System.out.println("Failed to read course");
                    return;
                }

                studentManager.addStudent(student);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void exportCSV(List<Student> studentList) throws IOException {
        File file = FileGetter.getFile("csv", true);
        Writer writer = null;

        try {
             writer = new BufferedWriter(new FileWriter(file));

             String columnString = "firstName,lastName,age,course,degree,other\n";

             writer.write(columnString);

             for(Student student : studentList){
                 String row;

                 if(student instanceof UndergraduateStudent uStud){
                     row = uStud.getFirstName() + "," + uStud.getLastName() + "," + uStud.getAge() + "," + uStud.getCourse().getName() + "," + "Undergraduate" + "," + uStud.getYear() + "\n";
                 }
                 else if(student instanceof  MasterStudent mStud) {
                     row = mStud.getFirstName() + "," + mStud.getLastName() + "," + mStud.getAge() + "," + mStud.getCourse().getName() + "," + "Master" + "," + mStud.getResearchArea() + "\n";
                 }
                 else {
                     row = student.getFirstName() + "," + student.getLastName() + "," + student.getAge() + "," + student.getCourse().getName() + "," + "-" + "\n";
                 }

                 writer.write(row);
             }
        }
        catch (Exception e){
            System.out.println("Failed to open file");
            return;
        }
        finally {
            if(writer != null){
                writer.flush();
                writer.close();
            }
        }
    }
}
