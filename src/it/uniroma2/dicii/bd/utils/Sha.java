package it.uniroma2.dicii.bd.utils;

import java.security.MessageDigest;

/**
 * Simple class used to expose the SHA encryption method.
 *
 * @author  https://stackoverflow.com/a/11009612
 */

public class Sha {

    /**
     * Returns the string supplied as parameter,
     * encrypted with the sha-256 algorithm
     *
     * @param  stringToEncrypt string to encrypt
     * @return The string encrypted
     */
    public static String sha256(String stringToEncrypt) {

        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(stringToEncrypt.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();

        } catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }
}
