package com.overseer.dao.impl;

import com.overseer.dao.RequestSubscribersDao;
import com.overseer.model.User;
import com.overseer.service.QueryService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * /**
 * <p>
 * Implementation of {@link RequestSubscribersDao} interface.
 * </p>
 */
@Repository
public class RequestSubscribersDaoImpl implements RequestSubscribersDao {

    @Autowired
    private QueryService queryService;

    @Autowired
    private NamedParameterJdbcOperations jdbc;


    @Override
    public boolean exists(Long subscriberId, Long requestId) {
        val parameter = new MapSqlParameterSource("subscriberId", subscriberId);
        parameter.addValue("requestId", requestId);
        return jdbc.queryForObject(
                queryService.getQuery("request.subscriber.exists"), parameter, Integer.class) == 1;
    }

    @Override
    public void save(Long subscriberId, Long requestId) {
        val parameter = new MapSqlParameterSource("subscriberId", subscriberId);
        parameter.addValue("requestId", requestId);
        String insertQuery = queryService.getQuery("request.subscribe");
        KeyHolder keyHolder = new GeneratedKeyHolder();
        this.jdbc.update(insertQuery, parameter, keyHolder, new String[]{"id"});
    }

    @Override
    public void delete(Long subscriberId, Long requestId) {
        String deleteQuery = queryService.getQuery("request.unsubscribe");
        val parameter = new MapSqlParameterSource("subscriberId", subscriberId);
        parameter.addValue("requestId", requestId);
        this.jdbc.update(deleteQuery, parameter);
    }

    @Override
    public List<User> getSubscribersOfRequest(Long requestId) {
        return jdbc.queryForObject(queryService.getQuery("request.subscribers"),
                new MapSqlParameterSource("requestId", requestId), getMapper());
    }


    protected RowMapper<List<User>> getMapper() {
        return (resultSet, i) -> {
            User firstUser = new User();
            firstUser.setFirstName(resultSet.getString("first_name"));
            firstUser.setLastName(resultSet.getString("last_name"));
            firstUser.setEmail(resultSet.getString("email"));
            List<User> list = new ArrayList<>();
            list.add(firstUser);
            while (resultSet.next()) {
                User user = new User();
                user.setFirstName(resultSet.getString("first_name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setEmail(resultSet.getString("email"));
                list.add(user);
            }
            return list;
        };
    }
}
