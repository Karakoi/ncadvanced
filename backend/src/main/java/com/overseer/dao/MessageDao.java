package com.overseer.dao;


import com.overseer.model.Message;
import com.overseer.model.User;
import io.jsonwebtoken.lang.Assert;

import java.util.List;

/**
 * The <code>MessageDao</code> interface represents access to {@link MessageDao} object in database.
 */
public interface MessageDao extends CrudDao<Message, Long> {

    List<Message> findByRecipient(Long recipientId, int pageSize, int pageNumber);

    default List<Message> findByRecipient(User recipient, int pageSize, int pageNumber) {
        Assert.notNull(recipient, "Recipient have to be not null");
        return findByRecipient(recipient.getId(), pageSize, pageNumber);
    }

    Long getCountByRecipient(Long recipientId);

    default Long getCountByRecipient(User recipient) {
        Assert.notNull(recipient, "Recipient have to be not null");
        return getCountByRecipient(recipient.getId());
    }

    List<Message> findByTopic(Long topicId);

    List<Message> findDialogMessages(Long senderId, Long recipientId);
}
