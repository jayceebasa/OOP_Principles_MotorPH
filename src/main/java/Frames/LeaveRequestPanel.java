package Frames;

import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;

public class LeaveRequestPanel extends JPanel {
    private JTextField employeeIdField;
    private JTextField leaveTypeField;
    private JTextField startDateField;
    private JTextField endDateField;
    private JTextArea reasonField;
    private JButton submitButton;

    public LeaveRequestPanel(String employeeId) {
        setLayout(new GridLayout(6, 2, 5, 5));

        add(new JLabel("Employee ID:"));
        employeeIdField = new JTextField(employeeId);
        employeeIdField.setEditable(false);
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

        submitButton = new JButton("Submit");
        add(submitButton);

        submitButton.addActionListener(e -> submitLeaveRequest());
    }

    private void submitLeaveRequest() {
        String employeeId = employeeIdField.getText();
        String leaveType = leaveTypeField.getText();
        String startDate = startDateField.getText();
        String endDate = endDateField.getText();
        String reason = reasonField.getText();

        try (FileWriter writer = new FileWriter("resources/Leaves.csv", true)) {
            writer.append(employeeId).append(',')
                    .append(leaveType).append(',')
                    .append(startDate).append(',')
                    .append(endDate).append(',')
                    .append(reason).append('\n');
            JOptionPane.showMessageDialog(this, "Leave request submitted for Employee ID: " + employeeId);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error saving leave request: " + ex.getMessage());
        }
    }
}