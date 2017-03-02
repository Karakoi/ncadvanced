package com.overseer.service.impl;

import com.overseer.dao.RequestDao;
import com.overseer.exception.entity.EntityAlreadyExistsException;
import com.overseer.exception.entity.NoSuchEntityException;
import com.overseer.model.PriorityStatus;
import com.overseer.model.ProgressStatus;
import com.overseer.model.Request;
import com.overseer.service.RequestService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;

/**
 * Implementation of {@link RequestService} interface.
 */
@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {

    private static final Logger LOG = LoggerFactory.getLogger(RequestServiceImpl.class);

    private final RequestDao requestDao;

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<Request> getJoinedGroups() {
        LOG.info("Retrieving all joined groups...");
        return requestDao.getJoinedGroups();
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<Request> getJoinedGroupRequests(Long id) {
        LOG.info("Retrieving all joined group requests for joined group with id: {}", id);
        return requestDao.getJoinedGroupRequests(id);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<Request> getSubRequests(Long id) {
        Assert.notNull(id);
        LOG.info("Retrieving all sub requests for parent request with id: {}", id);
        return requestDao.getSubRequests(id);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public Request create(Request request) throws EntityAlreadyExistsException {
        Assert.notNull(request);
        if (request.getId() != null) {
            throw new EntityAlreadyExistsException("Failed to create request. Id was not null for request: " + request);
        }
        LOG.debug("Saving request: {}", request);
        return requestDao.save(request);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public Request update(Request request) throws NoSuchEntityException {
        Assert.notNull(request);
        if (request.getId() != null) {
            throw new EntityAlreadyExistsException("Failed to update request. Id was not null for request: " + request);
        }
        LOG.debug("Updating request: {}", request);
        return requestDao.save(request);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public Request findOne(Long id) throws NoSuchEntityException {
        Assert.notNull(id);
        Request request = requestDao.findOne(id);
        if (request == null) {
            throw new NoSuchEntityException("Failed to retrieve request with id " + id);
        }
        LOG.debug("Retrieving request with id: {}", id);
        return request;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void delete(Request request) {
        Assert.notNull(request);
        LOG.debug("Removing request: {}", request);
        requestDao.delete(request);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void delete(Long id) {
        Assert.notNull(id);
        LOG.debug("Removing request with id: {}", id);
        requestDao.delete(id);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public boolean exists(Long id) {
        Assert.notNull(id);
        LOG.debug("Checking if request with id {} exists", id);
        return requestDao.exists(id);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<Request> findAll() {
        LOG.info("Retrieving all requests...");
        return requestDao.findAll();
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<Request> getRequestsByParent(Long parentId) {
        Assert.notNull(parentId);
        LOG.info("Retrieving all requests for parent request with id: {}", parentId);
        return requestDao.getRequestsByParent(parentId);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<Request> getRequestsByAssignee(Long assigneeId) {
        Assert.notNull(assigneeId);
        LOG.info("Retrieving all requests for assignee with id: {}", assigneeId);
        return requestDao.getRequestsByAssignee(assigneeId);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<Request> getRequestsByReporter(Long reporterId) {
        Assert.notNull(reporterId);
        LOG.info("Retrieving all requests for reporter with id: {}", reporterId);
        return requestDao.getRequestsByReporter(reporterId);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<Request> getRequestsByPeriod(LocalDate begin, LocalDate end) {
        Assert.notNull(begin);
        Assert.notNull(end);
        LOG.info("Retrieving all requests in the period from {} to {}", begin, end);
        return requestDao.getRequestsByPeriod(begin, end);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<Request> getRequestsByDate(LocalDate date) {
        Assert.notNull(date);
        LOG.info("Retrieving all requests by {} date", date);
        return requestDao.getRequestsByDate(date);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<Request> getRequestsByStatus(ProgressStatus progressStatus) {
        Assert.notNull(progressStatus);
        LOG.info("Retrieving all requests by {} progress status", progressStatus);
        return requestDao.getRequestsByStatus(progressStatus);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<Request> getRequestsByPriority(PriorityStatus priorityStatus) {
        Assert.notNull(priorityStatus);
        LOG.info("Retrieving all requests by {} priority status", priorityStatus);
        return requestDao.getRequestsByPriority(priorityStatus);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public Request changeProgressStatus(Request request) {
        Assert.notNull(request);
        LOG.info("Change progress status for request with id: {}", request.getId());
        return requestDao.changeProgressStatus(request);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public Request changePriorityStatus(Request request) {
        Assert.notNull(request);
        LOG.info("Change priority status for request with id: {}", request.getId());
        return requestDao.changePriorityStatus(request);
    }
}
