package com.overseer.dao;


import com.overseer.model.Message;

import java.util.List;

/**
 * The <code>MessageDao</code> interface represents access to {@link MessageDao} object in database.
 */
public interface MessageDao extends CrudDao<Message, Long> {

    List<Message> findByTopic(Long topicId);

    /**
     * This method deletes all messages in given topic by id.
     * @param topicId id of the topic.
     * @return number of deleted messages.
     */
    int deleteByTopicId(Long topicId);

    List<Message> findDialogMessages(Long senderId, Long recipientId);

    List<Message> findUnreadMessages(Long recipientId);
}
