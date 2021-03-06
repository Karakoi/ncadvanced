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
public class DeadlineDTO {
    private Long id;
    private String title;
    private LocalDate deadline;
}
