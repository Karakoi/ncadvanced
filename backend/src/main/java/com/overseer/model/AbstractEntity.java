package com.overseer.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Base class for the entities stored in the database, which should contain the id.
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@SuppressWarnings("PMD.UnusedPrivateField")
class AbstractEntity {
    private Long id;
}
