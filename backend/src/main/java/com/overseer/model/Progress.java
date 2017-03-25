package com.overseer.model;

/**
 * Enum that represents Progress Status of Request.
 */
public enum Progress {
    FREE(5L, 200L),
    JOINED(6L, 300L),
    IN_PROGRESS(7L, 400L),
    CLOSED(8L, 500L);

    private Long id;
    private Long value;

    Progress(Long id, Long value) {
        this.id = id;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public Long getValue() {
        return value;
    }
}
