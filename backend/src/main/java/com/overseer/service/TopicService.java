package com.overseer.service;

import com.overseer.model.Topic;

import java.util.List;

/**
 * The <code>TopicService</code> interface represents access to TopicDao.
 */
public interface TopicService extends CrudService<Topic, Long>{

    /**
     * Returns all topics where user take participant.
     *
     * @param userId user id
     * @return all user topics
     */
    List<Topic> findUserTopics(Long userId);
}
