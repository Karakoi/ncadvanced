package com.overseer.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

/**
 * HistoryDetail entity.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@SuppressWarnings("PMD.UnusedPrivateField")
public class History extends AbstractEntity {
    private static final int MAX_COLUMN_NAME_LENGTH = 20;

    @NotNull(message = "History has to have a column name")
    private String columnName;

    @NotNull(message = "History has to have an old value")
    private String oldValue;

    @NotNull(message = "History has to have a new value")
    private String newValue;

    @NotNull(message = "History has to have a change date")
    private LocalDateTime dateOfLastChange;

    @NotNull(message = "History has to have a changer")
    private User changer;

    @NotNull(message = "History has to belongs to something")
    private Long recordId;
}
