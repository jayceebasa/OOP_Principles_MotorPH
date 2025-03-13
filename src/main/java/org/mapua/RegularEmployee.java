package org.mapua;

public class RegularEmployee extends Employee {
    @Override
    public void computeSalary() {
        // Implementation for computing salary for regular employees
        double basicSalary = Double.parseDouble(getBasicSalary());
        double riceSubsidy = Double.parseDouble(getRiceSubsidy());
        double phoneAllowance = Double.parseDouble(getPhoneAllowance());
        double clothingAllowance = Double.parseDouble(getClothingAllowance());

        double totalSalary = basicSalary + riceSubsidy + phoneAllowance + clothingAllowance;
        setGrossSemiMonthlyRate(String.valueOf(totalSalary));
    }
}