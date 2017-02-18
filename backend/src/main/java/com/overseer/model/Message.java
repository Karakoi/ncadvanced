package com.overseer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import java.time.LocalDateTime;

/**
 * Message entity.
 */
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@SuppressWarnings("PMD.UnusedPrivateField")
public class Message extends AbstractEntity{

    private final int senderId;

    private Long recipientId;

    private Long topicId;

    @NonNull
    private String text;

    private LocalDateTime dateAndTime;

}
