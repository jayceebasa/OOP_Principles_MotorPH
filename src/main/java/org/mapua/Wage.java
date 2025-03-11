package org.mapua;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.YearMonth;

public class Wage {
    // Encapsulation: Private fields for wage details
    private String hoursPerWeekWorked;
    private String grossWeeklySalary;
    private String netWeeklySalary;
    private String genericDeduction;
    private String grossMonthlySalary;
    private String netMonthlySalary;
    private static final String FILENAME = "resources/Employees.csv";

    private String basicSalary = "";
    private String riceSubsidy = "";
    private String phoneAllowance = "";
    private String clothingAllowance = "";
    private String perCutOffSalary = "";
    private String hourlyRate = "";

    // Encapsulation: Public getters and setters for all fields
    public String getHoursPerWeekWorked() {
        return hoursPerWeekWorked;
    }

    public void setHoursPerWeekWorked(String hoursPerWeekWorked) {
        this.hoursPerWeekWorked = hoursPerWeekWorked;
    }

    public String getGrossWeeklySalary() {
        return grossWeeklySalary;
    }

    public void setGrossWeeklySalary(String grossWeeklySalary) {
        this.grossWeeklySalary = grossWeeklySalary;
    }

    public String getNetWeeklySalary() {
        return netWeeklySalary;
    }

    public void setNetWeeklySalary(String netWeeklySalary) {
        this.netWeeklySalary = netWeeklySalary;
    }

    public String getGenericDeduction() {
        return genericDeduction;
    }

    public void setGenericDeduction(String genericDeduction) {
        this.genericDeduction = genericDeduction;
    }

    public String getGrossMonthlySalary() {
        return grossMonthlySalary;
    }

    public void setGrossMonthlySalary(String grossMonthlySalary) {
        this.grossMonthlySalary = grossMonthlySalary;
    }

    public String getNetMonthlySalary() {
        return netMonthlySalary;
    }

    public void setNetMonthlySalary(String netMonthlySalary) {
        this.netMonthlySalary = netMonthlySalary;
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

    public String getPerCutOffSalary() {
        return perCutOffSalary;
    }

    public void setPerCutOffSalary(String perCutOffSalary) {
        this.perCutOffSalary = perCutOffSalary;
    }

    public String getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(String hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    // Abstraction: Public method to compute wage by employee ID
    public void computeWageByEmployeeId(String employeeId) {
        try {
            EmployeeResource employeeResource = new EmployeeResource();
            CSVReader reader = employeeResource.readCSVEmployee();
            for (String[] row; (row = reader.readNext()) != null; ) {
                if (!row[0].equalsIgnoreCase("Employee #") && row[0].equalsIgnoreCase(employeeId)) {
                    basicSalary = row[13].trim();
                    riceSubsidy = row[14].trim();
                    phoneAllowance = row[15].trim();
                    clothingAllowance = row[16].trim();
                    perCutOffSalary = row[17].trim();
                    hourlyRate = row[18].trim();
                }
            }
            reader.close();
            employeeResource.closeCSVEmployee();
            compute();
        } catch (CsvValidationException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Encapsulation: Private method to compute wage
    private void compute() {
        double hoursPerWeekOfWorked = Double.parseDouble(this.hoursPerWeekWorked.trim());
        this.grossWeeklySalary = String.valueOf(new BigDecimal(this.hourlyRate).multiply(
                new BigDecimal(hoursPerWeekOfWorked)));
        this.grossMonthlySalary = String.valueOf(new BigDecimal(this.hourlyRate).multiply(
                new BigDecimal(hoursPerWeekOfWorked * 4))); // Assuming 4 weeks in a month
    }
}