package com.overseer.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.*;

import java.time.LocalDate;

/**
 * Class helper for transfer request's entity objects.
 */
@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, isGetterVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.ANY)
@SuppressWarnings("PMD.UnusedPrivateField")
public class RequestDTO {
    private Long count;
    private LocalDate startDateLimit;
    private LocalDate endDateLimit;
}
