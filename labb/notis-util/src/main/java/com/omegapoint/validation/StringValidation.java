package com.omegapoint.validation;

import com.omegapoint.exceptions.ValidationException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringValidation {

    private static final String IP_PATTERN = "^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";

    /**
     *
     * @param str The string to be validated
     * @param pattern The regex pattern to be matched
     * @param errorMessage The error message to be in the exception
     */
    public static void validateString(String str, String pattern, String errorMessage) throws ValidationException {
        if (!validate(str, pattern)) {
            throw new ValidationException(errorMessage);
        }
    }

    /**
     * Validate an IP address
     * @param ip The IP to be validated
     */
    public static void validateIP(String ip) throws ValidationException {
        if (!validate(ip, IP_PATTERN)) {
            throw new ValidationException("IP address format is wrong!");
        }
    }

    private static boolean validate(String str, String pattern) {
        Pattern ptrn = Pattern.compile(pattern);
        Matcher matcher = ptrn.matcher(str);
        return matcher.find();
    }
}
