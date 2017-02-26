package com.overseer.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * HistoryDetail entity.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@SuppressWarnings("PMD.UnusedPrivateField")
public class History extends AbstractEntity {

    @NotNull(message = "History has to have a column name")
    private String columnName;

    @NotNull(message = "History has to have a old value")
    private String modifiedValue;

    @NotNull(message = "History has to have a change date")
    private LocalDateTime dateOfLastChange;

    @NotNull(message = "History has to have a changer")
    private User changer;

    @NotNull(message = "History has to belongs to something")
    private Long recordId;
}
