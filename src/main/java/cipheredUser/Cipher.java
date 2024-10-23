package cipheredUser;

import java.math.BigInteger;
import java.security.SecureRandom;

public class Cipher {
    private BigInteger n, e, d;

    public Cipher() {
        generateKeys();  // 在构造函数中生成加密密钥
    }

    // 生成 RSA 密钥对
    private void generateKeys() {
        SecureRandom random = new SecureRandom();
        BigInteger p = BigInteger.probablePrime(64, random);
        BigInteger q = BigInteger.probablePrime(64, random);
        e = BigInteger.probablePrime(64, random);
        n = p.multiply(q);
        d = e.modInverse(p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE)));
    }

    // 加密消息
    public String encryptMessage(String input) {
        BigInteger message = new BigInteger(input.getBytes());
        return message.modPow(e, n).toString();  // 使用 RSA 公钥加密消息
    }

    // 解密消息
    public String decryptMessage(String input) {
        BigInteger encryptedMessage = new BigInteger(input);
        return new String(encryptedMessage.modPow(d, n).toByteArray());  // 使用 RSA 私钥解密消息
    }

    // 密码验证方法（只是验证给定密码是否与加密存储的密码匹配）
    public boolean checkPasswd(String storedEncryptedPassword, String inputPassword) {
        String encryptedInput = encryptMessage(inputPassword);
        return storedEncryptedPassword.equals(encryptedInput);
    }
}