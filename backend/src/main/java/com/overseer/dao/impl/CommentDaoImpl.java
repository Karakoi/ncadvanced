package com.overseer.dao.impl;

import com.overseer.dao.CommentDao;
import com.overseer.model.*;
import com.overseer.service.QueryService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Implementation of {@link CommentDao} interface.
 */
@Transactional
@Repository
@RequiredArgsConstructor
public class CommentDaoImpl extends CrudDaoImpl<Comment> implements CommentDao {
    @Autowired
    private QueryService queryService;

    @Override
    public List<Comment> findByRequest(Long requestId) {
        val parameterSource = new MapSqlParameterSource("requestId", requestId);
        return jdbc().query(getByRequestQuery(), parameterSource, getMapper());
    }

    @Override
    public void deleteAllByRequest(Long requestId) {
        val deleteByRequestQuery = queryService.getQuery("comment.deleteByRequest");
        jdbc().update(deleteByRequestQuery, new MapSqlParameterSource("requestId", requestId));
    }

    @Override
    protected RowMapper<Comment> getMapper() {
        return (resultSet, i) -> {
            Role role = new Role(resultSet.getString("name"));

            User sender = new User();
            sender.setId(resultSet.getLong("sender_id"));
            sender.setFirstName(resultSet.getString("sender_first_name"));
            sender.setLastName(resultSet.getString("sender_last_name"));
            sender.setEmail(resultSet.getString("sender_email"));
            sender.setRole(role);

            Request request = new Request();
            request.setId(resultSet.getLong("request_id"));

            Comment comment = new Comment();
            comment.setText(resultSet.getString("text"));
            comment.setId(resultSet.getLong("id"));
            comment.setSender(sender);
            comment.setCreateDateAndTime(resultSet.getTimestamp("create_date_and_time").toLocalDateTime());
            Timestamp updateTimestamp = resultSet.getTimestamp("update_date_and_time");
            LocalDateTime updateTime = updateTimestamp != null ? updateTimestamp.toLocalDateTime() : null;
            comment.setUpdateDateAndTime(updateTime);
            comment.setRequest(request);

            return comment;
        };
    }

    @Override
    protected String getInsertQuery() {
        return queryService().getQuery("comment.insert");
    }

    @Override
    protected String getFindOneQuery() {
        return queryService().getQuery("comment.findOne");
    }

    @Override
    protected String getDeleteQuery() {
        return queryService().getQuery("comment.delete");
    }

    @Override
    protected String getExistsQuery() {
        return null;
    }

    @Override
    protected String getFindAllQuery() {
        return queryService().getQuery("comment.findAll");
    }

    @Override
    protected String getCountQuery() {
        return queryService().getQuery("comment.count");
    }

    private String getByRequestQuery() {
        return queryService().getQuery("comment.select") + queryService().getQuery("comment.getByRequestQuery");
    }
}
