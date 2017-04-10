package com.dosug.app.form;

import org.junit.Before;
import org.junit.Test;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by radmir on 10.04.17.
 */
public class RegistrationFormValidationTest {


    private static final int USERNAME_MAX_SYMBOLS = 256;

    private static final String NORMAL_PASSWORD = "password";
    private static final String NORMAL_USERNAME = "username";
    private static final String NORMAL_EMAIL = "email@email.com";
    public static final int PASSWORD_MAX_LENGHT = 256;
    public static final int EMAIL_MAX_LENGTH = 256;

    private Validator validator;

    /**
     * form init by username=NORMAL_USERNAME and password=NORMAL_PASSWORD
     */
    private RegistrationForm form;

    @Before
    public void init() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();

        this.form = new RegistrationForm();
        form.setUsername(NORMAL_USERNAME);
        form.setPassword(NORMAL_PASSWORD);
        form.setPasswordRetry(NORMAL_PASSWORD);
        form.setEmail(NORMAL_EMAIL);
    }

    @Test
    public void testUsernameNullability() {
        form.setUsername(null);
        //username is null
        notValid();
    }

    @Test public void testPasswordNullability() {
        form.setPassword(null);
        form.setPasswordRetry(null);
        //password is null
        notValid();
    }

    @Test public void testOnEmptyFields() {
        form.setUsername("");
        form.setPassword("");
        form.setPasswordRetry("");
        form.setEmail("");
        //all fields is empty is empty
        notValid();
    }

    @Test public void testTooLongUsername() {
        form.setUsername(
                getStringWithLength(USERNAME_MAX_SYMBOLS + 1));

        notValid();
    }

    @Test public void testTooLongPassword() {
        form.setPassword(
                getStringWithLength(PASSWORD_MAX_LENGHT + 1));

        notValid();
    }


    @Test public void testUsernameHaveNotSpaces() {
        form.setUsername("use r");

        notValid();
    }


    @Test public void testUsernameCanHaveDigits() {
        form.setUsername("username123456789");

        valid();
    }

    @Test public void testUsernameCanHaveHyphen() {
        form.setUsername("user-name");
        valid();
    }

    @Test public void testUsernameCanHaveUnderScore() {
        form.setUsername("user_name");
        valid();
    }

    @Test public void testNotAllowedSymbolsInUsername() {
        String username = "username";

        char[] notAllowedSymbols = {',', '.', '!', '?', '/', '`', '~', 'ш', 'б', '<', '\''};

        for (char symbol: notAllowedSymbols) {
            form.setUsername(username + symbol);
            notValid();
        }
    }

    @Test public void testPasswordCanHaveDigits() {
        String passwordWithDigits = "password12886508";
        form.setPassword(passwordWithDigits);
        form.setPasswordRetry(passwordWithDigits);
        valid();
    }

    @Test public void testRetryPasswordNullability() {
        form.setPasswordRetry(null);

        notValid();
    }

    @Test public void testPasswordCanHaveUnderScore() {
        String validPassword = "pass_word";
        form.setPassword(validPassword);
        form.setPasswordRetry(validPassword);
        valid();
    }

    @Test public void testPasswordCanHaveCapitalLetters() {
        String passwordWithCapitalLetters = "passwordWithCapitalLetters";
        form.setPassword(passwordWithCapitalLetters);
        form.setPasswordRetry(passwordWithCapitalLetters);
        valid();
    }

    @Test public void testNotAllowedSymbolsInPassword() {
        String password = "password";

        char[] notAllowedSymbols = {'-', '/', '\\', ',', '`', 'г', ',', 'ш', '"'};

        for (char symbol : notAllowedSymbols) {
            form.setPassword(password + symbol);

            notValid();
        }
    }

    @Test public void testPasswordNotHaveSpace() {
        form.setPassword("pass word with space");
        notValid();
    }

    private String getStringWithLength(int length) {
        // я знаю что не оптимально но работает отлично и этого достаточно
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            builder.append("a");
        }
        return builder.toString();
    }

    @Test public void testPasswordAndRetryNotEquals() {
        form.setPassword("pass");
        form.setPasswordRetry("otherPass");

        notValid();
    }

    @Test public void testEmailNullability() {
        form.setEmail(null);

        notValid();
    }

    @Test public void testEmailMaxLength() {
        form.setEmail(getStringWithLength(EMAIL_MAX_LENGTH) + 1);

        notValid();
    }

    @Test public void testEmptyEmail() {
        form.setEmail("");
    }


    /**
     * все случаи не тестятся из-за того что я не использовал регулярку,
     * а тестить библиотечную аннотацию @Email не охота
     */
    @Test public void testNotValidEmail() {
        form.setEmail("email.com");

        notValid();
    }

    //assert if form is valid
    private void notValid() {
        assertFalse(validator.validate(form).isEmpty());
    }

    //assert if form invalid
    private void valid() {
        assertTrue(validator.validate(form).isEmpty());
    }
}
