package com.overseer.model;

/**
 * Base class for the entities stored in the database, which should contain the id.
 */
class AbstractEntity {

    private Long id;

    AbstractEntity() {
        this.id = null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
