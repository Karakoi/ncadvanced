package com.overseer.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.util.Locale;

/**
 * The <code>ProgressStatus</code> enumeration represents the {@link Request} life cycle.
 */
@JsonSerialize(using = ToStringSerializer.class)
public enum  ProgressStatus {

    /**
     * The {@link Request} has just been created by employee.
     */
    REGISTERED,

    /**
     * The {@link Request} is shown in office manager's list of unassigned requests.
     */
    FREE,

    /**
     * Office manager moved the {@link Request} to {@link JoinedRequest}.
     */
    JOINED,

    /**
     * Office manager assigned the {@link Request} and started to work on it.
     */
    IN_PROGRESS,

    /**
     * Office manager implemented the {@link Request}.
     */
    CLOSED,

    /**
     * Office manager/administrator/employee decided that {@link Request} is not implemented and
     * reopened it.
     */
    REOPENED;

    @Override
    public String toString() {
        return name().toLowerCase(Locale.ENGLISH);
    }
}
