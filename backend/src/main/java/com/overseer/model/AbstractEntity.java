package com.overseer.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Base class for the entities stored in the database, which should contain the id.
 */
@Getter
@Setter
@ToString
@SuppressWarnings("PMD.UnusedPrivateField")
public class AbstractEntity {
    private Long id;

    public boolean isNew() {
        return this.id == null;
    }
}
