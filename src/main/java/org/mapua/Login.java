package org.mapua;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;

public class Login {
    private String username;
    private char[] password;
    private String role;
    private String employeeId;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public char[] getPassword() {
        return password;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public boolean isAuthenticated() throws IOException, CsvValidationException {
        try (CSVReader reader = new CSVReader(new FileReader("resources/credentials.csv"))) {
            String[] line;
            while ((line = reader.readNext()) != null) {
                if (line[0].equals(username) && line[1].equals(new String(password))) {
                    role = line[2];
                    employeeId = line[3];
                    return true;
                }
            }
        }
        return false;
    }
}