package com.multithreadfileupload.multithreadfileupload.filemanagment;

import java.time.LocalDate;

public record RowData (
    Integer id,
    String firstName,
    String lastName,
    String email,
    String gender,
    String country,
    String domain,
    LocalDate birthDate
) {}
