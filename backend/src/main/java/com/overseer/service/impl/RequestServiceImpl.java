package com.overseer.service.impl;

import com.overseer.dao.ProgressStatusDao;
import com.overseer.dao.RequestDao;
import com.overseer.dto.RequestDTO;
import com.overseer.exception.RmovingNotFreeRequestException;
import com.overseer.exception.UnpropreateJoinRequest;
import com.overseer.exception.entity.NoSuchEntityException;
import com.overseer.model.PriorityStatus;
import com.overseer.model.ProgressStatus;
import com.overseer.model.Request;
import com.overseer.model.User;
import com.overseer.service.EmailBuilder;
import com.overseer.service.EmailService;
import com.overseer.service.RequestService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Implementation of {@link RequestService} interface.
 */
@Service
@Slf4j
public class RequestServiceImpl extends CrudServiceImpl<Request> implements RequestService {
    private static final short DEFAULT_PAGE_SIZE = 20;

    private static final Long FREE_STATUS = 5L;
    private static final Long JOINED_STATUS = 6L;
    private static final Long IN_PROGRESS_STATUS = 7L;
    private static final Long CLOSED_STATUS = 8L;

    private RequestDao requestDao;
    private ProgressStatusDao progressStatusDao;

    @Override
    public Long countRequestByReporter(Long reporterId) {
        return requestDao.countRequestsByReporter(reporterId);
    }

    @Override
    public Long countRequestByAssignee(Long managerId) {
        return requestDao.countRequestsByAssignee(managerId) / DEFAULT_PAGE_SIZE + 1;
    }

    @Override
    public Long countInProgressRequestByAssignee(Long managerId) {
        return requestDao.countInProgressRequestByAssignee(managerId) / DEFAULT_PAGE_SIZE + 1;
    }

    private EmailBuilder<Request> emailStrategyForAssignee;
    private EmailBuilder<Request> emailStrategyForReporter;
    private EmailService emailService;

