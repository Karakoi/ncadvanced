package com.overseer.model;

import lombok.Data;
import lombok.EqualsAndHashCode;


import java.util.List;

/**
 * The <code>Request</code> class represents subscribers of request {@link Request}.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@SuppressWarnings("PMD.UnusedPrivateField")
public class RequestSubscribers extends AbstractEntity {
    private Request request;
    private List<User> userList;
}
