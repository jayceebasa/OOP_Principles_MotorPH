package Frames;

import org.mapua.Employees;
import org.mapua.EmployeeRepository;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

    private JScrollPane scrollPane;
    private JPanel listPanel;
    private JPanel buttonPanel;
    private JButton manageLeavesButton;

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
        listPanel = new JPanel();
        listTitle = new JLabel("List of Employees");
        listTitle.setHorizontalAlignment(SwingConstants.LEFT);
        listPanel.add(listTitle);

        // Initialize table
        tableModel = new DefaultTableModel(employees.getTableData(), employees.getTableHeader());
        table = new JTable(tableModel);
        table.setDefaultEditor(Object.class, null);
        table.getTableHeader().setBackground(Color.yellow);
        scrollPane = new JScrollPane(table);

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

        // Manage Leaves Function
        manageLeavesButton = new JButton("Manage Leaves");
        manageLeavesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LeaveFrame().setVisible(true);
            }
        });

        buttonPanel = new JPanel();
        buttonPanel.add(editButton);
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(logoutButton);
        buttonPanel.add(manageLeavesButton);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(listPanel, BorderLayout.NORTH);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        // Call the styleComponents method to apply styles
        styleComponents();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                MainFrame frame = new MainFrame();
                frame.setVisible(true);
            }
        });
    }

    // In MainFrame.java constructor after initializing components
    private void styleComponents() {
        // Style the table
        table.setRowHeight(35);
        table.setIntercellSpacing(new Dimension(10, 5));
        table.setShowGrid(false);
        table.setShowHorizontalLines(true);
        table.setGridColor(new Color(230, 230, 230));
        table.setSelectionBackground(new Color(232, 242, 254));
        table.setSelectionForeground(Color.BLACK);
        table.getTableHeader().setBackground(new Color(240, 240, 240)); // Light gray
        table.getTableHeader().setForeground(new Color(60, 60, 60)); // Dark gray
        table.setSelectionBackground(new Color(232, 242, 254)); // Light blue
        table.setSelectionForeground(Color.BLACK); // Black text
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        // Style the scrollpane
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);

        // Style the buttons
        styleButton(editButton, new Color(25, 118, 210), Color.WHITE);
        styleButton(addButton, new Color(76, 175, 80), Color.WHITE);
        styleButton(deleteButton, new Color(211, 47, 47), Color.WHITE);
        styleButton(logoutButton, new Color(158, 158, 158), Color.WHITE);
        styleButton(manageLeavesButton, new Color(255, 193, 7), Color.WHITE);

        // Style the panel
        listPanel.setBackground(Color.WHITE);
        listPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 5, 15));
        listTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));

        // Style button panel
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Style the frame
        getContentPane().setBackground(Color.WHITE);
    }

    private void styleButton(JButton button, Color bgColor, Color fgColor) {
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setPreferredSize(new Dimension(100, 35));

        // Round corners using a custom border
        button.setBorder(new EmptyBorder(8, 15, 8, 15));

        // Add hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(darken(bgColor, 0.1f));
            }

            @Override
            public void mouseExited(MouseEvent e) {
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
}