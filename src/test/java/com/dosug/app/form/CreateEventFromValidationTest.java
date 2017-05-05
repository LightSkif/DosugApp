package com.dosug.app.form;

import org.junit.Before;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public class CreateEventFromValidationTest {

    public static final String NORMAL_EVENTNAME = "Concert";

    public static final String NORMAL_CONTENT = "Powerwolf is a German power metal band created in 2003";

    public static final String PLACE_NAME = "Berlin";

    public static final double NORMAL_LONGITUDE = 5.5;

    public static final double NORMAL_LATITUDE = 5.5;


    private Validator validator;

    private CreateEventForm form;

    @Before
    public void init() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();

        this.form = new CreateEventForm();
        form.setEventName(NORMAL_EVENTNAME);
        form.setContent(NORMAL_CONTENT);
    }
}
