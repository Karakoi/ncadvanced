package com.overseer.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * HistoryDetail entity.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@SuppressWarnings("PMD.UnusedPrivateField")
public class History extends AbstractEntity {

    private String columnName;

    private String modifiedVaule;

    private LocalDateTime dateOfLastChange;

    private User changer;

    private Long recordId;
}
