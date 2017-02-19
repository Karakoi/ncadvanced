package com.overseer.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.util.Locale;

/**
 * The <code>PriorityStatus</code> class represents priority of the {@link Request}.
 */
@JsonSerialize(using = ToStringSerializer.class)
public enum PriorityStatus {

    /**
     * Represents an urgent {@link Request}.
     */
    HIGH,

    /**
     * Represents an average priority {@link Request}.
     */
    NORMAL,

    /**
     * Represents a postponed {@link Request}.
     */
    LOW;

    @Override
    public String toString() {
        return name().toLowerCase(Locale.ENGLISH);
    }
}
