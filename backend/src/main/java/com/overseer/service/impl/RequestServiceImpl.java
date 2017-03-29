package com.overseer.service.impl;

import com.overseer.dao.RequestDao;
import com.overseer.dao.UserDao;
import com.overseer.dto.DeadlineDTO;
import com.overseer.dto.RequestDTO;
import com.overseer.dto.RequestSearchDTO;
import com.overseer.event.AssignRequestEvent;
import com.overseer.event.CloseRequestEvent;
import com.overseer.event.JoinRequestEvent;
import com.overseer.event.ReopenRequestEvent;
import com.overseer.exception.InappropriateProgressStatusException;
import com.overseer.exception.entity.NoSuchEntityException;
import com.overseer.model.PriorityStatus;
import com.overseer.model.Request;
import com.overseer.model.User;
import com.overseer.model.enums.ProgressStatus;
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

    private RequestDao requestDao;
    private UserDao userDao;

    private ApplicationEventPublisher publisher;

    public RequestServiceImpl(RequestDao requestDao, UserDao userDao) {
        super(requestDao);
        this.userDao = userDao;
        this.requestDao = requestDao;
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }

    //-----------------------CRUD---------------------------

    /**
     * {@inheritDoc}.
     */
    @Override
    public Request update(Request request) throws NoSuchEntityException {
        Assert.notNull(request, "request must not be null");
        log.debug("Updating request with id: {} ", request.getId());
        Long progressStatusId = request.getProgressStatus().getId();
        if (!ProgressStatus.FREE.getId().equals(progressStatusId)) {
            throw new InappropriateProgressStatusException("Request with id: "
                    + request.getId() + " and ProgressStatus: "
                    + request.getProgressStatus().getName()
                    + " can not be updated");
        }
        return super.update(request);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void delete(Long idRequest) {
        Assert.notNull(idRequest, "id of request must not be null");
        log.debug("Delete request with id: {} ", idRequest);
        Request request = requestDao.findOne(idRequest);
        Long progressStatusId = request.getProgressStatus().getId();
        if (progressStatusId == null || ProgressStatus.FREE.getId().equals(progressStatusId)) {
            super.delete(idRequest);
        } else {
            throw new InappropriateProgressStatusException("Request with id: "
                    + request.getId() + " and ProgressStatus: "
                    + request.getProgressStatus().getName()
                    + " can not be deleted");
        }
    }


    //-----------------------FIND---------------------------

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
    public List<Request> findRequestsWithGivenProgressByAssignee(Long assigneeId, ProgressStatus progressStatus, int size, int pageNumber) {
        Assert.notNull(assigneeId, "assignee must not be null");
        List<Request> requestsOfGivenAssignee = this.requestDao.findRequestsWithGivenProgressByAssignee(assigneeId, progressStatus, size, pageNumber);
        log.debug("Fetched {} requests for assignee with id: {} for page number: {}",
                requestsOfGivenAssignee.size(), assigneeId, pageNumber);
        return requestsOfGivenAssignee;
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
    public List<Request> findClosedRequestsByReporter(Long reporterId, int pageNumber, int size) {
        Assert.notNull(reporterId, "Reporter id must be not null");
        Assert.notNull(pageNumber, "Page number must be not null");
        return requestDao.findRequestsByReporterAndProgress(reporterId, ProgressStatus.CLOSED, size, pageNumber);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<Request> findFreeRequests(int pageNumber, int size) {
        return requestDao.findFreeRequests(size, pageNumber);
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

    //-----------------------DEADLINE---------------------------

    @Override
    public List<DeadlineDTO> getManagerDeadlines(Long managerID) {
        return requestDao.getDeadlinesByAssignee(managerID);
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

    //-----------------------REPORT---------------------------

    /**
     * {@inheritDoc}.
     */
    @Override
    public RequestDTO findCountRequestsByPeriod(LocalDate start, LocalDate end, Long progressStatusId) {
        RequestDTO requestDTO = this.requestDao.findCountRequestsByPeriod(start, end, progressStatusId);
        log.debug("Fetched {} count of requests for period {} - {}", requestDTO, start, end);
        return requestDTO;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<RequestDTO> findListCountRequestsByPeriod(LocalDate start, LocalDate end, Long progressStatusId) {
        List<RequestDTO> list = this.requestDao.findListCountRequestsByPeriod(start, end, progressStatusId);
        log.debug("Fetched {} request DTO's for period {} - {}", list.size(), start, end);
        return list;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public RequestDTO findCountRequestsByManagerAndPeriod(LocalDate start, LocalDate end, Long progressStatusId, int id) {
        RequestDTO requestDTO = this.requestDao.findCountRequestsByManagerAndPeriod(start, end, progressStatusId, id);
        log.debug("Fetched {} count of requests for period {} - {}", requestDTO, start, end);
        return requestDTO;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<RequestDTO> findListCountRequestsByManagerAndPeriod(LocalDate start, LocalDate end, Long progressStatusId, int id) {
        List<RequestDTO> list = this.requestDao.findListCountRequestsByManagerAndPeriod(start, end, progressStatusId, id);
        log.debug("Fetched {} request DTO's for period {} - {}", list.size(), start, end);
        return list;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<RequestDTO> findBestManagersByPeriod(LocalDate start, LocalDate end, Long progressStatusId, int countTop) {
        List<RequestDTO> list = this.requestDao.findListOfBestManagersByPeriod(start, end, progressStatusId, countTop);
        log.debug("Fetched {} request DTO's for period {} - {}", list.size(), start, end);
        return list;
    }

    //-----------------------QUANTITY---------------------------

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

    //-----------------------LIFECYCLE---------------------------

    /**
     * {@inheritDoc}.
     */
    @Override
    public Request saveSubRequest(Request subRequest) {
        Assert.notNull(subRequest, "sub request must not be null");
        log.debug("Create sub request {} for parent request with id {}", subRequest, subRequest.getParentId());
        subRequest.getProgressStatus().getId();
        return requestDao.save(subRequest);
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
            if (!ProgressStatus.FREE.getId().equals(request.getProgressStatus().getId())) {
                throw new InappropriateProgressStatusException("Can not join request with id: " + request.getId()
                        + " because it has progress status that is not [Free]");
            }
        });

        JoinRequestEvent event = new JoinRequestEvent(this, parentRequest, joinedRequests);
        publisher.publishEvent(event);

        return parentRequest;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public Request assignRequest(Request request) {
        Assert.notNull(request, "request must not be null");
        log.debug("Assign request with id: {} to office manager with id: {}", request.getId(), request.getAssignee().getId());
        if (!ProgressStatus.FREE.getId().equals(request.getProgressStatus().getId())) {
            throw new InappropriateProgressStatusException("Request with id: "
                    + request.getId() + " and ProgressStatus: "
                    + request.getProgressStatus().getName()
                    + " can not be assign");
        }

        AssignRequestEvent event = new AssignRequestEvent(this, request);
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
        if (!ProgressStatus.IN_PROGRESS.getId().equals(progressStatusId) && !ProgressStatus.JOINED.getId().equals(progressStatusId)) {
            throw new InappropriateProgressStatusException("Request with id: "
                    + request.getId() + " and ProgressStatus: "
                    + request.getProgressStatus().getName()
                    + " can not be closed");
        }

        CloseRequestEvent event = new CloseRequestEvent(this, request);
        publisher.publishEvent(event);
        return request;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public Request reopenRequest(Long requestId) {
        Assert.notNull(requestId, "id of request must not be null");
        log.debug("Reopen request with id: {} ", requestId);

        Request request = requestDao.findOne(requestId);
        if (request == null) {
            throw new NoSuchEntityException("Request with given id: " + requestId + " is absent in DB");
        }
        User reporter = userDao.findByEmail(request.getReporter().getEmail());
        if (reporter == null) {
            throw new NoSuchEntityException("Reporter of request with id: " + requestId + " is absent in DB");
        }
        if (!ProgressStatus.CLOSED.getId().equals(request.getProgressStatus().getId())) {
            throw new InappropriateProgressStatusException("Request with id: "
                    + request.getId() + " and ProgressStatus: "
                    + request.getProgressStatus().getName()
                    + " can not be reopen");
        }

        ReopenRequestEvent event = new ReopenRequestEvent(this, request);
        publisher.publishEvent(event);
        return request;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void closeAllRequestsOfGivenReporter(Long reporterId) {
        Assert.notNull(reporterId, "id of request must not be null");
        log.debug("Close all requests of Reporter with id: {} ", reporterId);
        List<Long> idsOfProgresStatuses = new ArrayList<>();
        idsOfProgresStatuses.add(ProgressStatus.IN_PROGRESS.getId());
        idsOfProgresStatuses.add(ProgressStatus.JOINED.getId());
        List<Request> requests = requestDao.findRequestsByProgressStatusesAndReporterId(idsOfProgresStatuses, reporterId);
        requests.forEach(this::closeRequest);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void deleteAllFreeRequestsOfGivenReporter(Long reporterId) {
        Assert.notNull(reporterId, "id of request must not be null");
        log.debug("Close all requests of Reporter with id: {} ", reporterId);
        List<Long> idsOfProgresStatuses = new ArrayList<>();
        idsOfProgresStatuses.add(ProgressStatus.FREE.getId());
        List<Request> requests = requestDao.findRequestsByProgressStatusesAndReporterId(idsOfProgresStatuses, reporterId);
        requests.forEach(requestDao::delete);
    }

    //-----------------------COUNT---------------------------

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
    public Long getRunningRequestToday() {
        return requestDao.countRequestsRunningToday();
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

    @Override
    public Long countRequestByReporter(Long reporterId) {
        return requestDao.countRequestsByReporter(reporterId);
    }

    @Override
    public Long countClosedRequestsByReporter(Long reporterId) {
        Assert.notNull(reporterId, "Reporter id must be not null");
        return requestDao.countRequestsWithNullParentByReporterAndProgress(reporterId, ProgressStatus.CLOSED);
    }

    @Override
    public Long countRequestsWithGivenProgressByAssignee(Long managerId, ProgressStatus progressStatus) {
        return requestDao.countRequestsWithGivenProgressByAssignee(managerId, progressStatus);
    }

    @Override
    public Long countFreeRequests() {
        val freeRequestsQuantity = requestDao.countFree();
        log.debug("Counted {} requests with Free progress status", freeRequestsQuantity);
        return freeRequestsQuantity;
    }

    @Override
    public Long countRequestsByAssignee(Long assigneeId) {
        val requestsByAssignee = requestDao.countRequestsByAssignee(assigneeId);
        log.debug("Counted {} requests for user with id: {}", requestsByAssignee, assigneeId);
        return requestsByAssignee;
    }

    @Override
    public Request createEmpRequest(Request request) {
        request.setDateOfCreation(LocalDateTime.now());
        request.setProgressStatus(ProgressStatus.FREE);
        request.setAssignee(new User());
        return requestDao.save(request);
    }
}
