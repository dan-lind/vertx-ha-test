package com.omegapoint.validation;

import com.omegapoint.exceptions.ValidationException;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TestStringValidation {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void testValidateCorrectIP() throws ValidationException {
        String ip = "1.2.3.4";
        StringValidation.validateIP(ip);
    }

    @Test
    public void testValidateIncorrectIP() throws ValidationException {
        String ip = "1.2.3.4444";
        exception.expect(ValidationException.class);
        StringValidation.validateIP(ip);
    }

    @Test
    public void testValidateStringContainsOnlyDigits() throws ValidationException {
        String str = "123456789";
        StringValidation.validateString(str, "^\\d+$", "The string does not contains only digits!");
    }

    @Test
    public void testValidateStringContainsNotOnlyDigits() throws ValidationException {
        String str = "123456789ab";
        exception.expect(ValidationException.class);
        StringValidation.validateString(str, "^\\d+$", "The string does not contains only digits!");
    }
}
