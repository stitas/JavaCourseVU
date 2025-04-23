module com.schoolmanagment.chatapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.schoolmanagment.chatapp to javafx.fxml;
    exports com.schoolmanagment.chatapp;
}