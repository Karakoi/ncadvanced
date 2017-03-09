package com.overseer.dao.impl;


import com.overseer.dao.MessageDao;
import com.overseer.model.Message;
import com.overseer.model.User;
import lombok.val;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Implementation of {@link MessageDao} interface.
 * </p>
 */
@Repository
public class MessageDaoImpl extends CrudDaoImpl<Message> implements MessageDao {
    @Override
    public Long getCountByRecipient(Long recipientId) {
        return jdbc().queryForObject(getCountByRecipientQuery(),
                new MapSqlParameterSource("recipient", recipientId), Long.class);
    }

    @Override
    public List<Message> findByRecipient(Long recipientId, int pageSize, int pageNumber) {
        val parameterSource = new MapSqlParameterSource("recipient", recipientId);
        parameterSource.addValue("limit", pageSize);
        parameterSource.addValue("offset", pageSize * (pageNumber - 1));
        return jdbc().query(getMessageByRecipientQuery(), parameterSource, getMapper());
    }

    protected String getMessageByRecipientQuery() {
        return queryService().getQuery("message.findByRecipient");
    }

    @Override
    protected RowMapper<Message> getMapper() {
        return (resultSet, i) -> {
            User sender = new User();
            sender.setId(resultSet.getLong("sender_id"));
            sender.setFirstName(resultSet.getString("sender_first_name"));
            sender.setLastName(resultSet.getString("sender_last_name"));

            User recipient = new User();
            recipient.setId(resultSet.getLong("recipient_id"));

            Message message = new Message();
            message.setText(resultSet.getString("text"));
            message.setId(resultSet.getLong("id"));
            message.setRecipient(recipient);
            message.setSender(sender);
            message.setTopic(null);
            message.setDateAndTime(resultSet.getTimestamp("date_and_time").toLocalDateTime());

            return message;
        };
    }

    @Override
    protected String getInsertQuery() {
        return queryService().getQuery("message.insert");
    }

    @Override
    protected String getFindOneQuery() {
        return queryService().getQuery("message.findOne");
    }

    @Override
    protected String getDeleteQuery() {
        return null;
    }

    @Override
    protected String getExistsQuery() {
        return null;
    }

    @Override
    protected String getFindAllQuery() {
        return queryService().getQuery("message.findAll");
    }

    @Override
    protected String getCountQuery() {
        return queryService().getQuery("message.count");
    }

    private String getCountByRecipientQuery() {
        return queryService().getQuery("message.countByRecipient");
    }
}