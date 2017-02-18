package com.overseer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class HistoryDetail extends AbstractEntity {

    private boolean actual;

    private LocalDate dateOfLastChange;

    private Long changerId;

}
