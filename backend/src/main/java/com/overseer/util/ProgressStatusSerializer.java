package com.overseer.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.overseer.model.enums.ProgressStatus;

import java.io.IOException;

/**
 * Serializes {@link ProgressStatus} to json object.
 */
public class ProgressStatusSerializer extends JsonSerializer<ProgressStatus> {

    @Override
    public void serialize(ProgressStatus progressStatus, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeFieldName("id");
        jsonGenerator.writeNumber(progressStatus.getId());

        jsonGenerator.writeFieldName("name");
        String name = progressStatus.getName();
        name = name.toLowerCase();
        name = name.replaceAll("_", " ");
        name = name.toUpperCase().charAt(0) + name.substring(1);
        jsonGenerator.writeString(name);

        jsonGenerator.writeFieldName("value");
        jsonGenerator.writeNumber(progressStatus.getValue());
        jsonGenerator.writeEndObject();
    }
}
