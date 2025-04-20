package com.schoolmanagment.studentmanager.helpers;

import com.schoolmanagment.studentmanager.data.Attendance;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

import javax.swing.text.DateFormatter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AttendancePDFExporter {
    public static void exportPDF(List<Attendance> attendanceList) {
        File file = FileGetter.getFile("pdf", true);

        Document document = new Document();

        try {
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();

            // Write title to pdf
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
            Paragraph title = new Paragraph("Attendance Report", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            document.add(title);

            // Create new pdf table with 4 columns
            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);

            // Add column titles
            addHeaderCell(table, "STUDENT ID");
            addHeaderCell(table, "GROUP ID");
            addHeaderCell(table, "DATE");
            addHeaderCell(table, "ATTENDANCE");

            // Populate with data
            for(Attendance attendance : attendanceList){
                table.addCell(String.valueOf(attendance.getStudent().getId()));
                table.addCell(String.valueOf(attendance.getGroup().getId()));
                table.addCell(attendance.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                table.addCell(attendance.didAttend() ? "Attended" : "Did not attend");
            }

            document.add(table);
        }
        catch(FileNotFoundException e) {
            System.out.println("File not found!");
        }
        finally {
            document.close();
        }
    }

    private static void addHeaderCell(PdfPTable table, String text) {
        PdfPCell header = new PdfPCell(new Phrase(text));
        header.setPadding(5);
        table.addCell(header);
    }
}
