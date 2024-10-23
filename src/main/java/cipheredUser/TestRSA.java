package cipheredUser;

/**
 *
 * @author cloud
 

import java.math.BigInteger;
import java.util.Scanner;

public class TestRSA {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        Cipher a = new Cipher();
        
        

        System.out.println("Enter the encrypted message (space-separated integers):");
        String input = scanner.nextLine();
        String encryptedMessage = a.encryptMessage(input);
        System.out.println("Encrypted Message:"+ encryptedMessage);
        
        

        String decryptedMessage = a.decryptMessage(encryptedMessage);

        System.out.println("Decrypted Message: " + decryptedMessage);

        scanner.close();
    }
}
* */