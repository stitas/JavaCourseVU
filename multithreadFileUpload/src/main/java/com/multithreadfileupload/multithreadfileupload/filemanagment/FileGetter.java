package com.multithreadfileupload.multithreadfileupload.filemanagment;

import javafx.stage.FileChooser;

import java.io.File;
import java.util.List;

public class FileGetter {
    public static List<File> getFiles(String fileExtension) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Files", "*." + fileExtension));

        return fileChooser.showOpenMultipleDialog(null);
    }
}
