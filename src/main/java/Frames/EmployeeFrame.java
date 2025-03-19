package Frames;

import org.mapua.Employee;
import org.mapua.Employees;
import org.mapua.EmployeeRepository;
import org.mapua.MandatoryTaxContribution;
import org.mapua.RegularEmployee;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import Frames.components.ModernTextField;

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

    private EmployeeRepository employeeRepository;
    private Employees employees;

    private final Color primaryColor = new Color(25, 118, 210);
    private final Color successColor = new Color(76, 175, 80);
    private final Color dangerColor = new Color(211, 47, 47);
    private final Color grayColor = new Color(158, 158, 158);
    private final Color backgroundColor = Color.WHITE;


    public EmployeeFrame() {
        // Initialize EmployeeRepository and Employees with dependency injection
        employeeRepository = new EmployeeRepository();
        employees = new Employees(employeeRepository);

        setTitle("Employee Details");
        setSize(1300, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        tabbedPane = new JTabbedPane();
        tabbedPane.add("Profile", createProfilePanel());

        // Set the next available ID for new employees
        idField.setText(employees.getNextEmployeeId());
        idField.setEnabled(false);  // Disable editing of the ID field
        isUpdate = false;

        getContentPane().add(tabbedPane);

        // Apply styling
        styleComponents();
    }

    private void styleComponents() {
        // Style the frame
        getContentPane().setBackground(backgroundColor);

        // Style the tabbed pane
        tabbedPane.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tabbedPane.setBackground(backgroundColor);

        // Style buttons
        styleButton(saveButton, primaryColor, Color.WHITE);
        styleButton(clearBackButton, grayColor, Color.WHITE);
        if (computeWageButton != null) {
            styleButton(computeWageButton, successColor, Color.WHITE);
            styleButton(resetWageButton, dangerColor, Color.WHITE);
        }

        // Style text fields and labels
        styleFormComponents();
    }

    private boolean validateHoursWorked() {
        String hours = hoursWorkedField.getText().trim();
        if (hours.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter hours worked per week.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try {
            double hoursWorked = Double.parseDouble(hours);
            if (hoursWorked <= 0) {
                JOptionPane.showMessageDialog(this, "Hours worked must be greater than zero.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            if (hoursWorked > 168) { // Max hours in a week
                JOptionPane.showMessageDialog(this, "Hours worked cannot exceed 168 hours per week.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            return true;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Hours worked must be a valid number.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    private void styleFormComponents() {
        Component[] components = getContentPane().getComponents();
        for (Component component : components) {
            if (component instanceof JPanel) {
                JPanel panel = (JPanel) component;
                panel.setBackground(backgroundColor);
                stylePanel(panel);
            }
        }
    }

    private void stylePanel(JPanel panel) {
        panel.setBackground(backgroundColor);
        Component[] components = panel.getComponents();
        for (Component component : components) {
            if (component instanceof JLabel) {
                JLabel label = (JLabel) component;
                label.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            } else if (component instanceof JTextField) {
                JTextField textField = (JTextField) component;
                textField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
                textField.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(200, 200, 200)),
                        BorderFactory.createEmptyBorder(5, 8, 5, 8)));
            } else if (component instanceof JTextArea) {
                JTextArea textArea = (JTextArea) component;
                textArea.setFont(new Font("Segoe UI", Font.PLAIN, 13));
                textArea.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(200, 200, 200)),
                        BorderFactory.createEmptyBorder(5, 8, 5, 8)));
            } else if (component instanceof JPanel) {
                stylePanel((JPanel) component);
            } else if (component instanceof JTabbedPane) {
                JTabbedPane innerPane = (JTabbedPane) component;
                innerPane.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                for (int i = 0; i < innerPane.getTabCount(); i++) {
                    Component tabComponent = innerPane.getComponentAt(i);
                    if (tabComponent instanceof JPanel) {
                        stylePanel((JPanel) tabComponent);
                    }
                }
            }
        }
    }

    private void styleButton(JButton button, Color bgColor, Color fgColor) {
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));

        // Adjust button sizing to prevent text distortion
        int textWidth = button.getFontMetrics(button.getFont()).stringWidth(button.getText());
        int minWidth = Math.max(100, textWidth + 30); // Ensure minimum width with padding
        button.setPreferredSize(new Dimension(minWidth, 35));

        // Use less padding to prevent text cutoff
        button.setBorder(new javax.swing.border.EmptyBorder(5, 10, 5, 10));

        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setBackground(darken(bgColor, 0.1f));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBackground(bgColor);
            }
        });
    }

    private Color darken(Color color, float fraction) {
        int red = Math.max(0, Math.round(color.getRed() * (1 - fraction)));
        int green = Math.max(0, Math.round(color.getGreen() * (1 - fraction)));
        int blue = Math.max(0, Math.round(color.getBlue() * (1 - fraction)));
        return new Color(red, green, blue);
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
        weeklyWageField.setEditable(false);
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
        netWageLabel = new JLabel(" Monthly Net Wage:");
        netWageField = new JTextField();
        netWageField.setEditable(false);
        netWageField.setBackground(Color.white);
        netWageWithAllowanceField = new JTextField();
        netWageWithAllowanceField.setEditable(false);
        netWageWithAllowanceField.setBackground(Color.white);

        Employee employee = employees.getEmployeeById(employeeId);
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
        rightPanel.add(new JLabel(" Monthly Net Wage with Allowances:"));
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
                if (!validateHoursWorked()) {
                    return;
                }

                hoursWorkedField.setEditable(false);
                hoursWorkedField.setBackground(Color.white);

                double hoursWorked = Double.parseDouble(hoursWorkedField.getText().trim());
                double hourlyRate = Double.parseDouble(employee.getHourlyRate().replace(",", ""));
                double grossWeeklySalary = hoursWorked * hourlyRate;
                weeklyWageField.setText(df.format(grossWeeklySalary));

                // Calculate monthly gross salary (4 weeks per month)
                double grossMonthlySalary = grossWeeklySalary * 4;

                double basicSalary = Double.parseDouble(basicSalaryField.getText().replace(",", ""));
                double withHoldingTax = MandatoryTaxContribution.computeWithHoldingTax(basicSalary);
                double sssContribution = MandatoryTaxContribution.getSSSContributionBySalary(basicSalary);
                double philHealthContribution = MandatoryTaxContribution.getPhilHealthContributionBySalary(basicSalary);
                double pagibigContribution = MandatoryTaxContribution.getPagibigContributionBySalary(basicSalary);

                withHoldingTaxDeductionField.setText(df.format(withHoldingTax));
                sssDeductionField.setText(df.format(sssContribution));
                philHealthDeductionField.setText(df.format(philHealthContribution));
                pagibigDeductionField.setText(df.format(pagibigContribution));

                // Calculate monthly net wage instead of weekly
                double monthlyNetWage = grossMonthlySalary - (withHoldingTax + sssContribution + philHealthContribution + pagibigContribution);
                netWageField.setText(df.format(monthlyNetWage));

                double riceSubsidy = Double.parseDouble(riceSubsidyField.getText().replace(",", ""));
                double phoneAllowance = Double.parseDouble(phoneAllowanceField.getText().replace(",", ""));
                double clothingAllowance = Double.parseDouble(clothingAllowanceField.getText().replace(",", ""));
                double monthlyNetWageWithAllowance = monthlyNetWage + riceSubsidy + phoneAllowance + clothingAllowance;
                netWageWithAllowanceField.setText(df.format(monthlyNetWageWithAllowance));

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
        BigDecimal totalGross =
                new BigDecimal(employee.getBasicSalary().replace(",", ""))
                        .add(new BigDecimal(employee.getRiceSubsidy().replace(",", "")))
                        .add(new BigDecimal(employee.getPhoneAllowance().replace(",", "")))
                        .add(new BigDecimal(employee.getClothingAllowance().replace(",", "")));
        grossWageField.setText(df.format(totalGross));
        return grossWageField;
    }

    private JPanel createProfilePanel() {
        JPanel bigPanel = new JPanel();
        bigPanel.setLayout(new GridLayout(1, 2, 5, 5));
        JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayout(10, 2, 5, 5));
        JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayout(10, 2, 5, 5));
        // Create button panel with FlowLayout to ensure buttons are visible

        JLabel idLabel = new JLabel("  Employee ID:");
        idField = new JTextField(employeeId);
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


        // Initialize buttons
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

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
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
        if (!employeeId.trim().isEmpty()) {
            this.employeeId = employeeId;
            tabbedPane = new JTabbedPane();
            tabbedPane.add("Profile", createProfilePanel());
            tabbedPane.add("Wage", createWagePanel());
            getContentPane().add(tabbedPane);
            isUpdate = true;
            fillForm();
            styleComponents(); // Add styling after filling the form
        }
    }

    private void fillForm() {
        Employee employee = employees.getEmployeeById(this.employeeId);
        idField.setText(employee.getEmployeeNumber());
        lastNameField.setText(employee.getLastName());
        firstNameField.setText(employee.getFirstName());
        birthdayField.setText(employee.getBirthdate());
        addressField.setText(employee.getAddress());
        phoneNumberField.setText(employee.getPhoneNumber());
        sssNumberField.setText(employee.getSss());
        philhealthNumberField.setText(employee.getPhilHealth());
        tinNumberField.setText(employee.getTin());
        pagibigNumberField.setText(employee.getPagibig());
        statusField.setText(employee.getStatus());
        positionField.setText(employee.getPosition());
        supervisorField.setText(employee.getImmediateSupervisor());
        basicSalaryField.setText(employee.getBasicSalary());
        riceSubsidyField.setText(employee.getRiceSubsidy());
        phoneAllowanceField.setText(employee.getPhoneAllowance());
        clothingAllowanceField.setText(employee.getClothingAllowance());
        grossSemiMonthlyRateField.setText(employee.getGrossSemiMonthlyRate());
        hourlyRateField.setText(employee.getHourlyRate());
    }

    private void saveEmployee() {
        // Validation flags
        boolean hasErrors = false;
        StringBuilder errorMessage = new StringBuilder("Please correct the following errors:\n");

        // Required field validation
        if (idField.getText().trim().isEmpty()) {
            errorMessage.append("- Employee ID is required\n");
            hasErrors = true;
        }

        if (lastNameField.getText().trim().isEmpty()) {
            errorMessage.append("- Last Name is required\n");
            hasErrors = true;
        }

        if (firstNameField.getText().trim().isEmpty()) {
            errorMessage.append("- First Name is required\n");
            hasErrors = true;
        }

        // Date format validation for birthdate
        if (!birthdayField.getText().trim().isEmpty()) {
            try {
                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MM/dd/yyyy");
                sdf.setLenient(false);
                sdf.parse(birthdayField.getText());
            } catch (java.text.ParseException e) {
                errorMessage.append("- Birthday must be in MM/DD/YYYY format\n");
                hasErrors = true;
            }
        }

        // Phone number validation
        if (!phoneNumberField.getText().trim().isEmpty()) {
            if (!phoneNumberField.getText().matches("\\d{9}|\\d{3}-\\d{3}-\\d{4}")) {
                errorMessage.append("- Invalid phone number format\n");
                hasErrors = true;
            }
        }

        // Numeric validation for salary fields
        if (!basicSalaryField.getText().trim().isEmpty()) {
            try {
                Double.parseDouble(basicSalaryField.getText().replace(",", ""));
            } catch (NumberFormatException e) {
                errorMessage.append("- Basic salary must be a valid number\n");
                hasErrors = true;
            }
        }

        // Show error message if validation fails
        if (hasErrors) {
            JOptionPane.showMessageDialog(this, errorMessage.toString(), "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Proceed with saving if validation passes
        Employee employee = new RegularEmployee(); // Use RegularEmployee or PartTimeEmployee
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
        employee.setClothingAllowance(clothingAllowanceField.getText());
        employee.setGrossSemiMonthlyRate(grossSemiMonthlyRateField.getText());
        employee.setHourlyRate(hourlyRateField.getText());

        if (!isUpdate) {
            employees.addEmployee(employee);
        } else {
            employees.updateEmployee(employee);
        }
    }
}
