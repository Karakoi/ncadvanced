package com.overseer.model;

import lombok.Data;


import java.util.List;

/**
 * The <code>Request</code> class represents subscribers of request {@link Request}.
 */
@Data
@SuppressWarnings("PMD.UnusedPrivateField")
public class RequestSubscribers extends AbstractEntity {
    private Request request;
    private List<User> userList;
}
