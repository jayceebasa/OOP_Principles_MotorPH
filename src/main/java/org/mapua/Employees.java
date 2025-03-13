package org.mapua;

import com.opencsv.exceptions.CsvValidationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Employees {
    private List<Employee> employeeList;
    private EmployeeRepository employeeRepository;

    // Constructor with dependency injection
    public Employees(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
        this.employeeList = new ArrayList<>();
    }

    // Encapsulation: Public method to get table header
    public String[] getTableHeader() {
        return new String[]{"Employee Id", "Last Name", "First Name", "Birthday", "Status", "Position"};
    }

    // Encapsulation: Public method to generate employee ID
    public String generateId() {
        int newId = 1;
        try {
            List<String[]> records = employeeRepository.readEmployees();
            for (String[] row : records) {
                if (!row[0].equalsIgnoreCase("Employee #")) {
                    newId = Integer.parseInt(row[0]);
                }
            }
        } catch (IOException | CsvValidationException e) {
            throw new RuntimeException(e);
        }
        return String.valueOf(newId + 1);
    }

    // Abstraction: Public method to get employee by ID
    // Abstraction: Public method to get employee by ID
    public Employee getEmployeeById(String employeeId) {
        try {
            List<String[]> records = employeeRepository.readEmployees();
            for (String[] row : records) {
                if (!row[0].equalsIgnoreCase("Employee #") && row[0].equalsIgnoreCase(employeeId)) {
                    // Use a concrete subclass (e.g., RegularEmployee)
                    Employee employee = new RegularEmployee(); // Or PartTimeEmployee
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
                    return employee;
                }
            }
        } catch (IOException | CsvValidationException e) {
            throw new RuntimeException(e);
        }
        return null; // Return null if no employee is found
    }

    // Abstraction: Public method to add an employee
    public void addEmployee(Employee employee) {
        String[] newRecord = new String[19];
        newRecord[0] = employee.getEmployeeNumber();
        newRecord[1] = employee.getLastName();
        newRecord[2] = employee.getFirstName();
        newRecord[3] = employee.getBirthdate();
        newRecord[4] = employee.getAddress();
        newRecord[5] = employee.getPhoneNumber();
        newRecord[6] = employee.getSss();
        newRecord[7] = employee.getPhilHealth();
        newRecord[8] = employee.getTin();
        newRecord[9] = employee.getPagibig();
        newRecord[10] = employee.getStatus();
        newRecord[11] = employee.getPosition();
        newRecord[12] = employee.getImmediateSupervisor();
        newRecord[13] = employee.getBasicSalary();
        newRecord[14] = employee.getRiceSubsidy();
        newRecord[15] = employee.getPhoneAllowance();
        newRecord[16] = employee.getClothingAllowance();
        newRecord[17] = employee.getGrossSemiMonthlyRate();
        newRecord[18] = employee.getHourlyRate();

        try {
            List<String[]> records = employeeRepository.readEmployees();
            records.add(newRecord);
            employeeRepository.writeEmployees(records);
        } catch (IOException | CsvValidationException e) {
            throw new RuntimeException(e);
        }
    }

    // Abstraction: Public method to update an employee
    public void updateEmployee(Employee employee) {
        try {
            List<String[]> records = employeeRepository.readEmployees();
            for (String[] row : records) {
                if (row[0].equals(employee.getEmployeeNumber())) {
                    row[1] = employee.getLastName();
                    row[2] = employee.getFirstName();
                    row[3] = employee.getBirthdate();
                    row[4] = employee.getAddress();
                    row[5] = employee.getPhoneNumber();
                    row[6] = employee.getSss();
                    row[7] = employee.getPhilHealth();
                    row[8] = employee.getTin();
                    row[9] = employee.getPagibig();
                    row[10] = employee.getStatus();
                    row[11] = employee.getPosition();
                    row[12] = employee.getImmediateSupervisor();
                    row[13] = employee.getBasicSalary();
                    row[14] = employee.getRiceSubsidy();
                    row[15] = employee.getPhoneAllowance();
                    row[16] = employee.getClothingAllowance();
                    row[17] = employee.getGrossSemiMonthlyRate();
                    row[18] = employee.getHourlyRate();
                }
            }
            employeeRepository.writeEmployees(records);
        } catch (IOException | CsvValidationException e) {
            throw new RuntimeException(e);
        }
    }

    // Abstraction: Public method to get table data
    public String[][] getTableData() {
        List<String[]> dataList = new ArrayList<>();
        try {
            List<String[]> records = employeeRepository.readEmployees();
            for (String[] row : records) {
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
        } catch (IOException | CsvValidationException e) {
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
            List<String[]> records = employeeRepository.readEmployees();
            List<String[]> updatedRecords = new ArrayList<>();

            // Read all records except the one to delete
            for (String[] row : records) {
                if (!row[0].equalsIgnoreCase("Employee #") && !row[0].equalsIgnoreCase(employeeId)) {
                    updatedRecords.add(row);
                }
            }

            // Write the updated records back to the file
            employeeRepository.writeEmployees(updatedRecords);
        } catch (IOException | CsvValidationException e) {
            throw new RuntimeException("Error deleting employee: " + e.getMessage(), e);
        }
    }
}