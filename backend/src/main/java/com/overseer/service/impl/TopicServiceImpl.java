package com.overseer.service.impl;

import com.overseer.dao.TopicDao;
import com.overseer.model.Topic;
import com.overseer.service.TopicService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TopicServiceImpl extends CrudServiceImpl<Topic> implements TopicService {

    private final TopicDao topicDao;

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<Topic> findUserTopics(Long userId) {
        log.debug("Fetched all topics for user with id: {}", userId);
        return topicDao.findUserTopics(userId);
    }
}
