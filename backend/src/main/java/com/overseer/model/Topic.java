package com.overseer.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * Topic entity.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@SuppressWarnings("PMD.UnusedPrivateField")
public class Topic extends AbstractEntity {
    private static final int MIN_LENGTH = 3;
    private static final int MAX_TITLE_LENGTH = 20;
    private static final int MAX_DESCRIPTION_LENGTH = 500;

    @Size(min = MIN_LENGTH, max = MAX_TITLE_LENGTH)
    private String title;

    @Size(min = MIN_LENGTH, max = MAX_DESCRIPTION_LENGTH)
    private String description;

    private List<Role> roles = new ArrayList<>();
}
