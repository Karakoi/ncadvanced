package com.overseer.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * HistoryDetail entity.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@SuppressWarnings("PMD.UnusedPrivateField")
public class HistoryDetail extends AbstractEntity {

    private boolean actual;

    private LocalDate dateOfLastChange;

    private Long changerId;

}
