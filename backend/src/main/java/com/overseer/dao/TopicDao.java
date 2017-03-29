package com.overseer.dao;

import com.overseer.model.Message;
import com.overseer.model.Topic;

import java.util.List;

/**
 * The <code>TopicDao</code> interface represents access to {@link Topic} object in database.
 */
public interface TopicDao extends CrudDao<Topic, Long> {

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
    Message saveTopicMessage(Message message);

    /**
     * Checks whether entity with the given title exists.
     *
     * @param title title of topic
     * @return {@literal true} if entity with the given title exists, {@literal false} otherwise.
     */
    boolean existsByTitle(String title);
}