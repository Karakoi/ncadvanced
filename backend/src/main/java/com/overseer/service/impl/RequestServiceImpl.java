package com.overseer.service.impl;

import com.overseer.dao.ProgressStatusDao;
import com.overseer.dao.RequestDao;
import com.overseer.event.ChangeProgressStatusEvent;
import com.overseer.exception.RmovingNotFreeRequestException;
import com.overseer.exception.InappropriateProgressStatusChange;
import com.overseer.exception.entity.NoSuchEntityException;
import com.overseer.model.PriorityStatus;
import com.overseer.model.ProgressStatus;
import com.overseer.model.Request;
import com.overseer.model.User;
import com.overseer.service.RequestService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of {@link RequestService} interface.
 */
@Service
@Slf4j
public class RequestServiceImpl extends CrudServiceImpl<Request> implements RequestService, ApplicationEventPublisherAware {
    private static final short DEFAULT_PAGE_SIZE = 20;

    private static final Long FREE_STATUS = 5L;
    private static final Long JOINED_STATUS = 6L;
    private static final Long IN_PROGRESS_STATUS = 7L;
    private static final Long CLOSED_STATUS = 8L;

    private RequestDao requestDao;
    private ProgressStatusDao progressStatusDao;

    private ApplicationEventPublisher publisher;

    public RequestServiceImpl(RequestDao requestDao,
                              ProgressStatusDao progressStatusDao) {
        super(requestDao);
        this.requestDao = requestDao;
        this.progressStatusDao = progressStatusDao;
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
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
        List<Request> joinedRequests = requestDao.findRequestsByIds(ids);

        //check if joinedRequests are appropriate
        joinedRequests.forEach(request -> {
            if (request.getProgressStatus().getId() != FREE_STATUS) {
                throw new InappropriateProgressStatusChange("Can not join request with id: " + request.getId()
                        + " because it has progress status not Free");
            }
        });

        // Define progress status with 'Joined' value for child requests
        ProgressStatus joinedProgressStatus = progressStatusDao.findOne(JOINED_STATUS);
        ChangeProgressStatusEvent event = new ChangeProgressStatusEvent(this, joinedProgressStatus, parentRequest, joinedRequests);
        publisher.publishEvent(event);

        return parentRequest;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public Request saveSubRequest(Request subRequest, Long idParentRequest) {
        Assert.notNull(subRequest, "sub request must not be null");
        Assert.notNull(idParentRequest, "id of parent request must not be null");
        Assert.isNull(subRequest.getPriorityStatus(), "sub request priority status must be null");
        Assert.isNull(subRequest.getProgressStatus(), "sub request progress status must be null");
        log.debug("Create sub request {} for parent request with id {}", subRequest, idParentRequest);
        subRequest.setParentId(idParentRequest);
        return requestDao.save(subRequest);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public Request assignRequest(Request request) {
        Assert.notNull(request, "request must not be null");
        log.debug("Assign request with id: {} to office manager with id: {}", request.getId(), request.getAssignee().getId());
        if(request.getProgressStatus().getId() != FREE_STATUS){
            throw new InappropriateProgressStatusChange("Request with id: "
                    + request.getId() + " and ProgressStatus: "
                    + request.getProgressStatus().getName()
                    + " can not be assign");
        }
        // Define and set progress status
        ProgressStatus progressStatus = progressStatusDao.findOne(IN_PROGRESS_STATUS);
        ChangeProgressStatusEvent event = new ChangeProgressStatusEvent(this, progressStatus, request);
        publisher.publishEvent(event);
        return request;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public Request closeRequest(Request request) {
        Assert.notNull(request, "request must not be null");
        log.debug("Close request with id: {} ", request.getId());
        Long progressStatusId = request.getProgressStatus().getId();
        if(progressStatusId != IN_PROGRESS_STATUS && progressStatusId != JOINED_STATUS){
            throw new InappropriateProgressStatusChange("Request with id: "
                    + request.getId() + " and ProgressStatus: "
                    + request.getProgressStatus().getName()
                    + " can not be closed");
        }
        // Define and set progress status
        ProgressStatus closedProgressStatus = progressStatusDao.findOne(CLOSED_STATUS);
        ChangeProgressStatusEvent event = new ChangeProgressStatusEvent(this, closedProgressStatus, request);
        publisher.publishEvent(event);
        return request;
    }

    @Override
    public Request reopenRequest(Long requestId) {
        Assert.notNull(requestId, "id of request must not be null");
        log.debug("Close request with id: {} ", requestId);

        Request request = requestDao.findOne(requestId);
        if (request == null) {
            throw new NoSuchEntityException("Request with given id: " + requestId + " is absent in DB");
        }
        if(request.getProgressStatus().getId() != CLOSED_STATUS){
            throw new InappropriateProgressStatusChange("Request with id: "
                    + request.getId() + " and ProgressStatus: "
                    + request.getProgressStatus().getName()
                    + " can not be reopen");
        }
        ProgressStatus freeProgressStatus = progressStatusDao.findOne(FREE_STATUS);
        ChangeProgressStatusEvent event = new ChangeProgressStatusEvent(this, freeProgressStatus, request);
        publisher.publishEvent(event);
        return request;
    }

    @Override
    public void delete(Long idRequest) {
        Assert.notNull(idRequest, "id of request must not be null");
        log.debug("Delete request with id: {} ", idRequest);
        Request request = requestDao.findOne(idRequest);
        Long progressStatusId = request.getProgressStatus().getId();
        if (progressStatusId.equals(FREE_STATUS)) {
            super.delete(idRequest);
        } else {
            throw new RmovingNotFreeRequestException("Can not remove request with id: "
                    + request.getId() + " and progress status " + request.getProgressStatus().getName());
        }
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void closeAllRequestsOfGivenReporter(Long reporterId) {
        Assert.notNull(reporterId, "id of request must not be null");
        log.debug("Close all requests of Reporter with id: {} ", reporterId);
        List<Long> idsOfProgresStatuses = new ArrayList<>();
        idsOfProgresStatuses.add(IN_PROGRESS_STATUS);
        idsOfProgresStatuses.add(JOINED_STATUS);
        List<Request> requests = requestDao.findRequestsByProgressStatusesAndReporterId(idsOfProgresStatuses, reporterId);
        requests.forEach(this::closeRequest);
    }
}
