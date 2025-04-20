package com.schoolmanagment.studentmanager.helpers;

import javafx.stage.FileChooser;

import java.io.File;

public class FileGetter {
    public static File getFile(String fileExtension, boolean save) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Files", "*." + fileExtension));

        File file;

        if(save) {
            file = fileChooser.showSaveDialog(null);
        }
        else {
            file = fileChooser.showOpenDialog(null);
        }

        return file;
    }
}
