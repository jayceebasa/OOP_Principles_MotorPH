package Frames;

import org.mapua.Leave;
import org.mapua.LeaveRepository;
import com.opencsv.exceptions.CsvValidationException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MyLeavesPanel extends JPanel {
    private JTable leavesTable;
    private DefaultTableModel tableModel;
    private String employeeId;
    private LeaveRepository leaveRepository;
    private Color pendingColor = new Color(255, 235, 156); // Light yellow
    private Color approvedColor = new Color(198, 239, 206); // Light green
    private Color declinedColor = new Color(255, 199, 206); // Light red

    public MyLeavesPanel(String employeeId) {
        this.employeeId = employeeId;
        this.leaveRepository = new LeaveRepository();
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Create title
        JLabel titleLabel = new JLabel("My Leave Requests");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(new Color(70, 130, 180));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        add(titleLabel, BorderLayout.NORTH);

        // Create table model
        String[] columns = {"Leave Type", "Start Date", "End Date", "Reason", "Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table non-editable
            }
        };

        // Create table and set properties
        leavesTable = new JTable(tableModel) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component comp = super.prepareRenderer(renderer, row, column);
                String status = (String) getValueAt(row, 4);

                if ("Approved".equals(status)) {
                    comp.setBackground(approvedColor);
                } else if ("Declined".equals(status)) {
                    comp.setBackground(declinedColor);
                } else {
                    comp.setBackground(pendingColor);
                }

                return comp;
            }
        };

        leavesTable.setRowHeight(25);
        leavesTable.setIntercellSpacing(new Dimension(10, 5));
        leavesTable.setFillsViewportHeight(true);
        leavesTable.getTableHeader().setReorderingAllowed(false);
        leavesTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));

        // Set column widths
        TableColumn reasonColumn = leavesTable.getColumnModel().getColumn(3);
        reasonColumn.setPreferredWidth(200);

        // Create scroll pane
        JScrollPane scrollPane = new JScrollPane(leavesTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        add(scrollPane, BorderLayout.CENTER);

        // Add refresh button
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> loadLeaves());
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(refreshButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Load leaves data
        loadLeaves();
    }

    private void loadLeaves() {
        try {
            // Clear the table
            tableModel.setRowCount(0);

            // Read leaves from CSV
            List<String[]> leaveRecords = leaveRepository.readLeaves();

            // Filter by employee ID and populate table
            for (String[] record : leaveRecords) {
                if (record.length >= 6 && record[0].equals(employeeId)) {
                    String leaveType = record[1];
                    String startDate = record[2];
                    String endDate = record[3];
                    String reason = record[4].replace(';', ',');
                    String status = record.length > 5 ? record[5] : "Pending";

                    tableModel.addRow(new Object[]{leaveType, startDate, endDate, reason, status});
                }
            }


        } catch (IOException | CsvValidationException e) {
            JOptionPane.showMessageDialog(this,
                    "Error loading leave data: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}