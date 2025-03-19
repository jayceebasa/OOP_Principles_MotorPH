package Frames;

import com.opencsv.exceptions.CsvValidationException;
import org.mapua.Login;
import Frames.components.ModernTextField;
import Frames.components.ModernPasswordField;
import Frames.components.RoundedButton;
import Frames.components.RoundedPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginFrame extends JFrame {
    private JPanel mainPanel;
    private ModernTextField usernameField;
    private ModernPasswordField passwordField;
    private RoundedButton loginButton;
    private Color primaryColor = new Color(25, 118, 210);
    private Color backgroundColor = new Color(245, 245, 245);

    public LoginFrame() {
        initComponents();
    }

    private void initComponents() {
        setTitle("MotorPH Login");
        setSize(450, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        mainPanel = new RoundedPanel(15);
        ((RoundedPanel)mainPanel).setShady(true);
        mainPanel.setLayout(new BorderLayout(20, 20));
        mainPanel.setBackground(Color.WHITE);

        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        logoPanel.setOpaque(false);

        ImageIcon logoIcon = new ImageIcon("resources/MotorPH.png");
        Image img = logoIcon.getImage().getScaledInstance(300, 150, Image.SCALE_SMOOTH);
        logoIcon = new ImageIcon(img);
        JLabel logoLabel = new JLabel(logoIcon);
        logoLabel.setBorder(new EmptyBorder(30, 0, 20, 0));
        logoPanel.add(logoLabel);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setOpaque(false);
        formPanel.setBorder(new EmptyBorder(10, 40, 30, 40));

        JLabel titleLabel = new JLabel("Welcome Back");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(primaryColor);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("Sign in to continue");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(120, 120, 120));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        usernameField = new ModernTextField("Username");
        passwordField = new ModernPasswordField("Password");

        loginButton = new RoundedButton("SIGN IN");
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        loginButton.setBackground(primaryColor);
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setBorderPainted(false);
        loginButton.addActionListener(e -> loginbtnMouseClicked());
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        formPanel.add(titleLabel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        formPanel.add(subtitleLabel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        formPanel.add(usernameField);
        formPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        formPanel.add(passwordField);
        formPanel.add(Box.createRigidArea(new Dimension(0, 35)));
        formPanel.add(loginButton);

        mainPanel.add(logoPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);

        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.setBackground(backgroundColor);
        wrapperPanel.setBorder(new EmptyBorder(30, 30, 30, 30));
        wrapperPanel.add(mainPanel, BorderLayout.CENTER);

        setContentPane(wrapperPanel);
        getRootPane().setDefaultButton(loginButton);
    }

    private void loginbtnMouseClicked() {
        Login login = new Login();
        login.setUsername(usernameField.getText());
        login.setPassword(passwordField.getPassword());

        try {
            if ((!login.getUsername().isEmpty()) && (login.getPassword().length != 0)) {
                if (login.isAuthenticated()) {
                    JOptionPane.showMessageDialog(this, "Login Successful!");
                    dispose();
                    if (login.getRole().equals("Admin")) {
                        new MainFrame().setVisible(true);
                    } else if (login.getRole().equals("Employee")) {
                        new EmployeeUIFrame(login.getEmployeeId()).setVisible(true);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Username or Password is incorrect. Try again.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Username and password is a required field!");
            }
        } catch (IOException | CsvValidationException ex) {
            Logger.getLogger(LoginFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}