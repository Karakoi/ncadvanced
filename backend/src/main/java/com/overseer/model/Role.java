package com.overseer.model;

import com.fasterxml.jackson.annotation.JsonProperty;


import java.util.Locale;

/**
 * The <code>Role</code> enumeration describes user system roles.
 */

public enum Role {
    /**
     * An administrator can perform CRUD operations on other administrators,
     * but cannot delete himself. Able to perform Update/Delete operations on
     * already created {@link Request} and can perform all actions a manager can do.
     */
    @JsonProperty("administrator")
    ADMINISTRATOR,

    /**
     * An office manager can initialize {@link SubRequest}, join requests by
     * similar type {@link JoinedRequest}, change the {@link PriorityStatus} of the request.
     * Responsible for evaluation request's {@link ProgressStatus} as well as for
     * completing and closing the {@link Request}.
     */
    @JsonProperty("manager")
    MANAGER,

    /**
     * An employee can perform CRUD operations on the {@link Request} object.
     */
    @JsonProperty("employee")
    EMPLOYEE;


    @Override
    public String toString() {
        return name().toLowerCase(Locale.ENGLISH);
    }
}

