package com.overseer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

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
    @JsonProperty("admin")
    ADMINISTRATOR("admin"),

    /**
     * An office manager can initialize {@link SubRequest}, join requests by
     * similar type {@link JoinedRequest}, change the {@link PriorityStatus} of the request.
     * Responsible for evaluation request's {@link ProgressStatus} as well as for
     * completing and closing the {@link Request}.
     */
    @JsonProperty("office manager")
    MANAGER("office manager"),

    /**
     * An employee can perform CRUD operations on the {@link Request} object.
     */
    @JsonProperty("employee")
    EMPLOYEE("employee");
    
    private String custom;
    
    Role(String custom) {
        this.custom = custom;
    }
    
    /**
     * Returns role according to custom string representation of the role.
     */
    public static Role getValueFromString(String text) {
        for (Role role : Role.values()) {
            if (role.custom.equals(text)) {
                return role;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return custom;
    }
    

}

