package com.overseer.dao;


import com.overseer.model.Message;

import java.util.List;

/**
 * The <code>MessageDao</code> interface represents access to {@link MessageDao} object in database.
 */
public interface MessageDao extends CrudDao<Message, Long> {

    List<Message> findByTopic(Long topicId);

    List<Message> findDialogMessages(Long senderId, Long recipientId);
}
