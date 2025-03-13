package org.mapua;

public abstract class Employee {
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

    // Encapsulation: Public getters and setters for all fields
    public String getEmployeeNumber() { return employeeNumber; }
    public void setEmployeeNumber(String employeeNumber) { this.employeeNumber = employeeNumber; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getBirthdate() { return birthdate; }
    public void setBirthdate(String birthdate) { this.birthdate = birthdate; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public String getSss() { return sss; }
    public void setSss(String sss) { this.sss = sss; }
    public String getPhilHealth() { return philHealth; }
    public void setPhilHealth(String philHealth) { this.philHealth = philHealth; }
    public String getTin() { return tin; }
    public void setTin(String tin) { this.tin = tin; }
    public String getPagibig() { return pagibig; }
    public void setPagibig(String pagibig) { this.pagibig = pagibig; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }
    public String getImmediateSupervisor() { return immediateSupervisor; }
    public void setImmediateSupervisor(String immediateSupervisor) { this.immediateSupervisor = immediateSupervisor; }
    public String getBasicSalary() { return basicSalary; }
    public void setBasicSalary(String basicSalary) { this.basicSalary = basicSalary; }
    public String getRiceSubsidy() { return riceSubsidy; }
    public void setRiceSubsidy(String riceSubsidy) { this.riceSubsidy = riceSubsidy; }
    public String getPhoneAllowance() { return phoneAllowance; }
    public void setPhoneAllowance(String phoneAllowance) { this.phoneAllowance = phoneAllowance; }
    public String getClothingAllowance() { return clothingAllowance; }
    public void setClothingAllowance(String clothingAllowance) { this.clothingAllowance = clothingAllowance; }
    public String getGrossSemiMonthlyRate() { return grossSemiMonthlyRate; }
    public void setGrossSemiMonthlyRate(String grossSemiMonthlyRate) { this.grossSemiMonthlyRate = grossSemiMonthlyRate; }
    public String getHourlyRate() { return hourlyRate; }
    public void setHourlyRate(String hourlyRate) { this.hourlyRate = hourlyRate; }

    // Abstract method to compute salary
    public abstract void computeSalary();
}