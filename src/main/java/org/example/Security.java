package org.example;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public abstract class Security {
    public String getMd5(String input) throws Exception{
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());

            BigInteger no = new BigInteger(1, messageDigest);

            String hashvalue = no.toString(16);
            while (hashvalue.length() < 32) {
                hashvalue = "0" + hashvalue;
            }
            return hashvalue;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        //reference:https://www.geeksforgeeks.org/md5-hash-in-java/
    }
}
