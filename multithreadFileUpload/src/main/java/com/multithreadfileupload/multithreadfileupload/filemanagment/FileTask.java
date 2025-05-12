package com.multithreadfileupload.multithreadfileupload.filemanagment;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class FileTask implements Runnable {
    private final List<RowData> fileData;
    private final File file;
    private int progressCount;
    private int lineCount;
    private boolean done;

    public FileTask(List<RowData> fileData, File file) {
        this.fileData = fileData;
        this.file = file;
        this.done = false;
    }

    public float getProgress() {
        return (float) progressCount / lineCount;
    }

    public boolean isDone() {
        return done;
    }

    private void getLineCount() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            while (bufferedReader.readLine() != null){
                lineCount++;
            }
            bufferedReader.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
        catch (IOException e) {
            System.out.println("Error reading file");
        }
    }

    @Override
    public void run() {
        getLineCount();

        try {
            String line;
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

            // Skip first line because its csv header
            bufferedReader.readLine();

            // Read all rows
            while((line = bufferedReader.readLine()) != null){
                String[] values = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

                int id;
                LocalDate date;

                try {
                    id = Integer.parseInt(values[0]);
                }
                catch (NumberFormatException e) {
                    System.out.println("Invalid ID");
                    return;
                }

                try {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
                    date = LocalDate.parse(values[7], formatter);
                }
                catch (DateTimeParseException e) {
                    System.out.println(line);
                    System.out.println("Invalid date " + values[7] + " " + file.getName());
                    return;
                }

                RowData rowData = new RowData(
                        id,
                        values[1],
                        values[2],
                        values[3],
                        values[4],
                        values[5],
                        values[6],
                        date
                );

                fileData.add(rowData);

                progressCount++;

                Thread.sleep(5);
            }

            bufferedReader.close();
            done = true;
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
