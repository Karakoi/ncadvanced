package com.overseer.dao;

import com.overseer.model.Message;
import com.overseer.model.Topic;

import java.util.List;

/**
 * The <code>TopicDao</code> interface represents access to {@link Topic} object in database.
 */
public interface TopicDao extends SimpleEntityDao<Topic> {

    /**
     * Find all topics where user post messages.
     *
     * @param userId specified user id
     * @return all topics for input user
     */
    List<Topic> findUserTopics(Long userId);

    /**
     * Saved specified topic message.
     *
     * @param message topic message
     */
    void saveTopicMessage(Message message);
}