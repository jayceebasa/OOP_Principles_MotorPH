package Frames;

import org.mapua.Leave;
import org.mapua.LeaveRepository;
import com.opencsv.exceptions.CsvValidationException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

public class LeaveFrame extends JFrame {
    private JTextField employeeIdField;
    private JTextField leaveTypeField;
    private JTextField startDateField;
    private JTextField endDateField;
    private JTextArea reasonField;
    private JButton saveButton;
    private LeaveRepository leaveRepository;

    public LeaveFrame() {
        leaveRepository = new LeaveRepository();
        setTitle("Leave Management");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6, 2));

        add(new JLabel("Employee ID:"));
        employeeIdField = new JTextField();
        add(employeeIdField);

        add(new JLabel("Leave Type:"));
        leaveTypeField = new JTextField();
        add(leaveTypeField);

        add(new JLabel("Start Date:"));
        startDateField = new JTextField();
        add(startDateField);

        add(new JLabel("End Date:"));
        endDateField = new JTextField();
        add(endDateField);

        add(new JLabel("Reason:"));
        reasonField = new JTextArea();
        add(reasonField);

        saveButton = new JButton("Save");
        add(saveButton);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveLeave();
            }
        });
    }

    private boolean validateLeaveForm() {
        boolean hasErrors = false;
        StringBuilder errorMessage = new StringBuilder("Please correct the following errors:\n");

        // Employee ID validation
        if (employeeIdField.getText().trim().isEmpty()) {
            errorMessage.append("- Employee ID is required\n");
            hasErrors = true;
        }

        // Leave type validation
        if (leaveTypeField.getText().trim().isEmpty()) {
            errorMessage.append("- Leave type is required\n");
            hasErrors = true;
        }

        // Date validations
        String startDate = startDateField.getText().trim();
        String endDate = endDateField.getText().trim();
        if (startDate.isEmpty()) {
            errorMessage.append("- Start date is required\n");
            hasErrors = true;
        } else {
            try {
                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MM/dd/yyyy");
                sdf.setLenient(false);
                java.util.Date parsedStartDate = sdf.parse(startDate);

                // Check if start date is in the past
                if (parsedStartDate.before(new java.util.Date())) {
                    errorMessage.append("- Start date cannot be in the past\n");
                    hasErrors = true;
                }

                // Check end date if provided
                if (!endDate.isEmpty()) {
                    java.util.Date parsedEndDate = sdf.parse(endDate);
                    if (parsedEndDate.before(parsedStartDate)) {
                        errorMessage.append("- End date must be after or equal to start date\n");
                        hasErrors = true;
                    }
                }
            } catch (java.text.ParseException e) {
                errorMessage.append("- Start date must be in MM/DD/YYYY format\n");
                hasErrors = true;
            }
        }

        if (endDate.isEmpty()) {
            errorMessage.append("- End date is required\n");
            hasErrors = true;
        } else if (!hasErrors) { // Only validate format if no other date errors
            try {
                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MM/dd/yyyy");
                sdf.setLenient(false);
                sdf.parse(endDate);
            } catch (java.text.ParseException e) {
                errorMessage.append("- End date must be in MM/DD/YYYY format\n");
                hasErrors = true;
            }
        }

        // Reason validation
        if (reasonField.getText().trim().isEmpty()) {
            errorMessage.append("- Reason for leave is required\n");
            hasErrors = true;
        }

        if (hasErrors) {
            JOptionPane.showMessageDialog(this, errorMessage.toString(), "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    private void saveLeave() {
        if (!validateLeaveForm()) {
            return;
        }

        Leave leave = new Leave();
        leave.setEmployeeId(employeeIdField.getText());
        leave.setLeaveType(leaveTypeField.getText());
        leave.setStartDate(startDateField.getText());
        leave.setEndDate(endDateField.getText());
        leave.setReason(reasonField.getText());

        try {
            List<String[]> records = leaveRepository.readLeaves();
            String[] newRecord = {leave.getEmployeeId(), leave.getLeaveType(), leave.getStartDate(), leave.getEndDate(), leave.getReason()};
            records.add(newRecord);
            leaveRepository.writeLeaves(records);
            JOptionPane.showMessageDialog(this, "Leave saved successfully!");
        } catch (IOException | CsvValidationException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving leave.");
        }
    }
}