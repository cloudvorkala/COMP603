/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cipheredUser;

/**
 *
 * @author cloud
 */
public class User {
    private String username;
    private String password; // Encrypted or hashed password
    private String email;

    // Constructor
    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // Password setter should enforce some security checks
    public void setPassword(String password) {
        // Add password encryption or validation logic here
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Method to validate user credentials (example)
    public boolean validatePassword(String inputPassword) {
        // Add logic to compare encrypted password
        return this.password.equals(inputPassword);
    }
}