    public RequestServiceImpl(RequestDao requestDao,
                              ProgressStatusDao progressStatusDao,
                              EmailService emailService,
                              @Qualifier("officeManagerNotificationBuilderImpl") EmailBuilder<Request> emailStrategyForAssignee,
                              @Qualifier("employeeNotificationBuilderImpl") EmailBuilder<Request> emailStrategyForReporter) {
        super(requestDao);
        this.requestDao = requestDao;
        this.progressStatusDao = progressStatusDao;
        this.progressStatusDao = progressStatusDao;
        this.emailService = emailService;
        this.emailStrategyForAssignee = emailStrategyForAssignee;
        this.emailStrategyForReporter = emailStrategyForReporter;
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
    public List<Request> findRequestsByAssignee(Long assigneeId, int pageNumber) {
        Assert.notNull(assigneeId, "assignee must not be null");
        val list = this.requestDao.findRequestsByAssignee(assigneeId, DEFAULT_PAGE_SIZE, pageNumber);
        log.debug("Fetched {} requests for assignee with id: {} for page number: {}",
                list.size(), assigneeId, pageNumber);
        return list;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<Request> findInProgressRequestsByAssignee(Long assigneeId, int pageNumber) {
        Assert.notNull(assigneeId, "assignee must not be null");
        val list = this.requestDao.findInProgressRequestsByAssignee(assigneeId, DEFAULT_PAGE_SIZE, pageNumber);
        log.debug("Fetched {} requests for assignee with id: {} for page number: {}",
                list.size(), assigneeId, pageNumber);
        return list;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<Request> findRequestsByReporter(Long reporterId, int pageNumber) {
        Assert.notNull(reporterId, "reporter must not be null");
        val list = this.requestDao.findRequestsByReporter(reporterId, DEFAULT_PAGE_SIZE, pageNumber);
        log.debug("Fetched {} requests for reporter with id: {} for page number: {}",
                list.size(), reporterId, pageNumber);
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
    public RequestDTO findCountRequestsByPeriod(LocalDate start, LocalDate end, String progressStatusName) {
        RequestDTO requestDTO = this.requestDao.findCountRequestsByPeriod(start, end, progressStatusName);
        log.debug("Fetched {} count of requests for period {} - {}", requestDTO, start, end);
        return requestDTO;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<RequestDTO> findListCountRequestsByPeriod(LocalDate start, LocalDate end, String progressStatusName) {
        List<RequestDTO> list = this.requestDao.findListCountRequestsByPeriod(start, end, progressStatusName);
        log.debug("Fetched {} request DTO's for period {} - {}", list.size(), start, end);
        return list;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public RequestDTO findCountRequestsByManagerAndPeriod(LocalDate start, LocalDate end, String progressStatusName, int id) {
        RequestDTO requestDTO = this.requestDao.findCountRequestsByManagerAndPeriod(start, end, progressStatusName, id);
        log.debug("Fetched {} count of requests for period {} - {}", requestDTO, start, end);
        return requestDTO;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<RequestDTO> findListCountRequestsByManagerAndPeriod(LocalDate start, LocalDate end, String progressStatusName, int id) {
        List<RequestDTO> list = this.requestDao.findListCountRequestsByManagerAndPeriod(start, end, progressStatusName, id);
        log.debug("Fetched {} request DTO's for period {} - {}", list.size(), start, end);
        return list;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<RequestDTO> findBestManagersByPeriod(LocalDate start, LocalDate end, String progressStatusName) {
        List<RequestDTO> list = this.requestDao.findListOfBestManagersByPeriod(start, end, progressStatusName);
        log.debug("Fetched {} request DTO's for period {} - {}", list.size(), start, end);
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
        val parentProgressStatus = progressStatusDao.findOne(IN_PROGRESS_STATUS);
        parentRequest.setProgressStatus(parentProgressStatus);
        parentRequest.setDateOfCreation(LocalDateTime.now());

        // Save parent request to database
        val parent = requestDao.save(parentRequest);

        // Define progress status with 'Joined' value for child requests
        val childProgressStatus = progressStatusDao.findOne(JOINED_STATUS);

        // Update child requests with new progress status and parent id
        Long parentId = parent.getId();

        joinedRequests.forEach(request -> {
            if (request.getProgressStatus().getId() != FREE_STATUS || request.getAssignee() == null) {
                throw new UnpropreateJoinRequest("Can not join request with id: " + request.getId()
                        + " because it has progress status not Free or has Assignee ");
            }
            request.setProgressStatus(childProgressStatus);
            request.setParentId(parentId);
            request.setAssignee(parentRequest.getAssignee());
            request.setEstimateTimeInDays(parentRequest.getEstimateTimeInDays());
            request.setLastChanger(parentRequest.getAssignee());
            requestDao.save(request);
            sendMessageToReporter(request);
        });
        return parent;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public Request saveSubRequest(Request subRequest) {
        Assert.notNull(subRequest, "sub request must not be null");
//        Assert.isNull(subRequest.getPriorityStatus(), "sub request priority status must be null");
//        Assert.isNull(subRequest.getProgressStatus(), "sub request progress status must be null");
        log.debug("Create sub request {} for parent request with id {}", subRequest, subRequest.getParentId());
        return requestDao.save(subRequest);
    }

    @Override
    public Long countFreeRequests() {
        val freeRequestsQuantity = requestDao.countFree();
        log.debug("Counted {} requests with Free progress status", freeRequestsQuantity);
        return freeRequestsQuantity;
    }

    @Override
    public Long countRequestsByAssignee(Long assigneeId) {
        val requestsByAssignee = requestDao.countByAssignee(assigneeId);
        log.debug("Counted {} requests for user with id: {}", requestsByAssignee, assigneeId);
        return requestsByAssignee;
    }

    @Override
    public List<Request> findFreeRequests(int pageNumber) {
        return requestDao.findFreeRequests(DEFAULT_PAGE_SIZE, pageNumber);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<Long> quantityByProgressStatus() {
        return requestDao.countRequestByProgressStatus();
    }

    /**
     *{@inheritDoc}.
     */
    @Override
    public List<Long> quantityForUser(Long userId) {
        return requestDao.countRequestByProgressStatusForUser(userId);
    }

    /**
     *{@inheritDoc}.
     */
    @Override
    public List<Long> quantityByPriorityStatus() {
        return requestDao.countRequestByPriorityStatus();
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<Long> quantityByProgressStatusForSixMonths() {
        return requestDao.countRequestByProgressStatusForSixMonths();
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<Long> quantityByProgressStatusForSixMonthsForUser(Long userId) {
        return requestDao.countRequestByProgressStatusForSixMonthsForUser(userId);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public Request assignRequest(Request request) {
        Assert.notNull(request, "request must not be null");
        log.debug("Assign request with id: {} to office manager with id: {}", request.getId(), request.getAssignee().getId());
        // Define and set progress status
        val progressStatus = progressStatusDao.findOne(IN_PROGRESS_STATUS);
        request.setProgressStatus(progressStatus);
        requestDao.save(request);
        sendMessageToReporter(request);
        return request;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public Request closeRequest(Request request) {
        System.out.println("Service, ID = " + request.getId());
        Assert.notNull(request, "request must not be null");
        log.debug("Close request with id: {} ", request.getId());
        // Define and set progress status
        val closedProgressStatus = progressStatusDao.findOne(CLOSED_STATUS);
        //Check if request is parent
        List<Request> joinedRequests = requestDao.findJoinedRequests(request);
        if (joinedRequests.isEmpty()) {
            request.setProgressStatus(closedProgressStatus);
            requestDao.save(request);
            sendMessageToReporter(request);
        } else {
            System.out.println(joinedRequests);
            System.out.println("Point 1");
            for (Request joinedRequest : joinedRequests) {
                joinedRequest.setParentId(null);
                joinedRequest.setProgressStatus(closedProgressStatus);
                requestDao.save(joinedRequest);

                sendMessageToReporter(joinedRequest);
            }
            System.out.println("Point 2");
            List<Request> subRequests = requestDao.findSubRequests(request);
            for (Request subRequest : subRequests) {
                requestDao.delete(subRequest);
            }
            System.out.println("Point 3");
            System.out.println(request.getParentId());
            requestDao.deleteParentRequestIfItHasNoChildren(request.getId());
            System.out.println("Point 4");
        }
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
        ProgressStatus freeProgressStatus = progressStatusDao.findOne(FREE_STATUS);
        request.setProgressStatus(freeProgressStatus);

        sendMessageToAssignee(request);
        sendMessageToReporter(request);

        request.setEstimateTimeInDays(null);
        request.setAssignee(new User());

        requestDao.save(request);
        return null;
    }

    @Override
    public void delete(Long idRequest) {
        Assert.notNull(idRequest, "id of request must not be null");
        log.debug("Delete request with id: {} ", idRequest);
        Request request = requestDao.findOne(idRequest);
        Long progressStatusId = request.getProgressStatus().getId();
        if (progressStatusId == 0 || progressStatusId.equals(FREE_STATUS)) {
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
        // Define and set progress status
        ProgressStatus closedProgressStatus = progressStatusDao.findOne(CLOSED_STATUS);
        for (Request request : requests) {
            Long progressStatusId = request.getProgressStatus().getId();
            if (progressStatusId.equals(JOINED_STATUS)) {
                request.setProgressStatus(closedProgressStatus);
                Long parentRequestId = request.getParentId();
                request.setParentId(null);
                requestDao.save(request);
                requestDao.deleteParentRequestIfItHasNoChildren(parentRequestId);
            }
            if (progressStatusId.equals(IN_PROGRESS_STATUS)) {
                request.setProgressStatus(closedProgressStatus);
                requestDao.save(request);
            }
            sendMessageToAssignee(request);
        }
    }

    /**
     * Sends notification to Assignee of request.
     *
     * @param request request with changed {@link ProgressStatus}
     */
    private void sendMessageToAssignee(Request request) {
        val message = this.emailStrategyForAssignee.buildMessage(request);
        emailService.sendMessage(message);
    }

    /**
     * Sends notification to Reporter of request.
     *
     * @param request request with changed {@link ProgressStatus}
     */
    private void sendMessageToReporter(Request request) {
        val message = this.emailStrategyForReporter.buildMessage(request);
        emailService.sendMessage(message);
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


    @Override
    public List<Request> findClosedRequestsByReporter(Long reporterId, int pageNumber) {
        Assert.notNull(reporterId, "Reporter id must be not null");
        Assert.notNull(pageNumber, "Page number must be not null");
        return requestDao.findRequestsByReporterAndProgress(reporterId, "Closed",
                DEFAULT_PAGE_SIZE, pageNumber);
    }

    @Override
    public Long countClosedRequestsByReporter(Long reporterId) {
        Assert.notNull(reporterId, "Reporter id must be not null");
        return requestDao.countRequestsByReporterAndProgress(reporterId, "Closed");
    }

    @Override
    public Request createEmpRequest(Request request) {
        request.setDateOfCreation(LocalDateTime.now());
        val progress = progressStatusDao.findByName("Free");
        request.setProgressStatus(progress);
        request.setAssignee(new User());
        return requestDao.save(request);
    }
}
