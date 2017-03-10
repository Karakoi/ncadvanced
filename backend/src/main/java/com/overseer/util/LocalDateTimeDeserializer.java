package com.overseer.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Demoralizes json string to {@link LocalDateTime} date.
 */
public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

    @Override
    public LocalDateTime deserialize(JsonParser json,
                                     DeserializationContext ctxt) throws IOException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMMM d, yyyy HH:mm:ss");
        return LocalDateTime.parse(json.getText(), dtf);
    }
}
