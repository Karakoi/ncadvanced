package com.overseer.util;

import org.apache.commons.lang3.RandomStringUtils;

import java.security.SecureRandom;
import java.util.Random;

/**
 * Utility for generating temporary password.
 */
public class PasswordGeneratorUtil {
    private static final String PASSWORD_CHARACTERS =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~`!@#$%^&*()-_=+[{]}\\|;:\'\",<.>/?";

    private static final Integer PASSWORD_LENGTH = 16;

    /**
     * Generates temporary password.
     *
     * @return generated password
     */
    public String generatePassword() {
        char[] possibleCharacters = PASSWORD_CHARACTERS.toCharArray();
        int start = 0;
        int end = possibleCharacters.length - 1;
        Random random = new SecureRandom();
        return RandomStringUtils.random(PASSWORD_LENGTH, start, end, false, false, possibleCharacters, random);
    }
}
