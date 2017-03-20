package com.overseer.dao.impl;

import com.overseer.dao.TopicDao;
import com.overseer.model.Message;
import com.overseer.model.Role;
import com.overseer.model.Topic;
import lombok.val;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;

/**
 * <p>
 * Implementation of {@link TopicDao} interface.
 * </p>
 */
@Repository
public class TopicDaoImpl extends SimpleEntityDaoImpl<Topic> implements TopicDao {

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<Topic> findUserTopics(Long userId) {
        Assert.notNull(userId, "user id must not be null");
        return this.jdbc().query(this.queryService().getQuery("topic.findUserTopics"),
                new MapSqlParameterSource("userId", userId),
                this.getMapper());
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public Message saveTopicMessage(Message message) {
        Assert.notNull(message, "message must not be null");
        val parameterSource = new MapSqlParameterSource("text", message.getText());
        parameterSource.addValue("senderId", message.getSender().getId());
        parameterSource.addValue("topicId", message.getTopic().getId());
        parameterSource.addValue("dateAndTime", message.getDateAndTime());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        this.jdbc().update(this.queryService().getQuery("topic.saveTopicMessage"),
                parameterSource,
                keyHolder, new String[]{"id"});
        long generatedId = keyHolder.getKey().longValue();
        message.setId(generatedId);
        return message;
    }

    @Override
    protected String getInsertQuery() {
        return this.queryService().getQuery("topic.insert");
    }

    @Override
    protected String getFindOneQuery() {
        return this.queryService().getQuery("topic.findOne");
    }

    @Override
    protected String getDeleteQuery() {
        return this.queryService().getQuery("topic.delete");
    }

    @Override
    protected String getExistsQuery() {
        return this.queryService().getQuery("topic.exists");
    }

    @Override
    protected String getFindAllQuery() {
        return this.queryService().getQuery("topic.fetchPage");
    }

    @Override
    protected String getCountQuery() {
        return this.queryService().getQuery("topic.count");
    }

    @Override
    protected String getFindByNameQuery() {
        return this.queryService().getQuery("topic.findByName");
    }

    @Override
    protected RowMapper<Topic> getMapper() {
        return (resultSet, rowNum) -> {
            Role role = new Role(resultSet.getString("name"));
            role.setId(resultSet.getLong("id"));

            Topic topic = new Topic(resultSet.getString("title"), role);
            topic.setId(resultSet.getLong("id"));

            return topic;
        };
    }
}