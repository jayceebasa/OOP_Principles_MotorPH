package org.mapua;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Login {
    // Encapsulation: Private fields for username and password
    private String username;
    private String password;

    // Encapsulation: Public getters and setters
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

    // Abstraction: Public method to authenticate user
    public boolean isAuthenticated() throws FileNotFoundException, IOException, CsvValidationException {
        String filename = "resources/credentials.csv";
        try (CSVReader csvReader = new CSVReader(new FileReader(filename))) {
            String[] line;
            while ((line = csvReader.readNext()) != null) {
                if (line[0].equals(username)) {
                    return line[1].equals(password);
                }
            }
        }
        return false;
    }
}