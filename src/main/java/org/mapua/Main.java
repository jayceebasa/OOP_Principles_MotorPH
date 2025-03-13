package org.mapua;

import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMaterialLighterIJTheme;
import Frames.LoginFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            // Set modern look and feel
            FlatMaterialLighterIJTheme.setup();

            // Enable font anti-aliasing
            System.setProperty("awt.useSystemAAFontSettings", "on");
            System.setProperty("swing.aatext", "true");

            // Run the application
            SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}