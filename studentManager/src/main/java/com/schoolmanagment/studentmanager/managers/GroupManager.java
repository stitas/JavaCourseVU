package com.schoolmanagment.studentmanager.managers;

import com.schoolmanagment.studentmanager.data.Group;
import com.schoolmanagment.studentmanager.student.Student;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;

import java.util.ArrayList;
import java.util.List;

public class GroupManager {
    private static GroupManager instance;
    private List<Group> groups;

    private GroupManager() {
        groups = new ArrayList<>();
    }

    public static GroupManager getInstance() {
        if(instance == null){
            instance = new GroupManager();
        }

        return instance;
    }

    public void addGroup(Group group){
        groups.add(group);
    }

    public FilteredList<Group> getGroupFilteredList(){
        return new FilteredList<>(FXCollections.observableList(groups));
    }

    public void removeStudent(Group group){
        groups.removeIf(gr -> gr.equals(group));
    }
}
