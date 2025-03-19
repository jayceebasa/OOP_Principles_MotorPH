package Frames;

import org.mapua.*;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.text.DecimalFormat;

public class EmployeeUIFrame extends JFrame {
    private JTextField hoursWorkedField, weeklyWageField, withHoldingTaxDeductionField, sssDeductionField, pagibigDeductionField, philHealthDeductionField, netWageField, netWageWithAllowanceField;
    private JLabel hoursWorkedLabel, weeklyWageLabel, withHoldingTaxDeductionLabel, sssDeductionLabel, pagibigDeductionLabel, netWageLabel;
    private JButton computeWageButton, resetWageButton;
    private Employees employees;
    private String employeeId;
    private DecimalFormat df = new DecimalFormat("#,###.00");


    public EmployeeUIFrame(String employeeId) {
        this.employeeId = employeeId;
        EmployeeRepository employeeRepository = new EmployeeRepository();
        this.employees = new Employees(employeeRepository);
        setTitle("Employee UI");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Retrieve the employee object
        Employee employee = employees.getEmployeeById(employeeId);
        if (employee == null) {
            JOptionPane.showMessageDialog(this, "Employee not found!");
            dispose();
            return;
        }

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.add("Profile", createProfilePanel(employee));
        tabbedPane.add("Wage", createWagePanel(employee)); // Pass the employee object
        tabbedPane.add("Leave Request", new LeaveRequestPanel(employeeId)); // Add LeaveRequestPanel
        add(tabbedPane, BorderLayout.CENTER);

        fillForm();
        styleComponents();
    }

    private JPanel createWagePanel(Employee employee) {
        JPanel bigPanel = new JPanel();
        bigPanel.setLayout(new GridLayout(1, 2, 5, 5));
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(10, 2, 5, 5));
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new GridLayout(10, 2, 5, 5));

        // Initialize and set up components using the employee object
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

    private static JTextField getjTextField(Employee employee) {
        JTextField grossWageField = new JTextField();
        grossWageField.setEditable(false);
        grossWageField.setBackground(Color.white);
        BigDecimal totalGross = new BigDecimal(employee.getBasicSalary().replace(",", ""))
                .add(new BigDecimal(employee.getRiceSubsidy().replace(",", "")))
                .add(new BigDecimal(employee.getPhoneAllowance().replace(",", "")))
                .add(new BigDecimal(employee.getClothingAllowance().replace(",", "")));
        grossWageField.setText(new DecimalFormat("#,###.00").format(totalGross));
        return grossWageField;
    }

    private JPanel createProfilePanel(Employee employee) {
        JPanel bigPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        JPanel panel1 = new JPanel(new GridLayout(10, 2, 5, 5));
        JPanel panel2 = new JPanel(new GridLayout(10, 2, 5, 5));

        JLabel idLabel = new JLabel("  Employee ID:");
        JTextField idField = new JTextField(employee.getEmployeeNumber());
        idField.setEnabled(false);
        JLabel lastNameLabel = new JLabel("  Last Name:");
        JTextField lastNameField = new JTextField(employee.getLastName());
        JLabel firstNameLabel = new JLabel("  First Name:");
        JTextField firstNameField = new JTextField(employee.getFirstName());
        JLabel birthdayLabel = new JLabel("  Birthday:");
        JTextField birthdayField = new JTextField(employee.getBirthdate());
        JLabel addressLabel = new JLabel("  Address:");
        JTextArea addressField = new JTextArea(employee.getAddress());
        addressField.setLineWrap(true);
        addressField.setBorder(BorderFactory.createEtchedBorder());
        addressField.setAutoscrolls(true);
        JLabel phoneNumberLabel = new JLabel("  Phone Number:");
        JTextField phoneNumberField = new JTextField(employee.getPhoneNumber());
        JLabel sssNumberLabel = new JLabel("  SSS Number:");
        JTextField sssNumberField = new JTextField(employee.getSss());
        JLabel philhealthNumberLabel = new JLabel("  Philhealth Number:");
        JTextField philhealthNumberField = new JTextField(employee.getPhilHealth());
        JLabel tinNumberLabel = new JLabel("  Tax Income Number:");
        JTextField tinNumberField = new JTextField(employee.getTin());
        JLabel housingNumberLabel = new JLabel("  Pagibig Number:");
        JTextField pagibigNumberField = new JTextField(employee.getPagibig());
        JLabel statusLabel = new JLabel(" Status:");
        JTextField statusField = new JTextField(employee.getStatus());
        JLabel positionLabel = new JLabel(" Position:");
        JTextField positionField = new JTextField(employee.getPosition());
        JLabel supervisorLabel = new JLabel(" Immediate Supervisor:");
        JTextField supervisorField = new JTextField(employee.getImmediateSupervisor());
        JLabel basicSalaryLabel = new JLabel(" Basic Salary:");
        JTextField basicSalaryField = new JTextField(employee.getBasicSalary());
        JLabel riceSubsidyLabel = new JLabel(" Rice Subsidy:");
        JTextField riceSubsidyField = new JTextField(employee.getRiceSubsidy());
        JLabel phoneAllowanceLabel = new JLabel(" Phone Allowance:");
        JTextField phoneAllowanceField = new JTextField(employee.getPhoneAllowance());
        JLabel clothingAllowanceLabel = new JLabel(" Clothing Allowance:");
        JTextField clothingAllowanceField = new JTextField(employee.getClothingAllowance());
        JLabel grossSemiMonthlyRateLabel = new JLabel(" Gross Semi-monthly Rate:");
        JTextField grossSemiMonthlyRateField = new JTextField(employee.getGrossSemiMonthlyRate());
        JLabel hourlyRateLabel = new JLabel(" Hourly Rate:");
        JTextField hourlyRateField = new JTextField(employee.getHourlyRate());

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

        JButton saveButton = new JButton("Save");
        JButton clearBackButton = new JButton("Back");

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

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.add(saveButton);
        buttonPanel.add(clearBackButton);
        panel2.add(new JLabel());
        panel2.add(buttonPanel);
        return bigPanel;
    }

    private void fillForm() {
        Employee employee = employees.getEmployeeById(this.employeeId);
        // Fill the form with employee data
    }

    private void saveEmployee() {
        Employee employee = new RegularEmployee();
        // Save employee data
    }

    private void styleComponents() {
        // Style components
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new EmployeeUIFrame("1").setVisible(true);
            }
        });
    }
}