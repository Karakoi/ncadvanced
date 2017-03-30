package com.overseer.dao;

import com.overseer.model.Request;
import com.overseer.model.User;

import java.util.List;

/**
 * The <code>RequestDao</code> interface represents access list of subscribers {@link User} of request {@link Request}.
 */
public interface RequestSubscribersDao {
    boolean exists(Long subscriberId, Long requestId);

    void save(Long subscriberId, Long requestId);

    void delete(Long subscriberId, Long requestId);

    List<User> getSubscribersOfRequest(Long requestId);

    default List<User> getSubscribersOfRequest(Request request) {
        return getSubscribersOfRequest(request.getId());
    }
}
