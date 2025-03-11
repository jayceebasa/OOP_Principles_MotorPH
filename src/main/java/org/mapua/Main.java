package org.mapua;

import Frames.LoginFrame;

public class Main {
    public static void main(String[] args) {
        // Polymorphism: Using a LoginFrame object
        LoginFrame login = new LoginFrame();
        login.setVisible(true);
    }
}