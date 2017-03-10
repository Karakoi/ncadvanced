package com.overseer.service.impl;

import com.overseer.dao.RequestDao;
import com.overseer.model.PriorityStatus;
import com.overseer.model.ProgressStatus;
import com.overseer.model.Request;
import com.overseer.model.User;
import com.overseer.service.RequestService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

/**
 * Implementation of {@link RequestService} interface.
 */
@Service
@Slf4j
public class RequestServiceImpl extends CrudServiceImpl<Request> implements RequestService {
    private static final short DEFAULT_PAGE_SIZE = 20;
    private static final Long IN_PROGRESS_STATUS = 7L;
    private static final Long JOINED_STATUS = 6L;

    private RequestDao requestDao;

    public RequestServiceImpl(RequestDao requestDao) {
        super(requestDao);
        this.requestDao = requestDao;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<Request> findSubRequests(Request parent) {
        Assert.notNull(parent, "parent request must not be null");
        val list = this.requestDao.findSubRequests(parent);
        log.debug("Fetched {} sub requests for parent with id: {}", list.size(), parent.getId());
        return list;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<Request> findJoinedRequests(Request parent) {
        Assert.notNull(parent, "parent request must not be null");
        val list = this.requestDao.findJoinedRequests(parent);
        log.debug("Fetched {} joined requests for parent with id: {}", list.size(), parent.getId());
        return list;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<Request> findRequestsByAssignee(User assignee, int pageNumber) {
        Assert.notNull(assignee, "assignee must not be null");
        val list = this.requestDao.findRequestsByAssignee(assignee, DEFAULT_PAGE_SIZE, pageNumber);
        log.debug("Fetched {} requests for assignee with id: {} for page number: {}",
                list.size(), assignee.getId(), pageNumber);
        return list;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<Request> findRequestsByReporter(User reporter, int pageNumber) {
        Assert.notNull(reporter, "reporter must not be null");
        val list = this.requestDao.findRequestsByReporter(reporter, DEFAULT_PAGE_SIZE, pageNumber);
        log.debug("Fetched {} requests for reporter with id: {} for page number: {}",
                list.size(), reporter.getId(), pageNumber);
        return list;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<Request> findRequestsByProgress(ProgressStatus progressStatus, int pageNumber) {
        Assert.notNull(progressStatus, "progress status must not be null");
        val list = this.requestDao.findRequestsByProgress(progressStatus, DEFAULT_PAGE_SIZE, pageNumber);
        log.debug("Fetched {} requests with progress status: {} for page number: {}",
                list.size(), progressStatus.getName(), pageNumber);
        return list;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<Request> findRequestsByPriority(PriorityStatus priorityStatus, int pageNumber) {
        Assert.notNull(priorityStatus, "priority status must not be null");
        val list = this.requestDao.findRequestsByPriority(priorityStatus, DEFAULT_PAGE_SIZE, pageNumber);
        log.debug("Fetched {} requests with priority status: {} for page number: {}",
                list.size(), priorityStatus.getName(), pageNumber);
        return list;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<Request> findRequestsByPeriod(LocalDate start, LocalDate end, int pageNumber) {
        Assert.notNull(start, "start date must not be null");
        Assert.notNull(end, "end date must not be null");
        val list = this.requestDao.findRequestsByPeriod(start, end, DEFAULT_PAGE_SIZE, pageNumber);
        log.debug("Fetched {} requests for period {} - {} for page number: {}", list.size(), start, end, pageNumber);
        return list;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<Request> findRequestsByDate(LocalDate date) {
        Assert.notNull(date, "date must not be null");
        val list = this.requestDao.findRequestsByDate(date);
        log.debug("Fetched {} requests for date: {}", list.size(), date);
        return list;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public Request joinRequestsIntoParent(List<Long> ids, Request parentRequest) {
        Assert.notNull(ids, "ids must not be null");
        Assert.notNull(parentRequest, "parent request must not be null");
        log.debug("Joining requests with ids {} into parent request {}", ids, parentRequest);

        // Retrieve specified requests for joining from database
        val joinedRequests = requestDao.findRequestsByIds(ids);

        // Find and set max priority status from specified requests to parent request
        val maxPriorityStatus = getMaxPriorityStatus(joinedRequests);
        parentRequest.setPriorityStatus(maxPriorityStatus);

        // Define and set progress status and date of creation to parent
        val parentProgressStatus = new ProgressStatus();
        parentProgressStatus.setName("In progress");
        parentProgressStatus.setId(IN_PROGRESS_STATUS);
        parentRequest.setProgressStatus(parentProgressStatus);
        parentRequest.setDateOfCreation(LocalDateTime.now());

        // Save parent request to database
        val parent = requestDao.save(parentRequest);

        // Define progress status with 'Joined' value for child requests
        val childProgressStatus = new ProgressStatus();
        childProgressStatus.setName("Joined");
        childProgressStatus.setId(JOINED_STATUS);

        // Update child requests with new progress status and parent id
        val parentId = parent.getId();
        joinedRequests.forEach(request -> {
            request.setProgressStatus(childProgressStatus);
            request.setParentId(parentId);
            requestDao.save(request);
        });
        return parent;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public Request saveSubRequest(Request subRequest, Request parentRequest) {
        Assert.notNull(subRequest, "sub request must not be null");
        Assert.notNull(parentRequest, "parent request must not be null");
        Assert.isNull(subRequest.getPriorityStatus(), "sub request priority status must be null");
        Assert.isNull(subRequest.getProgressStatus(), "sub request progress status must be null");
        log.debug("Create sub request {} for parent request {}", subRequest, parentRequest);
        val parentId = parentRequest.getId();
        subRequest.setParentId(parentId);
        return requestDao.save(subRequest);
    }

    /**
     * Returns max {@link PriorityStatus} of specified requests list.
     * Statuses compares by {@link PriorityStatus#value}.
     *
     * @param requests specified requests list
     * @return max priority status
     */
    private PriorityStatus getMaxPriorityStatus(List<Request> requests) {
        return requests
                .stream()
                .map(Request::getPriorityStatus)
                .max(Comparator.comparingInt(PriorityStatus::getValue))
                .orElseThrow(UnsupportedOperationException::new);
    }
}
