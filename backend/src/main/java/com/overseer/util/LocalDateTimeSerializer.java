package com.overseer.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Serializes {@link LocalDateTime} date to json string.
 */
public class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {

    @Override
    public void serialize(LocalDateTime dateTime,
                          JsonGenerator json,
                          SerializerProvider serializers) throws IOException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMMM d, yyyy HH:mm:ss");
        String date = dateTime.format(dtf);
        json.writeString(date);
    }
}
