package com.overseer.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Message entity.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@SuppressWarnings("PMD.UnusedPrivateField")
public class Message extends AbstractEntity{

    @NotNull(message = "Message has to have a sender")
    private User sender;

    private User recipient;

    private Topic topic;

    @NotNull(message = "Message has to have a text")
    private String text;

    @NotNull(message = "Message has to have a time of creation")
    private LocalDateTime creationDateTime;

}
