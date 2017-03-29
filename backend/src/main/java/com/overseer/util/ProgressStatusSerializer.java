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
        if(!progressStatus.equals(ProgressStatus.NULL)){
            jsonGenerator.writeNumber(progressStatus.getId());
        } else {
            jsonGenerator.writeNumber(0);
        }
        jsonGenerator.writeFieldName("name");
        if(!progressStatus.equals(ProgressStatus.NULL)){
            String name = progressStatus.getName();
            name = name.toLowerCase();
            name = name.replaceAll("_", " ");
            name = name.toUpperCase().charAt(0) + name.substring(1);
            jsonGenerator.writeString(name);
        } else {
            jsonGenerator.writeString("");
        }
        jsonGenerator.writeFieldName("value");
        if(!progressStatus.equals(ProgressStatus.NULL)){
            jsonGenerator.writeNumber(progressStatus.getValue());
        } else {
            jsonGenerator.writeNumber(0);
        }
        jsonGenerator.writeEndObject();
    }
}
