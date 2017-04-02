package com.overseer.model;

import lombok.*;


import java.util.List;

/**
 * The <code>Request</code> class represents subscribers of request {@link Request}.
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuppressWarnings("PMD.UnusedPrivateField")
public class RequestSubscribers extends AbstractEntity {
    private Request request;
    private List<User> userList;
}
