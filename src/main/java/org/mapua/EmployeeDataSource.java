package org.mapua;

import com.opencsv.exceptions.CsvValidationException;
import java.io.IOException;
import java.util.List;

public interface EmployeeDataSource {
    List<String[]> readEmployees() throws IOException, CsvValidationException;
    void writeEmployees(List<String[]> records) throws IOException;
}