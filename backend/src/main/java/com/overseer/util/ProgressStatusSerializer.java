package com.overseer.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.overseer.model.enums.ProgressStatus;

import java.io.IOException;

/**
 * Serializes {@link ProgressStatus} to json object.
 */
public class ProgressStatusSerializer extends JsonSerializer<ProgressStatus> {

    @Override
    public void serialize(ProgressStatus progressStatus, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

        jsonGenerator.writeStartObject();

        jsonGenerator.writeFieldName("id");
        if (progressStatus.getId() != null) {
            jsonGenerator.writeNumber(progressStatus.getId());
        } else {
            jsonGenerator.writeNumber(0L);
        }

        jsonGenerator.writeFieldName("name");
        jsonGenerator.writeString(progressStatus.getName());

        jsonGenerator.writeFieldName("value");
        jsonGenerator.writeNumber(progressStatus.getValue());

        jsonGenerator.writeEndObject();
    }
}
