module com.schoolmanagment.studentmanager {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.schoolmanagment.studentmanager to javafx.fxml;
    exports com.schoolmanagment.studentmanager;
    opens com.schoolmanagment.studentmanager.controllers to javafx.fxml;
    exports com.schoolmanagment.studentmanager.controllers;
    exports com.schoolmanagment.studentmanager.student;
    exports com.schoolmanagment.studentmanager.data;
}