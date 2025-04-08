package com.tregubov.firstserver.securiry;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordEncryptor {

    public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }

}
