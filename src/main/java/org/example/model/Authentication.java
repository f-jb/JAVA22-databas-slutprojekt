package org.example.model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Authentication {
    public static byte[] stringToByteArray(String pw) {
        char[] pwCharred = pw.toCharArray();
        byte[] tmpArr = new byte[pwCharred.length];
        for (int i = 0; i < pwCharred.length; i++) {
            tmpArr[i] = (byte) pwCharred[i];
        }
        return tmpArr;
    }

    public static byte[] hashPw(byte[] pw, byte[] salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(pw);
            md.update(salt);
            return md.digest();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] generateSalt() {
        SecureRandom rand = new SecureRandom();
        byte[] salt = new byte[8];
        rand.nextBytes(salt);
        return salt;
    }

    public static boolean comparePassword(byte[] providedPassword, byte[] storedPassword) {
        return MessageDigest.isEqual(providedPassword, storedPassword);
    }
}

