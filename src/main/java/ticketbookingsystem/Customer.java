/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ticketbookingsystem;

import cipheredUser.Cipher;

public class Customer {
    protected String name;
    private String email;
    private String encryptedPassword; // Save encrypted password

    // Constructor: used for creating new user or loading from database
    public Customer(String name, String email, String encryptedPassword) {
        this.name = name;
        this.email = email;
        this.encryptedPassword = encryptedPassword;  // Store encrypted password directly
    }

    // Getter for customer name
    public String getName() {
        return name;
    }

    // Getter for customer email
    public String getEmail() {
        return email;
    }

    // Getter for encrypted password
    public String getEncryptedPasswd() {
        return encryptedPassword;
    }

    // Setter for encrypted password (in case password reset is needed)
    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    // Check if the provided plain text password matches the encrypted one
    public boolean checkPassword(String plainTextPassword, Cipher cipher) {
        // Use Cipher class to check if the provided password matches the encrypted one
        return cipher.checkPassword(encryptedPassword, plainTextPassword);
    }
    public void displayMenu() {
        System.out.println("1. Book a ticket");
        System.out.println("2. View bookings");
        System.out.println("Enter 'exit' to log out.");
    }
}

