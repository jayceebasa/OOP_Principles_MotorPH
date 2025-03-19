package Frames;

import org.mapua.LeaveRepository;
import org.mapua.EmployeeRepository;
import org.mapua.Employees;
import com.opencsv.exceptions.CsvValidationException;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

public class LeavesManagementFrame extends JFrame {
    private JTable leavesTable;
    private DefaultTableModel tableModel;
    private LeaveRepository leaveRepository;
    private EmployeeRepository employeeRepository;
    private Employees employees;
    private Color pendingColor = new Color(255, 235, 156); // Light yellow
    private Color approvedColor = new Color(198, 239, 206); // Light green
    private Color declinedColor = new Color(255, 199, 206); // Light red

    public LeavesManagementFrame() {
        leaveRepository = new LeaveRepository();
        employeeRepository = new EmployeeRepository();
        employees = new Employees(employeeRepository);

        setTitle("Leave Management");
        setSize(1400, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create main layout
        setLayout(new BorderLayout(10, 10));
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Create title panel
        JPanel titlePanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Leave Requests Management");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        titlePanel.add(titleLabel, BorderLayout.WEST);

        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadLeaveRequests();
            }
        });
        titlePanel.add(refreshButton, BorderLayout.EAST);

        mainPanel.add(titlePanel, BorderLayout.NORTH);

        // Create table
        String[] columns = {"ID", "Employee", "Leave Type", "Start Date", "End Date", "Reason", "Status", "Actions"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 7; // Only action column is editable
            }

            @Override
            public Class<?> getColumnClass(int column) {
                return column == 7 ? JPanel.class : Object.class;
            }
        };

        leavesTable = new JTable(tableModel);
        leavesTable.setRowHeight(40);
        leavesTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        leavesTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        leavesTable.getColumnModel().getColumn(2).setPreferredWidth(120);
        leavesTable.getColumnModel().getColumn(3).setPreferredWidth(100);
        leavesTable.getColumnModel().getColumn(4).setPreferredWidth(100);
        leavesTable.getColumnModel().getColumn(5).setPreferredWidth(200);
        leavesTable.getColumnModel().getColumn(6).setPreferredWidth(80);
        leavesTable.getColumnModel().getColumn(7).setPreferredWidth(150);

        // Set custom renderers
        leavesTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if (!isSelected) {
                    String status = (String) table.getValueAt(row, 6);
                    if ("Pending".equals(status)) {
                        c.setBackground(pendingColor);
                    } else if ("Approved".equals(status)) {
                        c.setBackground(approvedColor);
                    } else if ("Declined".equals(status)) {
                        c.setBackground(declinedColor);
                    }
                }

                return c;
            }
        });

        leavesTable.setDefaultRenderer(JPanel.class, new ButtonRenderer());
        leavesTable.setDefaultEditor(JPanel.class, new ButtonEditor());

        JScrollPane scrollPane = new JScrollPane(leavesTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Create legend panel
        JPanel legendPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JPanel pendingPanel = createLegendItem("Pending", pendingColor);
        JPanel approvedPanel = createLegendItem("Approved", approvedColor);
        JPanel declinedPanel = createLegendItem("Declined", declinedColor);

        legendPanel.add(pendingPanel);
        legendPanel.add(approvedPanel);
        legendPanel.add(declinedPanel);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(legendPanel, BorderLayout.WEST);
        bottomPanel.add(backButton, BorderLayout.EAST);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Load data
        loadLeaveRequests();
    }

    private JPanel createLegendItem(String text, Color color) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        JPanel colorBox = new JPanel();
        colorBox.setPreferredSize(new Dimension(15, 15));
        colorBox.setBackground(color);
        colorBox.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        panel.add(colorBox);
        panel.add(new JLabel(text));

        return panel;
    }

    private void loadLeaveRequests() {
        tableModel.setRowCount(0);

        try {
            List<String[]> leaveRecords = leaveRepository.readLeaves();

            for (int i = 0; i < leaveRecords.size(); i++) {
                String[] record = leaveRecords.get(i);
                if (record.length >= 5) {
                    String employeeId = record[0];
                    String employeeName = getEmployeeName(employeeId);
                    String leaveType = record[1];
                    String startDate = record[2];
                    String endDate = record[3];
                    String reason = record[4].replace(';', ',');
                    String status = record.length > 5 ? record[5] : "Pending";

                    JPanel actionPanel = new JPanel(new GridLayout(1, 2, 5, 0));
                    JButton approveBtn = new JButton("Approve");
                    JButton declineBtn = new JButton("Decline");

                    // Disable buttons if already approved/declined
                    if (!"Pending".equals(status)) {
                        approveBtn.setEnabled(false);
                        declineBtn.setEnabled(false);
                    }

                    actionPanel.add(approveBtn);
                    actionPanel.add(declineBtn);

                    // Store the row index and employee ID in the button's client property
                    approveBtn.putClientProperty("rowIndex", i);
                    approveBtn.putClientProperty("employeeId", employeeId);
                    declineBtn.putClientProperty("rowIndex", i);
                    declineBtn.putClientProperty("employeeId", employeeId);

                    tableModel.addRow(new Object[]{i + 1, employeeName, leaveType, startDate, endDate, reason, status, actionPanel});
                }
            }

        } catch (IOException | CsvValidationException e) {
            JOptionPane.showMessageDialog(this, "Error loading leave data: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String getEmployeeName(String employeeId) {
        try {
            return employees.getEmployeeById(employeeId).getLastName() + ", " +
                    employees.getEmployeeById(employeeId).getFirstName();
        } catch (Exception e) {
            return employeeId;
        }
    }

    // Custom renderer for buttons in table
    private class ButtonRenderer implements javax.swing.table.TableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            JPanel panel = (JPanel) value;
            return panel;
        }
    }

    // Custom editor for buttons in table
    // Custom editor for buttons in table
    private class ButtonEditor extends DefaultCellEditor {
        private JPanel panel;
        private JButton btn;

        public ButtonEditor() {
            super(new JTextField());
            panel = new JPanel();
            setClickCountToStart(1);
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            JPanel actionPanel = (JPanel) value;
            panel = actionPanel;

            // Get buttons from the panel
            Component[] components = actionPanel.getComponents();
            for (Component comp : components) {
                if (comp instanceof JButton) {
                    btn = (JButton) comp; // Store the button being clicked
                    btn.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // Get the button that was actually clicked
                            JButton clickedButton = (JButton) e.getSource();

                            int rowIndex = (int) clickedButton.getClientProperty("rowIndex");
                            String employeeId = (String) clickedButton.getClientProperty("employeeId");
                            String newStatus;

                            // Determine status based on the clicked button's text
                            if ("Approve".equals(clickedButton.getText())) {
                                newStatus = "Approved";
                            } else {
                                newStatus = "Declined";
                            }

                            try {
                                leaveRepository.updateLeaveStatus(employeeId, rowIndex, newStatus);

                                // Update the UI immediately
                                int tableRow = leavesTable.getSelectedRow();
                                leavesTable.setValueAt(newStatus, tableRow, 6); // Update status column

                                // Get the action panel and disable both buttons
                                JPanel actionPanel = (JPanel) leavesTable.getValueAt(tableRow, 7);
                                for (Component comp : actionPanel.getComponents()) {
                                    if (comp instanceof JButton) {
                                        comp.setEnabled(false);
                                    }
                                }

                                JOptionPane.showMessageDialog(LeavesManagementFrame.this,
                                        "Leave request has been " + newStatus.toLowerCase() + "!");

                                // Complete the editing and refresh the table
                                fireEditingStopped();
                                leavesTable.repaint(); // Force repaint to update disabled buttons

                            } catch (IOException | CsvValidationException ex) {
                                JOptionPane.showMessageDialog(LeavesManagementFrame.this,
                                        "Error updating leave status: " + ex.getMessage(),
                                        "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    });
                }
            }

            return panel;
        }

        @Override
        public Object getCellEditorValue() {
            return panel;
        }
    }
}