package com.dosug.app.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.Duration;


public class DurationSerializer extends JsonSerializer<Duration> {

    @Override
    public void serialize(Duration duration, JsonGenerator jsonGenerator, SerializerProvider arg2) throws IOException {

        jsonGenerator.writeString(duration.toString());
    }
}
