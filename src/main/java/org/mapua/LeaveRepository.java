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
}