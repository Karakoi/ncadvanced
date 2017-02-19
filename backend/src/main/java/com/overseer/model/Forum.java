package com.overseer.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import java.util.List;

/**
 * Forum entity.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@SuppressWarnings("PMD.UnusedPrivateField")
public class Forum extends AbstractEntity {

    @NonNull
    private String title;

    private List<Long> topicsId;

}
