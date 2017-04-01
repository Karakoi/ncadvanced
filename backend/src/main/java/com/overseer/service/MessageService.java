package com.overseer.service;

import com.overseer.model.Message;

import java.util.List;

/**
 * The <code>MessageService</code> interface provide functionality
 * of private messages.
 */
public interface MessageService extends CrudService<Message, Long> {

    List<Message> findByTopic(Long topicId);

    List<Message> findDialogMessages(Long senderId, Long recipientId);

    List<Message> findUnreadMessages(Long recipientId);
}
