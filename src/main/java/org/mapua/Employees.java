package org.mapua;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;

import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Employees {
    // Encapsulation: Private fields for employee details
    private String employeeNumber;
    private String lastName;
    private String firstName;
    private String birthdate;
    private String address;
    private String phoneNumber;
    private String sss;
    private String philHealth;
    private String tin;
    private String pagibig;
    private String status;
    private String position;
    private String immediateSupervisor;
    private String basicSalary;
    private String riceSubsidy;
    private String phoneAllowance;
    private String clothingAllowance;
    private String grossSemiMonthlyRate;
    private String hourlyRate;

    private static final String FILENAME = "resources/Employees.csv";

    // Encapsulation: Public getters and setters for all fields
    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSss() {
        return sss;
    }

    public void setSss(String sss) {
        this.sss = sss;
    }

    public String getPhilHealth() {
        return philHealth;
    }

    public void setPhilHealth(String philHealth) {
        this.philHealth = philHealth;
    }

    public String getTin() {
        return tin;
    }

    public void setTin(String tin) {
        this.tin = tin;
    }

    public String getPagibig() {
        return pagibig;
    }

    public void setPagibig(String pagibig) {
        this.pagibig = pagibig;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getImmediateSupervisor() {
        return immediateSupervisor;
    }

    public void setImmediateSupervisor(String immediateSupervisor) {
        this.immediateSupervisor = immediateSupervisor;
    }

    public String getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(String basicSalary) {
        this.basicSalary = basicSalary;
    }

    public String getRiceSubsidy() {
        return riceSubsidy;
    }

    public void setRiceSubsidy(String riceSubsidy) {
        this.riceSubsidy = riceSubsidy;
    }

    public String getPhoneAllowance() {
        return phoneAllowance;
    }

    public void setPhoneAllowance(String phoneAllowance) {
        this.phoneAllowance = phoneAllowance;
    }

    public String getClothingAllowance() {
        return clothingAllowance;
    }

    public void setClothingAllowance(String clothingAllowance) {
        this.clothingAllowance = clothingAllowance;
    }

    public String getGrossSemiMonthlyRate() {
        return grossSemiMonthlyRate;
    }

    public void setGrossSemiMonthlyRate(String grossSemiMonthlyRate) {
        this.grossSemiMonthlyRate = grossSemiMonthlyRate;
    }

    public String getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(String hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

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
        newRecord[0] = employeeNumber;
        newRecord[1] = lastName;
        newRecord[2] = firstName;
        newRecord[3] = birthdate;
        newRecord[4] = address;
        newRecord[5] = phoneNumber;
        newRecord[6] = sss;
        newRecord[7] = philHealth;
        newRecord[8] = tin;
        newRecord[9] = pagibig;
        newRecord[10] = status;
        newRecord[11] = position;
        newRecord[12] = immediateSupervisor;
        newRecord[13] = basicSalary;
        newRecord[14] = riceSubsidy;
        newRecord[15] = phoneAllowance;
        newRecord[16] = clothingAllowance;
        newRecord[17] = grossSemiMonthlyRate;
        newRecord[18] = hourlyRate;

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
                if (row[0].equals(employeeNumber)) {
                    row[1] = lastName;
                    row[2] = firstName;
                    row[3] = birthdate;
                    row[4] = address;
                    row[5] = phoneNumber;
                    row[6] = sss;
                    row[7] = philHealth;
                    row[8] = tin;
                    row[9] = pagibig;
                    row[10] = status;
                    row[11] = position;
                    row[12] = immediateSupervisor;
                    row[13] = basicSalary;
                    row[14] = riceSubsidy;
                    row[15] = phoneAllowance;
                    row[16] = clothingAllowance;
                    row[17] = grossSemiMonthlyRate;
                    row[18] = hourlyRate;
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
}