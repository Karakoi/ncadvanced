package com.overseer.dao;

import com.overseer.model.Request;
import com.overseer.model.User;

import java.util.List;

/**
 * The <code>RequestDao</code> interface represents access list of subscribers {@link User} of request {@link Request}.
 */
public interface RequestSubscribersDao {

    void save(User user, Request request);

    void delete(User user, Request request);

    List<User> getSubscribersOfRequest(Long requestId);

    default List<User> getSubscribersOfRequest(Request request) {
        return getSubscribersOfRequest(request.getId());
    }
}
