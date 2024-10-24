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
    // Constructor for creating a new customer (password is encrypted and stored)
    public Customer(String username, String email, String plainTextPassword, Cipher cipher) {
        this.name = username;
        this.email = email;
        this.encryptedPassword = cipher.encryptMessage(plainTextPassword);  // Encrypt the password upon user creation
    }

    // Constructor for loading a customer from the database (password is already encrypted)
    public Customer(String username, String email, String encryptedPassword) {
        this.name = username;
        this.email = email;
        this.encryptedPassword = encryptedPassword;  // Already encrypted password from database
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
    public boolean checkPassword(String inputPassword, Cipher cipher) {
        // Use Cipher class to check if the provided password matches the decrypted one
        return cipher.checkPassword(this.name, inputPassword);
    }
    public void displayMenu() {
        System.out.println("1. Book a ticket");
        System.out.println("2. View bookings");
        System.out.println("Enter 'exit' to log out.");
    }
}

