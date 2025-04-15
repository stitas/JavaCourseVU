package com.schoolmanagment.studentmanager.data;

public class Group {
    private static int objCount = 0;
    private final int id;
    private String name;

    public Group(String name) {
        this.id = objCount;
        this.name = name;
        objCount++;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }
}
