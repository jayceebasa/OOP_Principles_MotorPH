package Frames;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class LeaveRequestPanel extends JPanel {
    private JTextField employeeIdField;
    private JComboBox<String> leaveTypeComboBox;
    private JFormattedTextField startDateField;
    private JFormattedTextField endDateField;
    private JTextArea reasonField;
    private JButton submitButton;
    private JButton resetButton;
    private JLabel daysCountLabel;
    private Color primaryColor = new Color(70, 130, 180);
    private Color errorColor = new Color(255, 102, 102);
    private SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    // Mock leave balances - in a real app, these would come from a database
    private Map<String, Integer> leaveBalances = new HashMap<>();

    public LeaveRequestPanel(String employeeId) {
        // Initialize mock leave balances
        leaveBalances.put("Vacation Leave", 15);
        leaveBalances.put("Sick Leave", 10);
        leaveBalances.put("Emergency Leave", 5);

        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        // Create main content panel with spacing
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(primaryColor, 1),
                new EmptyBorder(15, 15, 15, 15)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Title
        JLabel titleLabel = new JLabel("Leave Request Form");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(primaryColor);
        titleLabel.setBorder(new EmptyBorder(0, 0, 15, 0));
        add(titleLabel, BorderLayout.NORTH);

        // Employee ID
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel idLabel = new JLabel("Employee ID:");
        idLabel.setFont(new Font("Arial", Font.BOLD, 12));
        contentPanel.add(idLabel, gbc);

        gbc.gridx = 1;
        employeeIdField = new JTextField(employeeId);
        employeeIdField.setEditable(false);
        employeeIdField.setBackground(new Color(240, 240, 240));
        contentPanel.add(employeeIdField, gbc);

        // Leave Type
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel typeLabel = new JLabel("Leave Type:");
        typeLabel.setFont(new Font("Arial", Font.BOLD, 12));
        contentPanel.add(typeLabel, gbc);

        gbc.gridx = 1;
        String[] leaveTypes = {"Vacation Leave", "Sick Leave", "Maternity Leave",
                "Paternity Leave", "Emergency Leave", "Other"};
        leaveTypeComboBox = new JComboBox<>(leaveTypes);
        contentPanel.add(leaveTypeComboBox, gbc);

        // Add balance information
        gbc.gridx = 2;
        JLabel balanceLabel = new JLabel();
        updateBalanceLabel(balanceLabel, (String)leaveTypeComboBox.getSelectedItem());
        contentPanel.add(balanceLabel, gbc);

        // Update balance label when leave type changes
        leaveTypeComboBox.addActionListener(e ->
                updateBalanceLabel(balanceLabel, (String)leaveTypeComboBox.getSelectedItem()));

        // Start Date
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        JLabel startLabel = new JLabel("Start Date:");
        startLabel.setFont(new Font("Arial", Font.BOLD, 12));
        contentPanel.add(startLabel, gbc);

        gbc.gridx = 1;
        try {
            MaskFormatter dateFormatter = new MaskFormatter("##/##/####");
            dateFormatter.setPlaceholderCharacter('_');
            startDateField = new JFormattedTextField(dateFormatter);
            startDateField.setToolTipText("MM/DD/YYYY");
            startDateField.setColumns(10);
        } catch (ParseException e) {
            startDateField = new JFormattedTextField();
        }
        contentPanel.add(startDateField, gbc);

        // Date picker icon for start date
        gbc.gridx = 2;
        JButton startDatePickerBtn = createDatePickerButton(startDateField);
        contentPanel.add(startDatePickerBtn, gbc);

        // End Date
        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel endLabel = new JLabel("End Date:");
        endLabel.setFont(new Font("Arial", Font.BOLD, 12));
        contentPanel.add(endLabel, gbc);

        gbc.gridx = 1;
        try {
            MaskFormatter dateFormatter = new MaskFormatter("##/##/####");
            dateFormatter.setPlaceholderCharacter('_');
            endDateField = new JFormattedTextField(dateFormatter);
            endDateField.setToolTipText("MM/DD/YYYY");
            endDateField.setColumns(10);
        } catch (ParseException e) {
            endDateField = new JFormattedTextField();
        }
        contentPanel.add(endDateField, gbc);

        // Date picker icon for end date
        gbc.gridx = 2;
        JButton endDatePickerBtn = createDatePickerButton(endDateField);
        contentPanel.add(endDatePickerBtn, gbc);

        // Days count
        gbc.gridx = 0;
        gbc.gridy = 4;
        JLabel daysLabel = new JLabel("Total Days:");
        daysLabel.setFont(new Font("Arial", Font.BOLD, 12));
        contentPanel.add(daysLabel, gbc);

        gbc.gridx = 1;
        daysCountLabel = new JLabel("0 day(s)");
        daysCountLabel.setForeground(primaryColor);
        contentPanel.add(daysCountLabel, gbc);

        // Add document listeners to update days count when dates change
        startDateField.getDocument().addDocumentListener(new DateChangeListener());
        endDateField.getDocument().addDocumentListener(new DateChangeListener());

        // Reason
        gbc.gridx = 0;
        gbc.gridy = 5;
        JLabel reasonLabel = new JLabel("Reason:");
        reasonLabel.setFont(new Font("Arial", Font.BOLD, 12));
        contentPanel.add(reasonLabel, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        reasonField = new JTextArea(4, 20);
        reasonField.setLineWrap(true);
        reasonField.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(reasonField);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        contentPanel.add(scrollPane, gbc);

        // Character count
        gbc.gridx = 1;
        gbc.gridy = 6;
        JLabel charCountLabel = new JLabel("0/100 characters");
        reasonField.getDocument().addDocumentListener(new CharCountListener(charCountLabel, 100));
        contentPanel.add(charCountLabel, gbc);

        // Button panel
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.EAST;

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        resetButton = new JButton("Reset");
        resetButton.addActionListener(e -> resetForm());
        buttonPanel.add(resetButton);

        submitButton = new JButton("Submit Request");
        submitButton.setForeground(Color.WHITE);
        submitButton.setBackground(primaryColor);
        submitButton.setFocusPainted(false);
        buttonPanel.add(submitButton);

        contentPanel.add(buttonPanel, gbc);

        add(contentPanel, BorderLayout.CENTER);

        submitButton.addActionListener(e -> submitLeaveRequest());
    }

    private JButton createDatePickerButton(JFormattedTextField dateField) {
        JButton datePickerBtn = new JButton("📅");
        datePickerBtn.setMargin(new Insets(0, 0, 0, 0));
        datePickerBtn.setPreferredSize(new Dimension(25, 25));
        datePickerBtn.addActionListener(e -> showDatePicker(dateField));
        return datePickerBtn;
    }

    private void showDatePicker(JFormattedTextField dateField) {
        // Create date picker dialog
        JDialog datePickerDialog = new JDialog((Frame)SwingUtilities.getWindowAncestor(this), "Select Date", true);
        datePickerDialog.setSize(300, 350);
        datePickerDialog.setLocationRelativeTo(this);
        datePickerDialog.setLayout(new BorderLayout());

        // Calendar instance for date manipulation
        Calendar calendar = Calendar.getInstance();
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentYear = calendar.get(Calendar.YEAR);

        // Month/Year navigation panel
        JPanel navigationPanel = new JPanel(new BorderLayout());
        JButton prevMonth = new JButton("◀");
        JButton nextMonth = new JButton("▶");
        JLabel monthYearLabel = new JLabel("", JLabel.CENTER);
        navigationPanel.add(prevMonth, BorderLayout.WEST);
        navigationPanel.add(monthYearLabel, BorderLayout.CENTER);
        navigationPanel.add(nextMonth, BorderLayout.EAST);
        datePickerDialog.add(navigationPanel, BorderLayout.NORTH);

        // Days panel - will be recreated when month changes
        JPanel daysPanel = new JPanel(new BorderLayout());
        datePickerDialog.add(daysPanel, BorderLayout.CENTER);

        // Action to update calendar when month changes
        ActionListener monthChangeListener = e -> {
            if (e.getSource() == prevMonth) {
                calendar.add(Calendar.MONTH, -1);
            } else {
                calendar.add(Calendar.MONTH, 1);
            }
            updateCalendarPanel(calendar, daysPanel, dateField, datePickerDialog);
        };

        prevMonth.addActionListener(monthChangeListener);
        nextMonth.addActionListener(monthChangeListener);

        // Initialize the calendar display
        updateCalendarPanel(calendar, daysPanel, dateField, datePickerDialog);

        datePickerDialog.setVisible(true);
    }

    private void updateCalendarPanel(Calendar calendar, JPanel daysPanel, JFormattedTextField dateField, JDialog dialog) {
        daysPanel.removeAll();

        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        // Update month/year label
        Container parent = daysPanel.getParent();
        ((JLabel)((JPanel)parent.getComponent(0)).getComponent(1)).setText(
                new java.text.SimpleDateFormat("MMMM yyyy").format(calendar.getTime()));

        // Create panel for days
        JPanel calendarPanel = new JPanel(new GridLayout(0, 7));

        // Add day of week headers
        String[] daysOfWeek = {"Su", "Mo", "Tu", "We", "Th", "Fr", "Sa"};
        for (String day : daysOfWeek) {
            JLabel label = new JLabel(day, JLabel.CENTER);
            label.setForeground(primaryColor);
            calendarPanel.add(label);
        }

        // Get first day of month
        Calendar temp = (Calendar)calendar.clone();
        temp.set(Calendar.DAY_OF_MONTH, 1);
        int firstDayOfMonth = temp.get(Calendar.DAY_OF_WEEK) - 1; // 0 = Sunday

        // Add empty labels for days before first day of month
        for (int i = 0; i < firstDayOfMonth; i++) {
            calendarPanel.add(new JLabel(""));
        }

        // Add buttons for days in month
        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int i = 1; i <= daysInMonth; i++) {
            JButton dayButton = new JButton(String.valueOf(i));
            dayButton.setMargin(new Insets(1, 1, 1, 1));

            final int day = i;
            dayButton.addActionListener(e -> {
                Calendar selectedDate = (Calendar)calendar.clone();
                selectedDate.set(Calendar.DAY_OF_MONTH, day);
                dateField.setText(dateFormat.format(selectedDate.getTime()));
                dialog.dispose();
                updateDaysCount();
            });

            // Highlight current day
            if (year == Calendar.getInstance().get(Calendar.YEAR) &&
                    month == Calendar.getInstance().get(Calendar.MONTH) &&
                    i == Calendar.getInstance().get(Calendar.DAY_OF_MONTH)) {
                dayButton.setBackground(new Color(135, 206, 250)); // Light blue
                dayButton.setForeground(Color.WHITE);
            }

            calendarPanel.add(dayButton);
        }

        // Add the calendar to a scroll pane
        JScrollPane scrollPane = new JScrollPane(calendarPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        daysPanel.add(scrollPane, BorderLayout.CENTER);

        // Add today button at bottom
        JButton todayButton = new JButton("Today");
        todayButton.addActionListener(e -> {
            dateField.setText(dateFormat.format(new Date()));
            dialog.dispose();
            updateDaysCount();
        });

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.add(todayButton);
        daysPanel.add(bottomPanel, BorderLayout.SOUTH);

        daysPanel.revalidate();
        daysPanel.repaint();
    }

    private void updateBalanceLabel(JLabel label, String leaveType) {
        Integer balance = leaveBalances.get(leaveType);
        if (balance != null) {
            label.setText("Balance: " + balance + " day(s)");
        } else {
            label.setText("");
        }
    }

    private class DateChangeListener implements javax.swing.event.DocumentListener {
        @Override
        public void insertUpdate(javax.swing.event.DocumentEvent e) {
            updateDaysCount();
        }

        @Override
        public void removeUpdate(javax.swing.event.DocumentEvent e) {
            updateDaysCount();
        }

        @Override
        public void changedUpdate(javax.swing.event.DocumentEvent e) {
            updateDaysCount();
        }
    }

    private class CharCountListener implements javax.swing.event.DocumentListener {
        private JLabel label;
        private int maxChars;

        public CharCountListener(JLabel label, int maxChars) {
            this.label = label;
            this.maxChars = maxChars;
        }

        private void update() {
            int length = reasonField.getText().length();
            label.setText(length + "/" + maxChars + " characters");
            if (length > maxChars) {
                label.setForeground(errorColor);
            } else {
                label.setForeground(Color.DARK_GRAY);
            }
        }

        @Override
        public void insertUpdate(javax.swing.event.DocumentEvent e) { update(); }
        @Override
        public void removeUpdate(javax.swing.event.DocumentEvent e) { update(); }
        @Override
        public void changedUpdate(javax.swing.event.DocumentEvent e) { update(); }
    }

    private void updateDaysCount() {
        try {
            String startDateStr = startDateField.getText();
            String endDateStr = endDateField.getText();

            if (startDateStr.contains("_") || endDateStr.contains("_")) {
                daysCountLabel.setText("0 day(s)");
                return;
            }

            Date startDate = dateFormat.parse(startDateStr);
            Date endDate = dateFormat.parse(endDateStr);

            long diff = endDate.getTime() - startDate.getTime();
            int days = (int) (diff / (1000 * 60 * 60 * 24)) + 1; // Add 1 to include both start and end dates

            if (days <= 0) {
                daysCountLabel.setText("Invalid date range");
                daysCountLabel.setForeground(errorColor);
            } else {
                daysCountLabel.setText(days + " day(s)");
                daysCountLabel.setForeground(primaryColor);
            }
        } catch (Exception e) {
            daysCountLabel.setText("Invalid date format");
            daysCountLabel.setForeground(errorColor);
        }
    }

    private void resetForm() {
        leaveTypeComboBox.setSelectedIndex(0);
        startDateField.setValue(null);
        endDateField.setValue(null);
        reasonField.setText("");
        daysCountLabel.setText("0 day(s)");
    }

    private boolean validateForm() {
        StringBuilder errorMessage = new StringBuilder();
        boolean hasErrors = false;

        // Check dates
        String startDateStr = startDateField.getText();
        String endDateStr = endDateField.getText();

        if (startDateStr.contains("_") || endDateStr.contains("_")) {
            errorMessage.append("- Please enter valid dates (MM/DD/YYYY)\n");
            hasErrors = true;
        } else {
            try {
                Date startDate = dateFormat.parse(startDateStr);
                Date endDate = dateFormat.parse(endDateStr);
                Date currentDate = new Date();

                // Set time to 00:00:00 for current date for accurate comparison
                Calendar cal = Calendar.getInstance();
                cal.setTime(currentDate);
                cal.set(Calendar.HOUR_OF_DAY, 0);
                cal.set(Calendar.MINUTE, 0);
                cal.set(Calendar.SECOND, 0);
                cal.set(Calendar.MILLISECOND, 0);
                currentDate = cal.getTime();

                if (startDate.before(currentDate)) {
                    errorMessage.append("- Start date cannot be in the past\n");
                    hasErrors = true;
                }

                if (endDate.before(startDate)) {
                    errorMessage.append("- End date must be after or equal to start date\n");
                    hasErrors = true;
                }

                // Check leave duration against available balance
                long diff = endDate.getTime() - startDate.getTime();
                int days = (int) (diff / (1000 * 60 * 60 * 24)) + 1;

                String leaveType = (String) leaveTypeComboBox.getSelectedItem();
                Integer balance = leaveBalances.get(leaveType);
                if (balance != null && days > balance) {
                    errorMessage.append("- Requested days exceed your available balance\n");
                    hasErrors = true;
                }

            } catch (ParseException e) {
                errorMessage.append("- Invalid date format\n");
                hasErrors = true;
            }
        }

        // Check reason
        String reason = reasonField.getText().trim();
        if (reason.isEmpty()) {
            errorMessage.append("- Please provide a reason for your leave request\n");
            hasErrors = true;
        } else if (reason.length() < 5) {
            errorMessage.append("- Please provide a more detailed reason (min 5 characters)\n");
            hasErrors = true;
        } else if (reason.length() > 100) {
            errorMessage.append("- Reason is too long (max 100 characters)\n");
            hasErrors = true;
        }

        if (hasErrors) {
            JOptionPane.showMessageDialog(this,
                    "Please correct the following errors:\n" + errorMessage.toString(),
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE);
        }

        return !hasErrors;
    }

    private void submitLeaveRequest() {
        if (!validateForm()) {
            return;
        }

        String employeeId = employeeIdField.getText();
        String leaveType = (String) leaveTypeComboBox.getSelectedItem();
        String startDate = startDateField.getText();
        String endDate = endDateField.getText();
        String reason = reasonField.getText();
        String status = "Pending"; // Default status for new leave requests

        try (FileWriter writer = new FileWriter("resources/Leaves.csv", true)) {
            writer.append(employeeId).append(',')
                    .append(leaveType).append(',')
                    .append(startDate).append(',')
                    .append(endDate).append(',')
                    .append(reason.replace(',', ';')).append(',')
                    .append(status).append('\n');

            // Update balance (in a real app, this would persist to a database)
            Integer balance = leaveBalances.get(leaveType);
            if (balance != null) {
                try {
                    Date start = dateFormat.parse(startDate);
                    Date end = dateFormat.parse(endDate);
                    int days = (int) ((end.getTime() - start.getTime()) / (1000 * 60 * 60 * 24)) + 1;
                    leaveBalances.put(leaveType, balance - days);
                } catch (ParseException ex) {
                    // Ignore, since we've already validated
                }
            }

            JOptionPane.showMessageDialog(this,
                    "Leave request submitted successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);

            resetForm();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this,
                    "Error saving leave request: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}