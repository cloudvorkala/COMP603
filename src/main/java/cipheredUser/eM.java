
package cipheredUser;
import java.math.BigInteger;
import java.security.SecureRandom;

/**
 *
 * @author C.V
 */

// rsa encrypt and  padding
public class eM {
    protected static String eM(String message, BigInteger e, BigInteger n) {
        StringBuilder enm = new StringBuilder();
        SecureRandom rand = new SecureRandom();
        
        for (char a : message.toCharArray()) {
            // add a padding with random in 100
            int randomPadding = rand.nextInt(100);
            BigInteger paddedChar = BigInteger.valueOf((int) a).multiply(BigInteger.valueOf(100)).add(BigInteger.valueOf(randomPadding));
            BigInteger enc = paddedChar.modPow(e, n);
            enm.append(enc).append(" ");
        }
        
        return enm.toString().trim();
    }

    protected static String dm(String message, BigInteger d, BigInteger n) {
        StringBuilder dem = new StringBuilder();
        String[] enV = message.split(" ");
        
        for (String a : enV) {
            BigInteger c = new BigInteger(a);
            BigInteger paddedChar = c.modPow(d, n);
            //remove random padding
            int originalCharValue = paddedChar.divide(BigInteger.valueOf(100)).intValue();
            dem.append((char) originalCharValue);
        }
        
        return dem.toString();
    }
}
