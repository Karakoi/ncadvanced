package com.overseer.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Base class for the entities stored in the database, which should contain the id.
 */
@Getter
@Setter
@SuppressWarnings("PMD.UnusedPrivateField")
class AbstractEntity {
    private Long id;
}
