package com.overseer.service.impl;

import com.overseer.dao.MessageDao;
import com.overseer.dao.TopicDao;
import com.overseer.model.Message;
import com.overseer.model.Topic;
import com.overseer.service.TopicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Implementation of {@link TopicService} interface.
 */
@Service
@Slf4j
public class TopicServiceImpl extends CrudServiceImpl<Topic> implements TopicService {

    private TopicDao topicDao;
    @Autowired
    private MessageDao messageDao;

    public TopicServiceImpl(TopicDao topicDao) {
        super(topicDao);
        this.topicDao = topicDao;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void delete(Long id) {
        Assert.notNull(id, "id must not be null");
        log.debug("Deleting entity with id: {}", id);
        topicDao.delete(id);
        messageDao.deleteByTopicId(id);
    }



    /*@Override
    public Topic create(Topic entity) throws EntityAlreadyExistsException {
        Assert.notNull(entity);
        if (!entity.isNew()) {
            throw new EntityAlreadyExistsException("Failed to perform create operation. Id was not null: " + entity);
        }
        Topic topic = topicDao.save(entity);

        return topic;
    }*/

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<Topic> findUserTopics(Long userId) {
        log.debug("Fetched all topics for user with id: {}", userId);
        return topicDao.findUserTopics(userId);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public Message saveTopicMessage(Message message) {
        return topicDao.saveTopicMessage(message);
    }
}
