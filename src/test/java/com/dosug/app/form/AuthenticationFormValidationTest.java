package com.dosug.app.form;

import org.junit.Before;
import org.junit.Test;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by radmir on 25.03.17.
 */
public class AuthenticationFormValidationTest {

    public static final String NORMAL_PASSWORD = "password";
    public static final String NORMAL_USERNAME = "username";
    private Validator validator;

    /**
     * form init by username=NORMAL_USERNAME and password=NORMAL_PASSWORD
     */
    private AuthenticationForm form;

    @Before
    public void init() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();

        this.form = new AuthenticationForm();
        form.setUsername(NORMAL_USERNAME);
        form.setPassword(NORMAL_PASSWORD);
    }

    @Test public void testUsernameNullability() {
        form.setUsername(null);

        //username is null
        notValid();
    }

    @Test public void testPasswordNullability() {
        form.setPassword(null);

        //password is null
        notValid();
    }

    @Test public void testOnEmptyFields() {
        form.setUsername("");
        form.setPassword("");

        //username and password is empty
        notValid();
    }

    @Test public void testTooLongUsername() {
        form.setUsername(
                getStringWithLength(AuthenticationForm.USERNAME_MAX_SYMBOLS + 1));

        notValid();
    }

    @Test public void testTooLongPassword() {
        form.setPassword(
                getStringWithLength(AuthenticationForm.PASSWORD_MAX_SYMBOLS + 1));

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
        form.setPassword("password12886508");

        valid();
    }

    @Test public void testPasswordCanHaveUnderScore() {
        form.setPassword("pass_word");

        valid();
    }

    @Test public void testPasswordCanHaveCapitalLetters() {
        form.setPassword("passwordWithCapitalLetters");

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

    //assert if form is valid
    private void notValid() {
        assertFalse(validator.validate(form).isEmpty());
    }

    //assert if form invalid
    private void valid() {
        assertTrue(validator.validate(form).isEmpty());
    }
}
