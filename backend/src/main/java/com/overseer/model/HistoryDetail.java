package com.overseer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * HistoryDetail entity.
 */
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@SuppressWarnings("PMD.UnusedPrivateField")
public class HistoryDetail extends AbstractEntity {

    private boolean actual;

    private LocalDate dateOfLastChange;

    private Long changerId;

}
