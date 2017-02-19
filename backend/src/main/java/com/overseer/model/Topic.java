package com.overseer.model;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import java.util.List;

/**
 * Topic entity.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@SuppressWarnings("PMD.UnusedPrivateField")
public class Topic extends AbstractEntity {

    @NonNull
    private String title;

    private List<Integer> messagesId;

    @NonNull
    private Long forumId;

}
