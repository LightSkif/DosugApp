package com.dosug.app.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by radmir on 22.05.17.
 */
public class LocalDateSerializer  extends JsonSerializer<LocalDate> {

    final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    @Override
    public void serialize(LocalDate date, JsonGenerator jsonGenerator, SerializerProvider arg2) throws IOException {

        jsonGenerator.writeString(date.format(formatter));
    }
}