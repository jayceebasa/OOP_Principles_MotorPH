package org.mapua;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class LeaveRepository {
    private static final String FILENAME = "resources/Leaves.csv";

    public List<String[]> readLeaves() throws IOException, CsvValidationException {
        List<String[]> records = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream(FILENAME), StandardCharsets.UTF_8))) {
            String[] row;
            while ((row = reader.readNext()) != null) {
                records.add(row);
            }
        }
        return records;
    }

    public void writeLeaves(List<String[]> records) throws IOException {
        try (CSVWriter writer = new CSVWriter(new FileWriter(FILENAME))) {
            writer.writeAll(records);
        }
    }

    public void updateLeaveStatus(String employeeId, int leaveIndex, String newStatus) throws IOException, CsvValidationException {
        List<String[]> records = readLeaves();

        // Make sure we're updating the correct record by direct index
        if (leaveIndex >= 0 && leaveIndex < records.size()) {
            String[] record = records.get(leaveIndex);
            if (record.length >= 5) {
                // Create a new record with the updated status
                String[] updatedRecord = new String[6];
                System.arraycopy(record, 0, updatedRecord, 0, Math.min(record.length, 5));
                updatedRecord[5] = newStatus;
                records.set(leaveIndex, updatedRecord);
                writeLeaves(records);
            }
        }
    }
}