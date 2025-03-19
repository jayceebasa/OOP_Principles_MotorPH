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

    private void saveLeave() {
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