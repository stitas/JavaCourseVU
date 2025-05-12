module com.multithreadfileupload.multithreadfileupload {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.multithreadfileupload.multithreadfileupload to javafx.fxml;
    exports com.multithreadfileupload.multithreadfileupload;

    opens com.multithreadfileupload.multithreadfileupload.controllers to javafx.fxml;
    exports com.multithreadfileupload.multithreadfileupload.controllers;
}