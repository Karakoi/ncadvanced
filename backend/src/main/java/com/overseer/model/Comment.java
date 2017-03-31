package com.overseer.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Comment entity.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@SuppressWarnings("PMD.UnusedPrivateField")
public class Comment extends AbstractEntity {

    private static final int MIN_MESSAGE_LENGTH = 2;
    private static final int MAX_MESSAGE_LENGTH = 500;

    @NotNull(message = "Message has to have a sender")
    private User sender;

    @NotNull(message = "Message has to have a request")
    private Request request;

    @Size(min = MIN_MESSAGE_LENGTH, max = MAX_MESSAGE_LENGTH)
    @NotNull(message = "Message has to have a text")
    private String text;

    @NotNull(message = "Message has to have a time of creation")
    private LocalDateTime createDateAndTime;

    private LocalDateTime updateDateAndTime;
}
