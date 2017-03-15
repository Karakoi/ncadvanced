package com.overseer.service;

import com.overseer.model.Message;

import java.util.List;

/**
 * The <code>MessageService</code> interface provide functionality
 * of private messages.
 */
public interface MessageService extends CrudService<Message, Long> {

    List<Message> findByRecipient(Long recipientId, int pageNumber);

    Long getCountByRecipient(Long recipientId);

    List<Message> findByTopic(Long topicId);
}
