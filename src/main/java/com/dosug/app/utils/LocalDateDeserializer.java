package com.dosug.app.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by radmir on 23.05.17.
 */
public class LocalDateDeserializer extends JsonDeserializer<LocalDate> {

    final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");


    @Override
    public LocalDate deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        try {
            return LocalDate.parse(jsonParser.getText(), formatter);
        } catch (Exception e) {
            // если не смогли десериализовать
            if(jsonParser.getText().equals("")) {
                return null;
            }
            throw e;
        }

    }

}
