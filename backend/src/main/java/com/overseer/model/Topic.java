package com.overseer.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import java.util.List;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Topic extends AbstractEntity {

    @NonNull
    private String title;

    List<Integer> messagesId;

    @NonNull
    private Long forumId;

}
