package com.overseer.service.impl;

import com.overseer.dao.CrudDao;
import com.overseer.dao.RequestDao;
import com.overseer.exception.entity.EntityAlreadyExistsException;
import com.overseer.model.PriorityStatus;
import com.overseer.model.ProgressStatus;
import com.overseer.model.Request;
import com.overseer.model.User;
import com.overseer.service.RequestService;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;

/**
 * Implementation of {@link RequestService} interface.
 */
@Service
@NoArgsConstructor
public class RequestServiceImpl extends CrudServiceImpl<Request> implements RequestService {
    private RequestDao requestDao;

    public RequestServiceImpl(@Qualifier("requestDaoImpl") CrudDao<Request, Long> requestDao) {
        super(requestDao);
        this.requestDao = (RequestDao) requestDao;
    }

    @Override
    public List<Request> findSubRequests(Request parent) {
        Assert.notNull(parent);
        return this.requestDao.findSubRequests(parent);
    }

    @Override
    public List<Request> findJoinedRequests(Request parent) {
        Assert.notNull(parent);
        return this.requestDao.findJoinedRequests(parent);
    }

    @Override
    public List<Request> findRequestsByAssignee(User assignee) {
        Assert.notNull(assignee);
        return this.requestDao.findRequestsByAssignee(assignee);
    }

    @Override
    public List<Request> findRequestsByReporter(User reporter) {
        Assert.notNull(reporter);
        return this.requestDao.findRequestsByReporter(reporter);
    }

    @Override
    public List<Request> findRequestsByProgress(ProgressStatus progressStatus) {
        Assert.notNull(progressStatus);
        return this.requestDao.findRequestsByProgress(progressStatus);
    }

    @Override
    public List<Request> findRequestsByPriority(PriorityStatus priorityStatus) {
        Assert.notNull(priorityStatus);
        return this.requestDao.findRequestsByPriority(priorityStatus);
    }

    @Override
    public List<Request> findRequestsByPeriod(LocalDate start, LocalDate end) {
        Assert.notNull(start);
        Assert.notNull(end);
        return this.requestDao.findRequestsByPeriod(start, end);
    }

    @Override
    public List<Request> findRequestsByDate(LocalDate date) {
        Assert.notNull(date);
        return this.requestDao.findRequestsByDate(date);
    }
}
