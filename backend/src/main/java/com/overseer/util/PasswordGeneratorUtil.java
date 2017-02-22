package com.overseer.util;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

import java.security.SecureRandom;
import java.util.Random;

/**
 * Utility for generating temporary password.
 */
@PropertySource("classpath:security.properties")
public class PasswordGeneratorUtil {
    private static final String PASSWORD_CHARACTERS =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~`!@#$%^&*()-_=+[{]}\\|;:\'\",<.>/?";
    @Value("${password.length}")
    private int passwordLength;

    /**
     * Generates temporary password.
     *
     * @return generated password
     */
    public String generatePassword() {
        char[] possibleCharacters = PASSWORD_CHARACTERS.toCharArray();
        int start = 0;
        int end = possibleCharacters.length - 1;
        boolean letters = false;
        boolean numbers = false;
        Random random = new SecureRandom();
        return RandomStringUtils.random(passwordLength, start, end, letters, numbers, possibleCharacters, random);
    }
}
