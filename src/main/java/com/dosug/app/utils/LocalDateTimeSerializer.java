package com.dosug.app.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {

    final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");

    @Override
    public void serialize(LocalDateTime date, JsonGenerator jsonGenerator, SerializerProvider arg2) throws IOException {
        
        jsonGenerator.writeString(date.format(formatter));
    }
}
