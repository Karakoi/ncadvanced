package com.overseer.dao.impl;

import com.overseer.dao.RequestSubscribersDao;
import com.overseer.model.Request;
import com.overseer.model.RequestSubscribers;
import com.overseer.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

/**
 * /**
 * <p>
 * Implementation of {@link RequestSubscribersDao} interface.
 * </p>
 */
public class RequestSubscribersDaoImpl implements RequestSubscribersDao {

    @Override
    public void save(User user, Request request) {

    }

    @Override
    public void delete(User user, Request request) {

    }

    @Override
    public List<User> getSubscribersOfRequest(Long requestId) {
        return null;
    }

    @Override
    public List<User> getSubscribersOfRequest(Request request) {
        return null;
    }


    protected RowMapper<RequestSubscribers> getMapper() {
        return (resultSet, i) -> {
            Request request = new Request();
            request.setId(resultSet.getLong("request_id"));
            RequestSubscribers requestSubscribers = new RequestSubscribers();
            requestSubscribers.setRequest(request);
            while (resultSet.next()){

            }
            return null;
        };
    }
}
