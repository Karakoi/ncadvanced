package com.overseer.service.impl;

import com.overseer.dao.MessageDao;
import com.overseer.model.Message;
import com.overseer.service.MessageService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of {@link MessageService} interface.
 */
@Service
public class MessageServiceImpl extends CrudServiceImpl<Message> implements MessageService {
    private static final int DEFAULT_PAGE_SIZE = 10;

    private MessageDao messageDao;

    public MessageServiceImpl(MessageDao messageDao) {
        super(messageDao);
        this.messageDao = messageDao;
    }

    @Override
    public List<Message> findByRecipient(Long recipientId, int pageNumber) {
        return messageDao.findByRecipient(recipientId, DEFAULT_PAGE_SIZE, pageNumber);
    }

    @Override
    public Long getCountByRecipient(Long recipientId) {
        return messageDao.getCountByRecipient(recipientId);
    }
}
