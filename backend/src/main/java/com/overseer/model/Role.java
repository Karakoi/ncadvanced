package com.overseer.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.util.Locale;

/**
 * The <code>Role</code> enumeration describes user system roles.
 */
@JsonSerialize(using = ToStringSerializer.class)
public enum Role {
    /**
     * An administrator can perform CRUD operations on other administrators,
     * but cannot delete himself. Able to perform Update/Delete operations on
     * already created {@link Request} and can perform all actions a manager can do.
     */
    ADMINISTRATOR,

    /**
     * An office manager can initialize {@link SubRequest}, join requests by
     * similar type {@link JoinedRequest}, change the {@link PriorityStatus} of the request.
     * Responsible for evaluation request's {@link ProgressStatus} as well as for
     * completing and closing the {@link Request}.
     */
    MANAGER,

    /**
     * An employee can perform CRUD operations on the {@link Request} object.
     */
    EMPLOYEE;

    @Override
    public String toString() {
        return name().toLowerCase(Locale.ENGLISH);
    }
}

