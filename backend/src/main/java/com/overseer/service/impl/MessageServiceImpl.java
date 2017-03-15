package com.overseer.service.impl;

import com.overseer.dao.MessageDao;
import com.overseer.model.Message;
import com.overseer.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of {@link MessageService} interface.
 */
@Service
@Slf4j
public class MessageServiceImpl extends CrudServiceImpl<Message> implements MessageService {
    private static final int DEFAULT_PAGE_SIZE = 10;

    private MessageDao messageDao;

    public MessageServiceImpl(MessageDao messageDao) {
        super(messageDao);
        this.messageDao = messageDao;
    }

    @Override
    public List<Message> findByRecipient(Long recipientId, int pageNumber) {
        val list = messageDao.findByRecipient(recipientId, DEFAULT_PAGE_SIZE, pageNumber);
        log.debug("Fetched {} messages for user with id: {}", list.size(), recipientId);
        return list;
    }

    @Override
    public Long getCountByRecipient(Long recipientId) {
        val count = messageDao.getCountByRecipient(recipientId);
        log.debug("Counted {} messages for user with id: {}", recipientId);
        return count;
    }

    @Override
    public List<Message> findByTopic(Long topicId) {
        val list = messageDao.findByTopic(topicId);
        log.debug("Fetched messages for topic with id: {}", topicId);
        return list;
    }
}
