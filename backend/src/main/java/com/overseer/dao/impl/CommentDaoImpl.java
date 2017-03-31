package com.overseer.dao.impl;

import com.overseer.dao.CommentDao;
import com.overseer.model.*;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of {@link CommentDao} interface.
 */
@Transactional
@Repository
@RequiredArgsConstructor
public class CommentDaoImpl extends CrudDaoImpl<Comment> implements CommentDao {

    @Override
    public List<Comment> findByRequest(Long requestId) {
        val parameterSource = new MapSqlParameterSource("requestId", requestId);
        return jdbc().query(getByRequestQuery(), parameterSource, getMapper());
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
            request.setId(resultSet.getLong("id"));

            Comment comment = new Comment();
            comment.setText(resultSet.getString("text"));
            comment.setId(resultSet.getLong("id"));
            comment.setSender(sender);
            comment.setDateAndTime(resultSet.getTimestamp("date_and_time").toLocalDateTime());
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
