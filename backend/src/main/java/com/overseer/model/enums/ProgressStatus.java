package com.overseer.model.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.overseer.util.ProgressStatusDeserializer;
import com.overseer.util.ProgressStatusSerializer;

/**
 * The <code>ProgressStatus</code> enum represents progress status of request.
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@JsonDeserialize(using = ProgressStatusDeserializer.class)
@JsonSerialize(using = ProgressStatusSerializer.class)
public enum ProgressStatus {
    FREE(5L, 200L),
    JOINED(6L, 300L),
    IN_PROGRESS(7L, 400L),
    CLOSED(8L, 500L),
    NULL(null, 0L);

    private Long id;
    private Long value;

    ProgressStatus(Long id, Long value) {
        this.id = id;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public Long getValue() {
        return value;
    }

    /**
     * Method return changed progress name.
     */
    public String getName() {
        String newName = this.name();
        newName = newName.toLowerCase();
        newName = newName.replaceAll("_", " ");
        newName = newName.toUpperCase().charAt(0) + newName.substring(1);
        return newName;
    }
}