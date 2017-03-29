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

    private MessageDao messageDao;

    public MessageServiceImpl(MessageDao messageDao) {
        super(messageDao);
        this.messageDao = messageDao;
    }

    @Override
    public List<Message> findByTopic(Long topicId) {
        val list = messageDao.findByTopic(topicId);
        log.debug("Fetched messages for topic with id: {}", topicId);
        return list;
    }

    @Override
    public List<Message> findDialogMessages(Long senderId, Long recipientId) {
        val list = messageDao.findDialogMessages(senderId, recipientId);
        log.debug("Fetched messages for dialog for sender with id {} and recipient with id {}", senderId, recipientId);
        return list;
    }
}
