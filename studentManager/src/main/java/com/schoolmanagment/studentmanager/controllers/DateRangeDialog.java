package com.schoolmanagment.studentmanager.controllers;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

import java.time.LocalDate;
import java.util.Optional;

public class DateRangeDialog {

    public static Optional<Pair<LocalDate, LocalDate>> showDateRangeDialog() {
        Dialog<Pair<LocalDate, LocalDate>> dialog = new Dialog<>();
        dialog.setTitle("Select Date Range");
        dialog.setHeaderText("Choose Start and End Dates");

        ButtonType applyButtonType = new ButtonType("Apply", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(applyButtonType, ButtonType.CANCEL);

        DatePicker startDatePicker = new DatePicker();
        DatePicker endDatePicker = new DatePicker();

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        grid.add(new Label("Start Date:"), 0, 0);
        grid.add(startDatePicker, 1, 0);
        grid.add(new Label("End Date:"), 0, 1);
        grid.add(endDatePicker, 1, 1);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == applyButtonType) {
                return new Pair<>(startDatePicker.getValue(), endDatePicker.getValue());
            }
            return null;
        });

        return dialog.showAndWait();
    }
}
