package org.mapua;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Login {
    private String username;
    private String password;
    private String role;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(char[] password) {
        this.password = new String(password);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public boolean isAuthenticated() throws FileNotFoundException, IOException, CsvValidationException {
        String filename = "resources/credentials.csv";
        try (CSVReader csvReader = new CSVReader(new FileReader(filename))) {
            String[] line;
            while ((line = csvReader.readNext()) != null) {
                if (line[0].equals(username)) {
                    if (line[1].equals(password)) {
                        role = line[2]; // Assuming the role is in the third column
                        return true;
                    }
                }
            }
        }
        return false;
    }
}