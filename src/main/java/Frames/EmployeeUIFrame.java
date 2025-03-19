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

    private final Color primaryColor = new Color(25, 118, 210);
    private final Color successColor = new Color(76, 175, 80);
    private final Color dangerColor = new Color(211, 47, 47);
    private final Color grayColor = new Color(158, 158, 158);
    private final Color backgroundColor = Color.WHITE;

    public EmployeeUIFrame(String employeeId) {
        this.employeeId = employeeId;
        EmployeeRepository employeeRepository = new EmployeeRepository();
        this.employees = new Employees(employeeRepository);
        setTitle("Employee UI");
        setSize(1300, 900);
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
        tabbedPane.add("Wage", createWagePanel(employee));
        tabbedPane.add("Request Leave", new LeaveRequestPanel(employeeId));
        tabbedPane.add("My Leaves", new MyLeavesPanel(employeeId)); // Add the new panel
        add(tabbedPane, BorderLayout.CENTER);

        fillForm();
        styleComponents();
    }

    // Add this to EmployeeUIFrame class
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
        idField.setEditable(false);
        JLabel lastNameLabel = new JLabel("  Last Name:");
        JTextField lastNameField = new JTextField(employee.getLastName());
        lastNameField.setEditable(false);
        JLabel firstNameLabel = new JLabel("  First Name:");
        JTextField firstNameField = new JTextField(employee.getFirstName());
        firstNameField.setEditable(false);
        JLabel birthdayLabel = new JLabel("  Birthday:");
        JTextField birthdayField = new JTextField(employee.getBirthdate());
        birthdayField.setEditable(false);
        JLabel addressLabel = new JLabel("  Address:");
        JTextArea addressField = new JTextArea(employee.getAddress());
        addressField.setEditable(false);
        addressField.setLineWrap(true);
        addressField.setBorder(BorderFactory.createEtchedBorder());
        addressField.setAutoscrolls(true);
        JLabel phoneNumberLabel = new JLabel("  Phone Number:");
        JTextField phoneNumberField = new JTextField(employee.getPhoneNumber());
        phoneNumberField.setEditable(false);
        JLabel sssNumberLabel = new JLabel("  SSS Number:");
        JTextField sssNumberField = new JTextField(employee.getSss());
        sssNumberField.setEditable(false);
        JLabel philhealthNumberLabel = new JLabel("  Philhealth Number:");
        JTextField philhealthNumberField = new JTextField(employee.getPhilHealth());
        philhealthNumberField.setEditable(false);
        JLabel tinNumberLabel = new JLabel("  Tax Income Number:");
        JTextField tinNumberField = new JTextField(employee.getTin());
        tinNumberField.setEditable(false);
        JLabel housingNumberLabel = new JLabel("  Pagibig Number:");
        JTextField pagibigNumberField = new JTextField(employee.getPagibig());
        pagibigNumberField.setEditable(false);
        JLabel statusLabel = new JLabel(" Status:");
        JTextField statusField = new JTextField(employee.getStatus());
        statusField.setEditable(false);
        JLabel positionLabel = new JLabel(" Position:");
        JTextField positionField = new JTextField(employee.getPosition());
        positionField.setEditable(false);
        JLabel supervisorLabel = new JLabel(" Immediate Supervisor:");
        JTextField supervisorField = new JTextField(employee.getImmediateSupervisor());
        supervisorField.setEditable(false);
        JLabel basicSalaryLabel = new JLabel(" Basic Salary:");
        JTextField basicSalaryField = new JTextField(employee.getBasicSalary());
        basicSalaryField.setEditable(false);
        JLabel riceSubsidyLabel = new JLabel(" Rice Subsidy:");
        JTextField riceSubsidyField = new JTextField(employee.getRiceSubsidy());
        riceSubsidyField.setEditable(false);
        JLabel phoneAllowanceLabel = new JLabel(" Phone Allowance:");
        JTextField phoneAllowanceField = new JTextField(employee.getPhoneAllowance());
        phoneAllowanceField.setEditable(false);
        JLabel clothingAllowanceLabel = new JLabel(" Clothing Allowance:");
        JTextField clothingAllowanceField = new JTextField(employee.getClothingAllowance());
        clothingAllowanceField.setEditable(false);
        JLabel grossSemiMonthlyRateLabel = new JLabel(" Gross Semi-monthly Rate:");
        JTextField grossSemiMonthlyRateField = new JTextField(employee.getGrossSemiMonthlyRate());
        grossSemiMonthlyRateField.setEditable(false);
        JLabel hourlyRateLabel = new JLabel(" Hourly Rate:");
        JTextField hourlyRateField = new JTextField(employee.getHourlyRate());
        hourlyRateField.setEditable(false);

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


        JButton clearBackButton = new JButton("Logout");


        clearBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new LoginFrame().setVisible(true);
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.add(clearBackButton);
        panel2.add(new JLabel());
        panel2.add(buttonPanel);
        return bigPanel;
    }

    private void fillForm() {
        Employee employee = employees.getEmployeeById(this.employeeId);
        // Fill the form with employee data
    }

    private void styleComponents() {
        // Style the frame
        getContentPane().setBackground(backgroundColor);

        // Style the tabbed pane (if it exists)
        Component[] components = getContentPane().getComponents();
        for (Component component : components) {
            if (component instanceof JTabbedPane) {
                JTabbedPane tabbedPane = (JTabbedPane) component;
                tabbedPane.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                tabbedPane.setBackground(backgroundColor);

                // Style each tab's content
                for (int i = 0; i < tabbedPane.getTabCount(); i++) {
                    Component tabComponent = tabbedPane.getComponentAt(i);
                    if (tabComponent instanceof JPanel) {
                        stylePanel((JPanel) tabComponent);
                    }
                }
            }
        }

        // Style buttons
        if (computeWageButton != null) {
            styleButton(computeWageButton, successColor, Color.WHITE);
        }
        if (resetWageButton != null) {
            styleButton(resetWageButton, dangerColor, Color.WHITE);
        }

        // Find and style Save and Logout buttons
        findAndStyleButtons();
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
        button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

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

    private void findAndStyleButtons() {
        // Recursively find and style all buttons in the UI
        findButtonsInContainer(getContentPane());
    }

    private void findButtonsInContainer(Container container) {
        Component[] components = container.getComponents();
        for (Component component : components) {
            if (component instanceof JButton) {
                JButton button = (JButton) component;
                if (button != computeWageButton && button != resetWageButton) {
                    if (button.getText().equals("Save")) {
                        styleButton(button, primaryColor, Color.WHITE);
                    } else if (button.getText().equals("Logout")) {
                        styleButton(button, grayColor, Color.WHITE);
                    }
                }
            } else if (component instanceof Container) {
                findButtonsInContainer((Container) component);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new EmployeeUIFrame("1").setVisible(true);
            }
        });
    }
}