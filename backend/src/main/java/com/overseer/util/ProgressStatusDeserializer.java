package com.overseer.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.overseer.model.enums.ProgressStatus;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

/**
 * Demoralizes json object to {@link ProgressStatus}.
 */
@RequiredArgsConstructor
public class ProgressStatusDeserializer extends JsonDeserializer<ProgressStatus> {

    private final ProgressStatusUtil progressStatusUtil;

    @Override
    public ProgressStatus deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode jsonNode = jsonParser.readValueAsTree();

        ProgressStatus progressStatus;
        if (jsonNode.has("id")) {
            Long id = jsonNode.get("id").asLong();
            progressStatus = progressStatusUtil.getProgressById(id);
        } else {
            progressStatus = ProgressStatus.NULL;
        }
        return progressStatus;
    }
}
