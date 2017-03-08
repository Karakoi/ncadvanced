package com.overseer.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import lombok.val;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Demoralizing date.
 */
public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

    @Override
    public LocalDateTime deserialize(JsonParser json, DeserializationContext ctxt) throws IOException {
        val dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return LocalDateTime.from(LocalDate.parse(json.getText(), dtf).atStartOfDay());
    }
}
