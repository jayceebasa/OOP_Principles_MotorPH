package Frames;

import org.mapua.Employees;
import org.mapua.EmployeeRepository;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MainFrame extends JFrame {
    private JLabel listTitle;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton editButton;
    private JButton addButton;
    private JButton logoutButton;
    private JButton deleteButton;

    private EmployeeRepository employeeRepository;
    private Employees employees;

    public MainFrame() {
        // Initialize EmployeeRepository and Employees with dependency injection
        employeeRepository = new EmployeeRepository();
        employees = new Employees(employeeRepository);

        setTitle("MotorPH Portal");
        setSize(650, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Initialize Label
        JPanel listPanel = new JPanel();
        listTitle = new JLabel("List of Employees");
        listTitle.setHorizontalAlignment(SwingConstants.LEFT);
        listPanel.add(listTitle);

        // Initialize table
        tableModel = new DefaultTableModel(employees.getTableData(), employees.getTableHeader());
        table = new JTable(tableModel);
        table.setDefaultEditor(Object.class, null);
        table.getTableHeader().setBackground(Color.yellow);
        JScrollPane scrollPane = new JScrollPane(table);

        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if ((table.getValueAt(table.getSelectedRow(), 0).toString() != null ||
                        (table.getValueAt(table.getSelectedRow(), 0).toString().equals("0")))) {
                    editButton.setVisible(true);
                    deleteButton.setVisible(true);
                } else {
                    editButton.setVisible(false);
                    deleteButton.setVisible(false);
                }
            }
        });

        // Initialize buttons, View / Edit Function
        editButton = new JButton("View/Edit");
        editButton.setVisible(false);
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    EmployeeFrame employeeFrame = new EmployeeFrame();
                    employeeFrame.updateEmployee(table.getValueAt(selectedRow, 0).toString());
                    employeeFrame.setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a row to edit.");
                }
            }
        });

        // Delete function
        deleteButton = new JButton("Delete");
        deleteButton.setVisible(false);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this employee?",
                            "Confirmation", JOptionPane.YES_NO_OPTION);
                    if (option == JOptionPane.YES_OPTION) {
                        String employeeId = table.getValueAt(selectedRow, 0).toString();
                        employees.deleteEmployee(employeeId);
                        // To refresh the window after deleting
                        dispose();
                        SwingUtilities.invokeLater(new Runnable() {
                            public void run() {
                                new MainFrame().setVisible(true);
                            }
                        });
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a row to delete.");
                }
            }
        });

        // Add Function
        addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new EmployeeFrame().setVisible(true);
                dispose();
            }
        });

        // Logout Function
        logoutButton = new JButton("Logout");
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new LoginFrame().setVisible(true);
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(editButton);
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(logoutButton);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(listPanel, BorderLayout.NORTH);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                MainFrame frame = new MainFrame();
                frame.setVisible(true);
            }
        });
    }
}