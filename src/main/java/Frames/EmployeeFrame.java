package Frames;

import org.mapua.Employees;
import org.mapua.MandatoryTaxContribution;
import org.mapua.Wage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class EmployeeFrame extends JFrame {
    private String employeeId;
    private JTextField idField;
    private JTextField lastNameField;
    private JTextField firstNameField;
    private JTextField birthdayField;
    private JTextArea addressField;
    private JTextField phoneNumberField;
    private JTextField sssNumberField;
    private JTextField philhealthNumberField;
    private JTextField tinNumberField; 
    private JTextField pagibigNumberField; 
    private JTextField statusField;
    private JTextField positionField;
    private JTextField supervisorField;
    private JTextField basicSalaryField;
    private JTextField riceSubsidyField;
    private JTextField phoneAllowanceField;
    private JTextField clothingAllowanceField;
    private JTextField grossSemiMonthlyRateField;
    private JTextField hourlyRateField;
    private JButton saveButton;
    private JButton clearBackButton;
    private boolean isUpdate;
    private JTabbedPane tabbedPane;
    private JLabel hoursWorkedLabel;
    private JTextField hoursWorkedField;
    private JLabel withHoldingTaxDeductionLabel;
    private JTextField withHoldingTaxDeductionField;
    private JLabel sssDeductionLabel;
    private JTextField sssDeductionField;
    private JLabel netWageLabel;
    private JTextField netWageField;
    private JLabel pagibigDeductionLabel;
    private JTextField pagibigDeductionField;
    private JLabel weeklyWageLabel;
    private JTextField weeklyWageField;
    private JButton computeWageButton;
    private JButton resetWageButton;
    private JTextField philHealthDeductionField;
    private JTextField netWageWithAllowanceField;
    private static final DecimalFormat df = new DecimalFormat("#,###.00");

    public EmployeeFrame() {
        setTitle("Employee Details");
        setSize(700, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        tabbedPane = new JTabbedPane();
        tabbedPane.add("Profile",createProfilePanel());
 
       getContentPane().add(tabbedPane);
    }


    private JPanel createWagePanel() {
        JPanel bigPanel = new JPanel();
        bigPanel.setLayout(new GridLayout(1, 2, 5, 5));
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(10, 2, 5, 5));
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new GridLayout(10, 2, 5, 5));

        hoursWorkedLabel = new JLabel(" Hours Worked Per Week:");
        hoursWorkedField = new JTextField();
        weeklyWageLabel = new JLabel(" Weekly Gross Wage:");
        weeklyWageField = new JTextField();
        withHoldingTaxDeductionLabel = new JLabel(" WithHolding Tax:");
        withHoldingTaxDeductionField = new JTextField();
        withHoldingTaxDeductionField.setEditable(false);
        withHoldingTaxDeductionField.setBackground(Color.white);
        sssDeductionLabel = new JLabel(" SSS Contribution:");
        sssDeductionField = new JTextField();
        sssDeductionField.setEditable(false);
        sssDeductionField.setBackground(Color.white);
        pagibigDeductionLabel = new JLabel(" Pag-ibig Contribution:");
        pagibigDeductionField = new JTextField();
        pagibigDeductionField.setEditable(false);
        pagibigDeductionField.setBackground(Color.white);
        JLabel philHealthDeductionLabel = new JLabel(" PhilHealth Contribution");
        philHealthDeductionField = new JTextField();
        philHealthDeductionField.setEditable(false);
        philHealthDeductionField.setBackground(Color.white);
        netWageLabel = new JLabel(" Net Wage:");
        netWageField = new JTextField();
        netWageField.setEditable(false);
        netWageField.setBackground(Color.white);
        netWageWithAllowanceField = new JTextField();
        netWageWithAllowanceField.setEditable(false);
        netWageWithAllowanceField.setBackground(Color.white);

        Employees employee = new Employees().getEmployeeById(employeeId);
        JTextField basicSalaryField = new JTextField(df.format(Double.parseDouble(
                employee.getBasicSalary().replace(",", "")
        )));
        basicSalaryField.setEditable(false);
        basicSalaryField.setBackground(Color.WHITE);
        leftPanel.add(new JLabel(" Basic Salary:"));
        leftPanel.add(basicSalaryField);
        JTextField riceSubsidyField = new JTextField(df.format(
                Double.parseDouble(employee.getRiceSubsidy().replace(",", ""))
        ));
        riceSubsidyField.setEditable(false);
        riceSubsidyField.setBackground(Color.WHITE);
        leftPanel.add(new JLabel(" Rice Subsidy:"));
        leftPanel.add(riceSubsidyField);
        JTextField phoneAllowanceField = new JTextField(df.format(
                Double.parseDouble(employee.getPhoneAllowance().replace(",", ""))
        ));
        phoneAllowanceField.setEditable(false);
        phoneAllowanceField.setBackground(Color.WHITE);
        leftPanel.add(new JLabel(" Phone Allowance:"));
        leftPanel.add(phoneAllowanceField);
        JTextField clothingAllowanceField = new JTextField(df.format(
                Double.parseDouble(employee.getClothingAllowance().replace(",", ""))
        ));
        clothingAllowanceField.setEditable(false);
        clothingAllowanceField.setBackground(Color.WHITE);
        leftPanel.add(new JLabel(" Clothing Allowance:"));
        leftPanel.add(clothingAllowanceField);
        JTextField perCutOffRateField = new JTextField(df.format(
                Double.parseDouble(employee.getGrossSemiMonthlyRate().replace(",", ""))
        ));
        perCutOffRateField.setEditable(false);
        perCutOffRateField.setBackground(Color.WHITE);
        leftPanel.add(new JLabel(" Per Cut-Off Rate:"));
        leftPanel.add(perCutOffRateField);
        JTextField hourlyRateField = new JTextField(df.format(
                Double.parseDouble(employee.getHourlyRate().replace(",", ""))
        ));
        hourlyRateField.setEditable(false);
        hourlyRateField.setBackground(Color.WHITE);
        leftPanel.add(new JLabel(" Hourly Rate:"));
        leftPanel.add(hourlyRateField);
        leftPanel.add(new JLabel(" Gross Wage:"));
        JTextField grossWageField = getjTextField(employee);
        leftPanel.add(grossWageField);
        leftPanel.add(new JLabel());
        leftPanel.add(new JLabel());

        rightPanel.add(hoursWorkedLabel);
        rightPanel.add(hoursWorkedField);
        rightPanel.add(weeklyWageLabel);
        rightPanel.add(weeklyWageField);
        rightPanel.add(philHealthDeductionLabel);
        rightPanel.add(philHealthDeductionField);
        rightPanel.add(withHoldingTaxDeductionLabel);
        rightPanel.add(withHoldingTaxDeductionField);
        rightPanel.add(sssDeductionLabel);
        rightPanel.add(sssDeductionField);
        rightPanel.add(pagibigDeductionLabel);
        rightPanel.add(pagibigDeductionField);
        rightPanel.add(netWageLabel);
        rightPanel.add(netWageField);
        rightPanel.add(new JLabel(" Net Wage with Allowances:"));
        rightPanel.add(netWageWithAllowanceField);

        computeWageButton = new JButton("Compute");
        resetWageButton = new JButton("Reset");
        resetWageButton.setEnabled(false);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(computeWageButton);
        buttonPanel.add(resetWageButton);
        rightPanel.add(new JLabel());
        rightPanel.add(buttonPanel);

        computeWageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (hoursWorkedField.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter hours worked per week.");
                    return;
                }

                hoursWorkedField.setEditable(false);
                hoursWorkedField.setBackground(Color.white);

                double hoursWorked = Double.parseDouble(hoursWorkedField.getText().trim());
                double hourlyRate = Double.parseDouble(employee.getHourlyRate().replace(",", ""));
                double grossWeeklySalary = hoursWorked * hourlyRate;
                weeklyWageField.setText(df.format(grossWeeklySalary));

                double basicSalary = Double.parseDouble(basicSalaryField.getText().replace(",", ""));
                double withHoldingTax = MandatoryTaxContribution.computeWithHoldingTax(basicSalary);
                double sssContribution = MandatoryTaxContribution.getSSSContributionBySalary(basicSalary);
                double philHealthContribution = MandatoryTaxContribution.getPhilHealthContributionBySalary(basicSalary);
                double pagibigContribution = MandatoryTaxContribution.getPagibigContributionBySalary(basicSalary);

                withHoldingTaxDeductionField.setText(df.format(withHoldingTax));
                sssDeductionField.setText(df.format(sssContribution));
                philHealthDeductionField.setText(df.format(philHealthContribution));
                pagibigDeductionField.setText(df.format(pagibigContribution));

                double netWage = grossWeeklySalary - (withHoldingTax + sssContribution + philHealthContribution + pagibigContribution);
                netWageField.setText(df.format(netWage));

                double riceSubsidy = Double.parseDouble(riceSubsidyField.getText().replace(",", ""));
                double phoneAllowance = Double.parseDouble(phoneAllowanceField.getText().replace(",", ""));
                double clothingAllowance = Double.parseDouble(clothingAllowanceField.getText().replace(",", ""));
                double netWageWithAllowance = netWage + riceSubsidy + phoneAllowance + clothingAllowance;
                netWageWithAllowanceField.setText(df.format(netWageWithAllowance));

                resetWageButton.setEnabled(true);
                computeWageButton.setEnabled(false);
            }
        });

        resetWageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hoursWorkedField.setText("");
                hoursWorkedField.setEditable(true);
                weeklyWageField.setText("");
                weeklyWageField.setEditable(false);
                weeklyWageField.setBackground(Color.white);
                resetWageButton.setEnabled(false);
                computeWageButton.setEnabled(true);
            }
        });

        bigPanel.add(leftPanel);
        bigPanel.add(rightPanel);
        add(bigPanel, BorderLayout.CENTER);
        return bigPanel;
    }

    private static JTextField getjTextField(Employees employee) {
        JTextField grossWageField = new JTextField();
        grossWageField.setEditable(false);
        grossWageField.setBackground(Color.white);
        BigDecimal totalGross =
                new BigDecimal(employee.getBasicSalary().replace(",",""))
                    .add(new BigDecimal(employee.getRiceSubsidy().replace(",","")))
                    .add(new BigDecimal(employee.getPhoneAllowance().replace(",","")))
                    .add(new BigDecimal(employee.getClothingAllowance().replace(",","")));
        grossWageField.setText(df.format(totalGross));
        return grossWageField;
    }

    private JPanel createProfilePanel(){
        JPanel bigPanel = new JPanel();
        bigPanel.setLayout(new GridLayout(1, 2, 5, 5));
        JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayout(10, 2, 5, 5));
        JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayout(10, 2, 5, 5));

        JLabel idLabel = new JLabel("  Employee ID:");
        idField = new JTextField(new Employees().generateId());
        isUpdate = false;
        idField.setEnabled(false);
        JLabel lastNameLabel = new JLabel("  Last Name:");
        lastNameField = new JTextField();
        JLabel firstNameLabel = new JLabel("  First Name:");
        firstNameField = new JTextField();
        JLabel birthdayLabel = new JLabel("  Birthday:");
        birthdayField = new JTextField();
        JLabel addressLabel = new JLabel("  Address:");
        addressField = new JTextArea();
        addressField.setLineWrap(true);
        addressField.setBorder(BorderFactory.createEtchedBorder());
        addressField.setAutoscrolls(true);
        JLabel phoneNumberLabel = new JLabel("  Phone Number:");
        phoneNumberField = new JTextField();
        JLabel sssNumberLabel = new JLabel("  SSS Number:");
        sssNumberField = new JTextField();
        JLabel philhealthNumberLabel = new JLabel("  Philhealth Number:");
        philhealthNumberField = new JTextField();
        JLabel tinNumberLabel = new JLabel("  Tax Income Number:");
        tinNumberField = new JTextField();
        JLabel housingNumberLabel = new JLabel("  Pagibig Number:");
        pagibigNumberField = new JTextField();
        JLabel statusLabel = new JLabel(" Status:");
        statusField = new JTextField();
        JLabel positionLabel = new JLabel(" Position:");
        positionField = new JTextField();
        JLabel supervisorLabel = new JLabel(" Immediate Supervisor:");
        supervisorField = new JTextField();
        JLabel basicSalaryLabel = new JLabel(" Basic Salary:");
        basicSalaryField = new JTextField();
        JLabel riceSubsidyLabel = new JLabel(" Rice Subsidy:");
        riceSubsidyField = new JTextField();
        JLabel phoneAllowanceLabel = new JLabel(" Phone Allowance:");
        phoneAllowanceField = new JTextField();
        JLabel clothingAllowanceLabel = new JLabel(" Clothing Allowance:");
        clothingAllowanceField = new JTextField();
        JLabel grossSemiMonthlyRateLabel = new JLabel(" Gross Semi-monthly Rate:");
        grossSemiMonthlyRateField = new JTextField();
        JLabel hourlyRateLabel = new JLabel(" Hourly Rate:");
        hourlyRateField = new JTextField();

        panel1.add(idLabel);
        panel1.add(idField);
        panel1.add(lastNameLabel);
        panel1.add(lastNameField);
        panel1.add(firstNameLabel);
        panel1.add(firstNameField);
        panel1.add(birthdayLabel);
        panel1.add(birthdayField);
        panel1.add(addressLabel);
        panel1.add(addressField);
        panel1.add(phoneNumberLabel);
        panel1.add(phoneNumberField);
        panel1.add(sssNumberLabel);
        panel1.add(sssNumberField);
        panel1.add(philhealthNumberLabel);
        panel1.add(philhealthNumberField);
        panel1.add(tinNumberLabel);
        panel1.add(tinNumberField);
        panel1.add(housingNumberLabel);
        panel1.add(pagibigNumberField);
        panel2.add(statusLabel);
        panel2.add(statusField);
        panel2.add(positionLabel);
        panel2.add(positionField);
        panel2.add(supervisorLabel);
        panel2.add(supervisorField);
        panel2.add(basicSalaryLabel);
        panel2.add(basicSalaryField);
        panel2.add(riceSubsidyLabel);
        panel2.add(riceSubsidyField);
        panel2.add(phoneAllowanceLabel);
        panel2.add(phoneAllowanceField);
        panel2.add(clothingAllowanceLabel);
        panel2.add(clothingAllowanceField);
        panel2.add(grossSemiMonthlyRateLabel);
        panel2.add(grossSemiMonthlyRateField);
        panel2.add(hourlyRateLabel);
        panel2.add(hourlyRateField);
        bigPanel.add(panel1);
        bigPanel.add(panel2);

        saveButton = new JButton("Save");
        clearBackButton = new JButton("Back");

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveEmployee();
                dispose();
                new MainFrame().setVisible(true);
                JOptionPane.showMessageDialog(null, "Record is saved!");
            }
        });

        clearBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new MainFrame().setVisible(true);
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(saveButton);
        buttonPanel.add(clearBackButton);
        panel2.add(new JLabel());
        panel2.add(buttonPanel);
        add(bigPanel, BorderLayout.CENTER);
        return bigPanel;
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                EmployeeFrame frame = new EmployeeFrame();
                frame.setVisible(true);
            }
        });
    }

    public void updateEmployee(String employeeId) {
        if(!employeeId.trim().isEmpty()){
            this.employeeId = employeeId;
            tabbedPane = new JTabbedPane();
            tabbedPane.add("Profile",createProfilePanel());
            tabbedPane.add("Wage", createWagePanel());
            getContentPane().add(tabbedPane);
            isUpdate = true;
            fillForm();
        }
    }

    private void fillForm(){
        Employees employees = new Employees().getEmployeeById(this.employeeId);
        idField.setText(employees.getEmployeeNumber());
        lastNameField.setText(employees.getLastName());
        firstNameField.setText(employees.getFirstName());
        birthdayField.setText(employees.getBirthdate());
        addressField.setText(employees.getAddress());
        phoneNumberField.setText(employees.getPhoneNumber());
        sssNumberField.setText(employees.getSss());
        philhealthNumberField.setText(employees.getPhilHealth());
        tinNumberField.setText(employees.getTin());
        pagibigNumberField.setText(employees.getPagibig());
        statusField.setText(employees.getStatus());
        positionField.setText(employees.getPosition());
        supervisorField.setText(employees.getImmediateSupervisor());
        basicSalaryField.setText(employees.getBasicSalary());
        riceSubsidyField.setText(employees.getRiceSubsidy());
        phoneAllowanceField.setText(employees.getPhoneAllowance());
        clothingAllowanceField.setText(employees.getClothingAllowance());
        grossSemiMonthlyRateField.setText(employees.getGrossSemiMonthlyRate());
        hourlyRateField.setText(employees.getHourlyRate());
    }

    private void saveEmployee() {
        Employees employee = new Employees();
        employee.setEmployeeNumber(idField.getText());
        employee.setLastName(lastNameField.getText());
        employee.setFirstName(firstNameField.getText());
        employee.setBirthdate(birthdayField.getText());
        employee.setAddress(addressField.getText());
        employee.setPhoneNumber(phoneNumberField.getText());
        employee.setSss(sssNumberField.getText());
        employee.setPhilHealth(philhealthNumberField.getText());
        employee.setTin(tinNumberField.getText());
        employee.setPagibig(pagibigNumberField.getText());
        employee.setStatus(statusField.getText());
        employee.setPosition(positionField.getText());
        employee.setImmediateSupervisor(supervisorField.getText());
        employee.setBasicSalary(basicSalaryField.getText());
        employee.setRiceSubsidy(riceSubsidyField.getText());
        employee.setPhoneAllowance(phoneAllowanceField.getText());
        employee.setClothingAllowance(clothingAllowanceField.getText());employee.setGrossSemiMonthlyRate(grossSemiMonthlyRateField.getText());
        employee.setHourlyRate(hourlyRateField.getText());
        if(!isUpdate){
            employee.addEmployee();
        }else{
            employee.updateEmployee();
        }
    }
}


