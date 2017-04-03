package com.overseer.dto;

import com.overseer.model.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;


/**
 * Class helper for transfer history message.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@SuppressWarnings("PMD.UnusedPrivateField")
public class HistoryMessageDTO extends AbstractEntity {

    @NotNull(message = "History record has to have a message")
    private String message;

    private String longMessage;

    @NotNull(message = "History has to have a changer id")
    private Long changerId;

    @NotNull(message = "History has to have a changer`s first name")
    private String changerFirstName;

    @NotNull(message = "History has to have a changer`s last name")
    private String changerLastName;

    @NotNull(message = "History has to have a date of change")
    private LocalDateTime dateOfChange;
}
