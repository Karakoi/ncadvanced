package com.overseer.service.impl;

import com.overseer.dao.CrudDao;
import com.overseer.dao.RequestDao;
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
    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    private RequestDao requestDao;

    public RequestServiceImpl(@Qualifier("requestDaoImpl") CrudDao<Request, Long> requestDao) {
        super(requestDao);
        this.requestDao = (RequestDao) requestDao;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<Request> findSubRequests(Request parent) {
        Assert.notNull(parent, "parent request must not be null");
        LOG.debug("Fetching sub requests for parent: {}", parent.getTitle());
        return this.requestDao.findSubRequests(parent);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<Request> findJoinedRequests(Request parent) {
        Assert.notNull(parent, "parent request must not be null");
        LOG.debug("Fetching joined requests for parent: {}", parent.getTitle());
        return this.requestDao.findJoinedRequests(parent);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<Request> findRequestsByAssignee(User assignee) {
        Assert.notNull(assignee, "assignee must not be null");
        LOG.debug("Fetching requests for assignee: {}", assignee.getEmail());
        return this.requestDao.findRequestsByAssignee(assignee);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<Request> findRequestsByReporter(User reporter) {
        Assert.notNull(reporter, "reporter must not be null");
        LOG.debug("Fetching requests for reporter: {}", reporter.getEmail());
        return this.requestDao.findRequestsByReporter(reporter);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<Request> findRequestsByProgress(ProgressStatus progressStatus) {
        Assert.notNull(progressStatus, "progress status must not be null");
        LOG.debug("Fetching requests with progress status: {}", progressStatus.getName());
        return this.requestDao.findRequestsByProgress(progressStatus);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<Request> findRequestsByPriority(PriorityStatus priorityStatus) {
        Assert.notNull(priorityStatus, "priority status must not be null");
        LOG.debug("Fetching requests with priority status: {}", priorityStatus.getName());
        return this.requestDao.findRequestsByPriority(priorityStatus);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<Request> findRequestsByPeriod(LocalDate start, LocalDate end) {
        Assert.notNull(start, "start date must not be null");
        Assert.notNull(end, "end date must not be null");
        LOG.debug("Fetching requests between {} and {} dates", start, end);
        return this.requestDao.findRequestsByPeriod(start, end);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<Request> findRequestsByDate(LocalDate date) {
        Assert.notNull(date, "date must not be null");
        LOG.debug("Fetching requests for date: {}", date);
        return this.requestDao.findRequestsByDate(date);
    }
}
