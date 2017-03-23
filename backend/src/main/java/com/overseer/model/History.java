package com.overseer.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * HistoryDetail entity.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@SuppressWarnings("PMD.UnusedPrivateField")
public class History extends AbstractEntity {
    private static final int MAX_COLUMN_NAME_LENGTH = 45;
    private static final int MAX_VALUE_INFO_LENGTH = 200;

    @NotNull(message = "History has to have a column name")
    @Size(max = MAX_COLUMN_NAME_LENGTH, message = "Size of column name has not be longer than " + MAX_COLUMN_NAME_LENGTH)
    private String columnName;

    @Size(max = MAX_VALUE_INFO_LENGTH, message = "Size of old value has not be longer than " + MAX_VALUE_INFO_LENGTH)
    private String oldValue;

    @Size(max = MAX_VALUE_INFO_LENGTH, message = "Size of new value has not be longer than " + MAX_VALUE_INFO_LENGTH)
    private String newValue;

    @Size(max = MAX_VALUE_INFO_LENGTH, message = "Size of demonstration of old value has not be longer than " + MAX_VALUE_INFO_LENGTH)
    private String demonstrationOfOldValue;

    @Size(max = MAX_VALUE_INFO_LENGTH, message = "Size of demonstration of new value has not be longer than " + MAX_VALUE_INFO_LENGTH)
    private String demonstrationOfNewValue;

    @NotNull(message = "History has to have a change date")
    private LocalDateTime dateOfChange;

    @NotNull(message = "History has to have a changer")
    private User changer;

    @NotNull(message = "History has to belongs to something")
    private Long recordId;
}
