package org.mapua;

public class PartTimeEmployee extends Employee {
    private int hoursWorked;

    public PartTimeEmployee(int hoursWorked) {
        this.hoursWorked = hoursWorked;
    }

    @Override
    public void computeSalary() {
        // Implementation for computing salary for part-time employees
        double hourlyRate = Double.parseDouble(getHourlyRate());
        double totalSalary = hourlyRate * hoursWorked;
        setGrossSemiMonthlyRate(String.valueOf(totalSalary));
    }

    // Method overloading
    public void computeSalary(double bonus) {
        double hourlyRate = Double.parseDouble(getHourlyRate());
        double totalSalary = (hourlyRate * hoursWorked) + bonus;
        setGrossSemiMonthlyRate(String.valueOf(totalSalary));
    }
}