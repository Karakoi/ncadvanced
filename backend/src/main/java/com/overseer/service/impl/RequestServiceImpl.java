package com.overseer.service.impl;

import com.overseer.dao.ProgressStatusDao;
import com.overseer.dao.RequestDao;
import com.overseer.dto.DeadlineDTO;
import com.overseer.dto.RequestDTO;
import com.overseer.dto.RequestSearchDTO;
import com.overseer.event.ChangeProgressStatusEvent;
import com.overseer.exception.InappropriateProgressStatusException;
import com.overseer.exception.entity.NoSuchEntityException;
import com.overseer.model.PriorityStatus;
import com.overseer.model.ProgressStatus;
import com.overseer.model.Request;
import com.overseer.model.User;
import com.overseer.service.RequestService;
import com.overseer.service.impl.builder.SqlQueryBuilder;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @Override
    public Long countRequestByAssignee(Long managerId) {
        return requestDao.countRequestsByAssignee(managerId);
    }

    @Override
    public Long countInProgressRequestByAssignee(Long managerId) {
        return requestDao.countInProgressRequestByAssignee(managerId);
    }

    @Override
    public Long countClosedRequestByAssignee(Long managerId) {
        return requestDao.countClosedRequestByAssignee(managerId);
    }

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
    public List<Request> findInProgressRequestsByAssignee(Long assigneeId, int size, int pageNumber) {
        Assert.notNull(assigneeId, "assignee must not be null");
        val list = this.requestDao.findInProgressRequestsByAssignee(assigneeId, size, pageNumber);
        log.debug("Fetched {} requests for assignee with id: {} for page number: {}",
                list.size(), assigneeId, pageNumber);
        return list;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<Request> findClosedRequestsByAssignee(Long assigneeId, int size, int pageNumber) {
        Assert.notNull(assigneeId, "assignee must not be null");
        val list = this.requestDao.findClosedRequestsByAssignee(assigneeId, size, pageNumber);
        log.debug("Fetched {} requests for assignee with id: {} for page number: {}",
                list.size(), assigneeId, pageNumber);
        return list;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<Request> findRequestsByReporter(Long reporterId, int pageNumber, int size) {
        Assert.notNull(reporterId, "reporter must not be null");
        val list = this.requestDao.findRequestsByReporter(reporterId, size, pageNumber);
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
        List<Request> joinedRequests = requestDao.findRequestsByIds(ids);

        //check if joinedRequests are appropriate
        joinedRequests.forEach(request -> {
            if (request.getProgressStatus().getId() != FREE_STATUS) {
                throw new InappropriateProgressStatusException("Can not join request with id: " + request.getId()
                        + " because it has progress status that is not [Free]");
            }
        });

        // Define progress status with 'Joined' value for child requests
        ProgressStatus joinedProgressStatus = progressStatusDao.findOne(JOINED_STATUS);
        ProgressStatus parentProgressStatus = progressStatusDao.findOne(IN_PROGRESS_STATUS);
        parentRequest.setProgressStatus(parentProgressStatus);
        ChangeProgressStatusEvent event = new ChangeProgressStatusEvent(this, joinedProgressStatus, parentRequest, joinedRequests);
        publisher.publishEvent(event);

        return parentRequest;
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
    public List<Request> findFreeRequests(int pageNumber, int size) {
        return requestDao.findFreeRequests(size, pageNumber);
    }

    @Override
    public List<Request> searchRequests(RequestSearchDTO searchDTO) {
        SqlQueryBuilder sqlQueryBuilder = new SqlQueryBuilder();

        sqlQueryBuilder.where().isNull("r.parent_id");

        String title = searchDTO.getTitle();
        if (!title.isEmpty()) {
            sqlQueryBuilder.and().like("title", title);
        }

        String reporterName = searchDTO.getReporterName();
        if (!reporterName.isEmpty()) {
            sqlQueryBuilder.and().like(new String[]{"reporter.first_name", "reporter.last_name", "reporter.second_name"}, reporterName);
        }

        String assigneeName = searchDTO.getAssigneeName();
        if (!assigneeName.isEmpty()) {
            sqlQueryBuilder.and().like(new String[]{"assignee.first_name", "assignee.last_name", "assignee.second_name"}, assigneeName);
        }

        String estimate = searchDTO.getEstimate();
        if (!estimate.isEmpty()) {
            sqlQueryBuilder.and().equal("r.estimate_time_in_days", estimate);
        }

        String progress = searchDTO.getProgress();
        if (!progress.isEmpty()) {
            sqlQueryBuilder.and().equal("progress.name", progress);
        }

        String priority = searchDTO.getPriority();
        if (!priority.isEmpty()) {
            sqlQueryBuilder.and().equal("priority.name", priority);
        }

        String dateOfCreation = searchDTO.getDateOfCreation();
        if (!dateOfCreation.isEmpty()) {
            sqlQueryBuilder.and().equalDate("date_of_creation", dateOfCreation);
        }

        int limit = searchDTO.getLimit();
        sqlQueryBuilder.limit(limit);

        String query = sqlQueryBuilder.build();

        return requestDao.searchRequests(query);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<Long> quantityByProgressStatusForUser(Long userId) {
        return requestDao.countRequestByProgressStatusForUser(userId);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<Long> quantityOpenClosedRequestForUser(Long userId, Long howLong) {
        return requestDao.countOpenClosedRequestForUser(userId, howLong);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public Long countTotalUsers() {
        return requestDao.countTotalUsers();
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public Long countTotalRequests() {
        return requestDao.countTotalRequests();
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public Long countRequestsCreatedToday() {
        return requestDao.countRequestsCreatedToday();
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public Long getRunningRequestToday() {
        return requestDao.countRequestsRunningToday();
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<Long> countStatisticForAdminDashBoard(Long howLong) {
        return requestDao.statisticForAdminDashBoard(howLong);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public Request assignRequest(Request request) {
        Assert.notNull(request, "request must not be null");
        System.out.println("In the assign Service method");
        System.out.println("Request: " + request);
        System.out.println("Progress: " + request.getProgressStatus().getId());
        log.debug("Assign request with id: {} to office manager with id: {}", request.getId(), request.getAssignee().getId());
        if (request.getProgressStatus().getId() != FREE_STATUS) {
            throw new InappropriateProgressStatusException("Request with id: "
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
        if (progressStatusId != IN_PROGRESS_STATUS && progressStatusId != JOINED_STATUS) {
            throw new InappropriateProgressStatusException("Request with id: "
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

    /**
     * Closes request with any progress status and changes it {@link Request#progressStatus}.
     *
     * @param request specified request
     * @return closed request
     */
    private Request forceCloseRequest(Request request) {
        Assert.notNull(request, "request must not be null");
        log.debug("Close request with id: {} ", request.getId());
        ProgressStatus closedProgressStatus = progressStatusDao.findOne(CLOSED_STATUS);
        ChangeProgressStatusEvent event = new ChangeProgressStatusEvent(this, closedProgressStatus, request);
        publisher.publishEvent(event);
        return request;
    }

    @Override
    public Request reopenRequest(Long requestId) {
        Assert.notNull(requestId, "id of request must not be null");
        log.debug("Reopen request with id: {} ", requestId);

        Request request = requestDao.findOne(requestId);
        if (request == null) {
            throw new NoSuchEntityException("Request with given id: " + requestId + " is absent in DB");
        }
        if (request.getProgressStatus().getId() != CLOSED_STATUS) {
            throw new InappropriateProgressStatusException("Request with id: "
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
    public Request update(Request request) throws NoSuchEntityException {
        Assert.notNull(request, "request must not be null");
        log.debug("Updating request with id: {} ", request.getId());
        Long progressStatusId = request.getProgressStatus().getId();
        if (!progressStatusId.equals(FREE_STATUS)) {
            throw new InappropriateProgressStatusException("Request with id: "
                    + request.getId() + " and ProgressStatus: "
                    + request.getProgressStatus().getName()
                    + " can not be updated");
        }
        return super.update(request);
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
            throw new InappropriateProgressStatusException("Request with id: "
                    + request.getId() + " and ProgressStatus: "
                    + request.getProgressStatus().getName()
                    + " can not be deleted");
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
        requests.forEach(this::forceCloseRequest);
    }

    @Override
    public Long countRequestByReporter(Long reporterId) {
        return requestDao.countRequestsByReporter(reporterId);
    }

    @Override
    public List<Request> findClosedRequestsByReporter(Long reporterId, int pageNumber, int size) {
        Assert.notNull(reporterId, "Reporter id must be not null");
        Assert.notNull(pageNumber, "Page number must be not null");
        return requestDao.findRequestsByReporterAndProgress(reporterId, "Closed", size, pageNumber);
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

    @Override
    public List<DeadlineDTO> getManagerDeadlines(Long managerID) {
        return requestDao.getDeadlinesByAssignee(managerID);
    }
}
