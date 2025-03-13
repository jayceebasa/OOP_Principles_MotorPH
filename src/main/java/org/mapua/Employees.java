package org.mapua;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;

import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Employees extends Employee {
    private static final String FILENAME = "resources/Employees.csv";

    // Encapsulation: Public method to get table header
    public String[] getTableHeader() {
        return new String[]{"Employee Id", "Last Name", "First Name", "Birthday", "Status", "Position"};
    }

    // Encapsulation: Public method to generate employee ID
    public String generateId() {
        int newId = 1;
        try {
            EmployeeResource employeeResource = new EmployeeResource();
            CSVReader reader = employeeResource.readCSVEmployee();
            for (String[] row; (row = reader.readNext()) != null; ) {
                if (!row[0].equalsIgnoreCase("Employee #")) {
                    newId = Integer.parseInt(row[0]);
                }
            }
            reader.close();
            employeeResource.closeCSVEmployee();
        } catch (FileNotFoundException | NullPointerException e) {
            throw new RuntimeException(e);
        } catch (IOException | CsvValidationException e) {
            newId = 1;
        }
        return String.valueOf(newId + 1);
    }

    // Abstraction: Public method to get employee by ID
    public Employees getEmployeeById(String employeeId) {
        Employees employee = new Employees();
        try {
            EmployeeResource employeeResource = new EmployeeResource();
            CSVReader reader = employeeResource.readCSVEmployee();
            for (String[] row; (row = reader.readNext()) != null; ) {
                if (!row[0].equalsIgnoreCase("Employee #") && row[0].equalsIgnoreCase(employeeId)) {
                    employee.setEmployeeNumber(row[0].trim());
                    employee.setLastName(row[1].trim());
                    employee.setFirstName(row[2].trim());
                    employee.setBirthdate(row[3].trim());
                    employee.setAddress(row[4].trim());
                    employee.setPhoneNumber(row[5].trim());
                    employee.setSss(row[6].trim());
                    employee.setPhilHealth(row[7].trim());
                    employee.setTin(row[8].trim());
                    employee.setPagibig(row[9].trim());
                    employee.setStatus(row[10].trim());
                    employee.setPosition(row[11].trim());
                    employee.setImmediateSupervisor(row[12].trim());
                    employee.setBasicSalary(row[13].trim());
                    employee.setRiceSubsidy(row[14].trim());
                    employee.setPhoneAllowance(row[15].trim());
                    employee.setClothingAllowance(row[16].trim());
                    employee.setGrossSemiMonthlyRate(row[17].trim());
                    employee.setHourlyRate(row[18].trim());
                }
            }
            reader.close();
            employeeResource.closeCSVEmployee();
        } catch (CsvValidationException | IOException e) {
            throw new RuntimeException(e);
        }
        return employee;
    }

    // Abstraction: Public method to add an employee
    public void addEmployee() {
        String[] newRecord = new String[19];
        newRecord[0] = getEmployeeNumber();
        newRecord[1] = getLastName();
        newRecord[2] = getFirstName();
        newRecord[3] = getBirthdate();
        newRecord[4] = getAddress();
        newRecord[5] = getPhoneNumber();
        newRecord[6] = getSss();
        newRecord[7] = getPhilHealth();
        newRecord[8] = getTin();
        newRecord[9] = getPagibig();
        newRecord[10] = getStatus();
        newRecord[11] = getPosition();
        newRecord[12] = getImmediateSupervisor();
        newRecord[13] = getBasicSalary();
        newRecord[14] = getRiceSubsidy();
        newRecord[15] = getPhoneAllowance();
        newRecord[16] = getClothingAllowance();
        newRecord[17] = getGrossSemiMonthlyRate();
        newRecord[18] = getHourlyRate();

        try {
            EmployeeResource employeeResource = new EmployeeResource();
            CSVWriter writer = new CSVWriter(new FileWriter(FILENAME, true));
            writer.writeNext(newRecord);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Abstraction: Public method to update an employee
    public void updateEmployee() {
        List<String[]> records = new ArrayList<>();
        try {
            EmployeeResource employeeResource = new EmployeeResource();
            CSVReader reader = employeeResource.readCSVEmployee();
            for (String[] row; (row = reader.readNext()) != null; ) {
                if (row[0].equals(getEmployeeNumber())) {
                    row[1] = getLastName();
                    row[2] = getFirstName();
                    row[3] = getBirthdate();
                    row[4] = getAddress();
                    row[5] = getPhoneNumber();
                    row[6] = getSss();
                    row[7] = getPhilHealth();
                    row[8] = getTin();
                    row[9] = getPagibig();
                    row[10] = getStatus();
                    row[11] = getPosition();
                    row[12] = getImmediateSupervisor();
                    row[13] = getBasicSalary();
                    row[14] = getRiceSubsidy();
                    row[15] = getPhoneAllowance();
                    row[16] = getClothingAllowance();
                    row[17] = getGrossSemiMonthlyRate();
                    row[18] = getHourlyRate();
                }
                records.add(row);
            }
            reader.close();

            CSVWriter writer = new CSVWriter(new FileWriter(FILENAME));
            writer.writeAll(records);
            writer.close();
        } catch (IOException | CsvValidationException e) {
            throw new RuntimeException(e);
        }
    }

    // Abstraction: Public method to get table data
    public String[][] getTableData() throws IOException {
        List<String[]> dataList = new ArrayList<>();
        try {
            EmployeeResource employeeResource = new EmployeeResource();
            CSVReader reader = employeeResource.readCSVEmployee();
            String[] row;
            while ((row = reader.readNext()) != null) {
                if (!row[0].equalsIgnoreCase("Employee #")) {
                    String[] record = new String[6];
                    record[0] = row[0].trim(); // Employee Number
                    record[1] = row[1].trim(); // Last Name
                    record[2] = row[2].trim(); // First Name
                    record[3] = row[3].trim(); // Birthdate
                    record[4] = row[10].trim(); // Status
                    record[5] = row[11].trim(); // Position
                    dataList.add(record);
                }
            }
            reader.close();
            employeeResource.closeCSVEmployee();
        } catch (CsvValidationException | IOException e) {
            throw new RuntimeException(e);
        }

        // Convert List<String[]> to String[][]
        String[][] data = new String[dataList.size()][6];
        for (int i = 0; i < dataList.size(); i++) {
            data[i] = dataList.get(i);
        }
        return data;
    }

    // Abstraction: Public method to delete an employee by ID
    public void deleteEmployee(String employeeId) {
        try {
            EmployeeResource employeeResource = new EmployeeResource();
            CSVReader reader = employeeResource.readCSVEmployee();
            List<String[]> records = new ArrayList<>();

            // Read all records except the one to delete
            String[] row;
            while ((row = reader.readNext()) != null) {
                if (!row[0].equalsIgnoreCase("Employee #") && !row[0].equalsIgnoreCase(employeeId)) {
                    records.add(row);
                }
            }
            reader.close();
            employeeResource.closeCSVEmployee();

            // Write the updated records back to the file
            CSVWriter writer = new CSVWriter(new FileWriter(FILENAME));
            writer.writeAll(records);
            writer.close();
        } catch (IOException | CsvValidationException e) {
            throw new RuntimeException("Error deleting employee: " + e.getMessage(), e);
        }
    }

    @Override
    public void computeSalary() {
        // Default implementation for Employees class
        // This can be overridden in subclasses if needed
    }
}