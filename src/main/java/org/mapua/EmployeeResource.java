package org.mapua;

import com.opencsv.CSVReader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class EmployeeResource {
    // Encapsulation: Private fields for file handling
    private FileInputStream fileInputStream;
    private InputStreamReader inputStreamReader;
    private static final String FILENAME = "resources/Employees.csv";

    // Encapsulation: Public method to read CSV
    public CSVReader readCSVEmployee() throws IOException {
        try {
            fileInputStream = new FileInputStream(FILENAME);
            inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
            return new CSVReader(inputStreamReader);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // Encapsulation: Public method to close CSV
    public void closeCSVEmployee() throws IOException {
        if (inputStreamReader != null) inputStreamReader.close();
        if (fileInputStream != null) fileInputStream.close();
    }
}