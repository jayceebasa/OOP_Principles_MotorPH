package Frames.components;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class ModernPasswordField extends JPanel {
    private JPasswordField passwordField;
    private JLabel placeholderLabel;
    private String placeholder;
    private Color focusBorderColor = new Color(25, 118, 210);
    private Color borderColor = new Color(200, 200, 200);

    public ModernPasswordField(String placeholder) {
        this.placeholder = placeholder;
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder());
        setOpaque(false);

        // Create the password field
        passwordField = new JPasswordField();
        passwordField.setBorder(new EmptyBorder(10, 10, 10, 10));
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordField.setOpaque(false);

        // Create the placeholder label
        placeholderLabel = new JLabel(placeholder);
        placeholderLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        placeholderLabel.setForeground(new Color(150, 150, 150));
        placeholderLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
        placeholderLabel.setOpaque(false);
        placeholderLabel.setFocusable(false);

        // Add focus listener to the password field
        passwordField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                placeholderLabel.setVisible(false);
                repaint();
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (passwordField.getPassword().length == 0) {
                    placeholderLabel.setVisible(true);
                }
                repaint();
            }
        });

        // Create a container panel for the password field and placeholder label
        JPanel container = new JPanel(new BorderLayout());
        container.setOpaque(false);
        container.add(passwordField, BorderLayout.CENTER);
        container.add(placeholderLabel, BorderLayout.WEST);

        add(container, BorderLayout.CENTER);
        setPreferredSize(new Dimension(200, 45));
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public char[] getPassword() {
        return passwordField.getPassword();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(passwordField.hasFocus() ? focusBorderColor : borderColor);
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 8, 8);

        g2.dispose();
    }
}