package com.multithreadfileupload.multithreadfileupload.controllers;

import com.multithreadfileupload.multithreadfileupload.filemanagment.FileGetter;
import com.multithreadfileupload.multithreadfileupload.filemanagment.FileTask;
import com.multithreadfileupload.multithreadfileupload.filemanagment.RowData;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class FileUploadController {
    @FXML
    private Button selectFilesBtn;

    @FXML
    private VBox fileUploadVBox;

    public void onSelectFilesBtnClick() {
        List<File> files = FileGetter.getFiles("csv");
        List<RowData> rowDataList = Collections.synchronizedList(new ArrayList<>());
        List<FileTask> fileTasks = new ArrayList<>();

        selectFilesBtn.setDisable(true);

        for(File file : files) {
            FileTask fileTask = new FileTask(rowDataList, file);
            fileTasks.add(fileTask);
            Thread thread = new Thread(fileTask);
            thread.start();

            // UI
            Label label = new Label(file.getName());

            ProgressBar progressBar = new ProgressBar(0);
            progressBar.setPrefHeight(40); // bigger height
            progressBar.prefWidthProperty().bind(fileUploadVBox.widthProperty().multiply(0.7));

            VBox progressContainer = new VBox(5); // spacing between label and bar
            progressContainer.setAlignment(Pos.CENTER);
            progressContainer.setPadding(new Insets(10, 0, 10, 0));
            progressContainer.getChildren().addAll(label, progressBar);

            fileUploadVBox.getChildren().add(progressContainer);

            // Monitor progress
            ScheduledExecutorService monitor = Executors.newSingleThreadScheduledExecutor();
            monitor.scheduleAtFixedRate(() -> {
                double progress = Math.round(fileTask.getProgress() * Math.pow(10, 2)) / Math.pow(10, 2);

                progressBar.setProgress(progress);

                if(progress == 1) {
                    monitor.shutdown();
                }
            }, 0, 100, TimeUnit.MILLISECONDS);
        }

        // Check if all done
        ScheduledExecutorService overallMonitor = Executors.newSingleThreadScheduledExecutor();
        overallMonitor.scheduleAtFixedRate(() -> {
            boolean allDone = fileTasks.stream().allMatch(FileTask::isDone);
            if (allDone) {
                overallMonitor.shutdown();

                javafx.application.Platform.runLater(() -> {
                    selectFilesBtn.setOnAction(e -> {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/multithreadfileupload/multithreadfileupload/file-table.fxml"));
                        Parent scene;

                        try {
                            scene = loader.load();
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }

                        TableController controller = loader.getController();
                        controller.initData(rowDataList);

                        Stage stage = (Stage) selectFilesBtn.getScene().getWindow();
                        stage.setScene(new Scene(scene));
                    });

                    selectFilesBtn.setText("Go to table");
                    selectFilesBtn.setDisable(false);
                });
            }
        }, 0, 1, TimeUnit.SECONDS);
    }
}
