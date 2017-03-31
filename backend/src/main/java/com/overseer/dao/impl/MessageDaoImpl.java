package com.overseer.dao.impl;


import com.overseer.dao.MessageDao;
import com.overseer.model.Message;
import com.overseer.model.Role;
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
    public List<Message> findByTopic(Long topicId) {
        val parameterSource = new MapSqlParameterSource("topicId", topicId);
        return jdbc().query(getByTopicQuery(), parameterSource, getMapper());
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public int deleteByTopicId(Long topicId) {
        List<Message> messagesByTopic = findByTopic(topicId);
        for (Message message: messagesByTopic) {
            delete(message);
        }
        return messagesByTopic.size();
    }

    @Override
    public List<Message> findDialogMessages(Long senderId, Long recipientId) {
        val parameterSource = new MapSqlParameterSource("senderId", senderId);
        parameterSource.addValue("recipientId", recipientId);
        return jdbc().query(getByFriendQuery(), parameterSource, getDialogMapper());
    }

    @Override
    protected RowMapper<Message> getMapper() {
        return (resultSet, i) -> {
            Role role = new Role(resultSet.getString("name"));

            User sender = new User();
            sender.setId(resultSet.getLong("sender_id"));
            sender.setFirstName(resultSet.getString("sender_first_name"));
            sender.setLastName(resultSet.getString("sender_last_name"));
            sender.setEmail(resultSet.getString("sender_email"));
            sender.setRole(role);

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

    private RowMapper<Message> getDialogMapper() {
        return (resultSet, i) -> {
            User sender = new User();
            sender.setId(resultSet.getLong("sender_id"));
            sender.setFirstName(resultSet.getString("sender_first_name"));
            sender.setEmail(resultSet.getString("sender_email"));

            User recipient = new User();
            recipient.setId(resultSet.getLong("recipient_id"));
            recipient.setFirstName(resultSet.getString("recipient_first_name"));
            recipient.setEmail(resultSet.getString("recipient_email"));

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
        return queryService().getQuery("message.delete");
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

    private String getByTopicQuery() {
        return queryService().getQuery("message.select") + queryService().getQuery("message.getByTopicQuery");
    }

    private String getByFriendQuery() {
        return queryService().getQuery("message.getByFriendQuery");
    }
}
