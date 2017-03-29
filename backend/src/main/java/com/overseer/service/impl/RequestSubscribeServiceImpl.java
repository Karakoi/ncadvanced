package com.overseer.service.impl;

import com.overseer.dao.RequestSubscribersDao;
import com.overseer.model.User;
import com.overseer.service.RequestSubscribeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * {@inheritDoc}.
 */
@Service
public class RequestSubscribeServiceImpl implements RequestSubscribeService {

    @Autowired
    private RequestSubscribersDao requestSubscribersDao;

    @Override
    public boolean exist(Long subscriberId, Long requestId) {
        return requestSubscribersDao.exists(subscriberId, requestId);
    }

    @Override
    public void save(Long subscriberId, Long requestId) {
        requestSubscribersDao.save(subscriberId, requestId);
    }

    @Override
    public void delete(Long subscriberId, Long requestId) {
        requestSubscribersDao.delete(subscriberId, requestId);
    }

    @Override
    public List<User> getSubscribersOfRequest(Long requestId) {
        return requestSubscribersDao.getSubscribersOfRequest(requestId);
    }
}
