package com.multithreadfileupload.multithreadfileupload.controllers;

import com.multithreadfileupload.multithreadfileupload.filemanagment.RowData;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class TableController {
    @FXML
    private TableView<RowData> table;

    @FXML
    private TableColumn<RowData, Integer> idCol;

    @FXML
    private TableColumn<RowData, String> firstNameCol, lastNameCol, emailCol, genderCol, countryCol, domainCol, birthdateCol;

    @FXML
    private DatePicker dateSortFrom, dateSortTo;

    private ObservableList<RowData> rowDataList;
    private boolean sortId, sortAbc;

    public void initData(List<RowData> rowDataList) {
        this.rowDataList = FXCollections.observableList(rowDataList);
        this.sortId = false;
        this.sortAbc = false;

        table.setItems(this.rowDataList);

        idCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().id()).asObject());
        firstNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().firstName()));
        lastNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().lastName()));
        emailCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().email()));
        genderCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().gender()));
        countryCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().country()));
        domainCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().domain()));
        birthdateCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().birthDate().toString()));

        dateSortFrom.valueProperty().addListener(((observable, oldValue, newValue) -> dateFilter(newValue, dateSortTo.getValue())));
        dateSortTo.valueProperty().addListener(((observable, oldValue, newValue) -> dateFilter(dateSortFrom.getValue(), newValue)));
    }

    private void dateFilter(LocalDate dateLower, LocalDate dateUpper) {
        FilteredList<RowData> filteredList = new FilteredList<>(rowDataList);

        if(dateLower != null && dateUpper == null){
            filteredList.setPredicate(rowData -> rowData.birthDate().isAfter(dateLower.minusDays(1)));
        }
        else if(dateLower == null & dateUpper != null){
            filteredList.setPredicate(rowData -> rowData.birthDate().isBefore(dateUpper.plusDays(1)));
        }
        else if(dateLower != null){
            filteredList.setPredicate(rowData -> rowData.birthDate().isBefore(dateUpper.plusDays(1)) && rowData.birthDate().isAfter(dateLower.minusDays(1)));
        }

        table.setItems(FXCollections.observableList(filteredList));
    }

    public void onSortIDBtnClick() {
        Stream<RowData> sortedList = table.getItems().stream();

        if(sortId) {
            sortedList = sortedList
                    .sorted(Comparator.comparing(RowData::id));
        }
        else {
            sortedList = sortedList
                    .sorted((Comparator.comparing(RowData::id)).reversed());
        }

        sortId = !sortId;

        table.setItems(FXCollections.observableList(sortedList.toList()));
    }

    public void onSortABCBtnClick() {
        Stream<RowData> sortedList = table.getItems().stream();

        if(sortAbc) {
            sortedList = sortedList
                    .sorted((Comparator.comparing(RowData::firstName)));
        }
        else {
            sortedList = sortedList
                    .sorted((Comparator.comparing(RowData::firstName)).reversed());
        }

        sortAbc = !sortAbc;

        table.setItems(FXCollections.observableList(sortedList.toList()));
    }
}
