package com.overseer.service;

import com.overseer.dto.DeadlineDTO;
import com.overseer.dto.RequestDTO;
import com.overseer.dto.RequestSearchDTO;
import com.overseer.model.PriorityStatus;
import com.overseer.model.ProgressStatus;
import com.overseer.model.Request;
import com.overseer.model.User;
import io.jsonwebtoken.lang.Assert;

import java.time.LocalDate;
import java.util.List;

/**
 * The <code>RequestService</code> interface represents access to RequestDao.
 */
public interface RequestService extends CrudService<Request, Long> {
    /**
     * Returns a list of closed requests which have provided {@link User} as reporter.
     *
     * @param reporterId id of the reporter, must not be {@literal null}.
     * @return list of closed requests which have provided {@link User} as reporter.
     */
    List<Request> findClosedRequestsByReporter(Long reporterId, int pageNumber, int size);

    /**
     * Returns number of closed requests for reporter.
     *
     * @param reporterId reporter id must be not null.
     * @return number of requests for reporter.
     */
    Long countClosedRequestsByReporter(Long reporterId);

    Long countRequestByReporter(Long reporterId);


    Long countRequestByAssignee(Long managerId);

    Long countClosedRequestByAssignee(Long managerId);

    Long countInProgressRequestByAssignee(Long managerId);
    /**
     * Returns a list of sub requests for the given request {@link Request}.
     *
     * @param parent request that contains sub requests, must not be {@literal null}.
     * @return list of sub requests.
     */
    List<Request> findSubRequests(Request parent);

    /**
     * Returns a list of requests that have been joined by the given request {@link Request}.
     *
     * @param parent parent request that joined requests, must not be {@literal null}.
     * @return list of joined requests.
     */
    List<Request> findJoinedRequests(Request parent);

    /**
     * Returns a list of requests which have provided {@link User} as assignee.
     *
     * @param assigneeId requests assignee, must not be {@literal null}.
     * @return list of requests which have provided {@link User} as assignee.
     */
    List<Request> findRequestsByAssignee(Long assigneeId, int pageNumber);

    /**
     * Returns a list of requests which have provided {@link User} as assignee.
     *
     * @param assigneeId requests assignee, must not be {@literal null}.
     * @return list of requests which have provided {@link User} as assignee.
     */
    List<Request> findInProgressRequestsByAssignee(Long assigneeId, int size, int pageNumber);

    /**
     * Returns a list of requests which have provided {@link User} as assignee.
     *
     * @param assigneeId requests assignee, must not be {@literal null}.
     * @return list of requests which have provided {@link User} as assignee.
     */
    List<Request> findClosedRequestsByAssignee(Long assigneeId, int size, int pageNumber);

    /**
     * Returns a list of requests which have provided {@link User} as reporter.
     *
     * @param reporterId requests reporterId, must not be {@literal null}.
     * @return list of requests which have provided {@link User} as reporter.
     */
    List<Request> findRequestsByReporter(Long reporterId, int pageNumber, int size);

    /**
     * Returns a list of requests which have provided {@link User} as reporter.
     *
     * @param reporter requests reporter, must not be {@literal null}.
     * @return list of requests which have provided {@link User} as reporter.
     */
    default List<Request> findRequestsByReporter(User reporter, int pageNumber, int size) {
        Assert.notNull(reporter);
        return findRequestsByReporter(reporter.getId(), pageNumber, size);
    }

    /**
     * Returns a list of requests with provided progress status {@link ProgressStatus}.
     *
     * @param progressStatus request's progress status, must not be {@literal null}.
     * @return list of requests with provided progress status {@link ProgressStatus}.
     */
    List<Request> findRequestsByProgress(ProgressStatus progressStatus, int pageNumber);

    /**
     * Returns a list of requests with provided progress status {@link PriorityStatus}.
     *
     * @param priorityStatus request's priority status, must not be {@literal null}.
     * @return list of requests with provided progress status {@link PriorityStatus}.
     */
    List<Request> findRequestsByPriority(PriorityStatus priorityStatus, int pageNumber);

    /**
     * Returns a list of requests created in provided period.
     *
     * @param start period start.
     * @param end   period end.
     * @return list of requests created in provided period.
     */
    List<Request> findRequestsByPeriod(LocalDate start, LocalDate end, int pageNumber);

    /**
     * Returns a request DTO created in provided period.
     *
     * @param start              period start.
     * @param end                period end.
     * @param progressStatusName progress status name.
     * @return request DTO created in provided period.
     */
    RequestDTO findCountRequestsByPeriod(LocalDate start, LocalDate end, String progressStatusName);

    /**
     * Returns a request DTO created in provided period.
     *
     * @param start              period start.
     * @param end                period end.
     * @param progressStatusName progress status name.
     * @param id                 manager id.
     * @return request DTO created in provided period.
     */
    RequestDTO findCountRequestsByManagerAndPeriod(LocalDate start, LocalDate end, String progressStatusName, int id);

    /**
     * Returns a list of requests DTO created in provided period.
     *
     * @param start              period start.
     * @param end                period end.
     * @param progressStatusName progress status name.
     * @return list of requests DTO created in provided period.
     */
    List<RequestDTO> findListCountRequestsByPeriod(LocalDate start, LocalDate end, String progressStatusName);

    /**
     * Returns a list of requests DTO created in provided period.
     *
     * @param start              period start.
     * @param end                period end.
     * @param progressStatusName progress status name.
     * @param id                 manager id.
     * @return list of requests DTO created in provided period.
     */
    List<RequestDTO> findListCountRequestsByManagerAndPeriod(LocalDate start, LocalDate end, String progressStatusName, int id);

    /**
     * Returns a list of best managers in provided period.
     *
     * @param start period start.
     * @param end   period end.
     * @return list of best managers in provided period.
     */
    List<RequestDTO> findBestManagersByPeriod(LocalDate start, LocalDate end, String progressStatusName);

    /**
     * Returns a list of requests created in provided date.
     *
     * @param date creation date, must not be {@literal null}.
     * @return list of requests created in provided date.
     */
    List<Request> findRequestsByDate(LocalDate date);

    /**
     * Joins specified requests by parent request.
     * Joined requests will have 'Joined' {@link Request#progressStatus}
     * and not null {@link Request#parentId}.
     *
     * @param ids specified requests primary keys for joining
     * @return created parent request
     */
    Request joinRequestsIntoParent(List<Long> ids, Request parentRequest);

    /**
     * Creates sub request for parent request.
     * Sub request will have null {@link Request#progressStatus}, {@link Request#priorityStatus}
     * and not null {@link Request#parentId}
     *
     * @param subRequest specified sub request
     * @param subRequest    specified sub request
     * @return joined sub request
     */
    Request saveSubRequest(Request subRequest);

    /**
     * Assigns request to specified office manager and changes it {@link Request#progressStatus}.
     *
     * @param request specified request
     * @return assigned request
     */
    Request assignRequest(Request request);

    /**
     * Closes request and changes it {@link Request#progressStatus}.
     *
     * @param request specified request
     * @return closed request
     */
    Request closeRequest(Request request);

    /**
     * Reopens request and changes it {@link Request#progressStatus}.
     *
     * @param requestId id of specified request
     * @return reopened request
     */
    Request reopenRequest(Long requestId);

    /**
     * Closes all requests which have provided {@link User} as reporter and have specified {@link ProgressStatus}.
     *
     * @param reporterId id of the reporter, must not be {@literal null}.
     */
    void closeAllRequestsOfGivenReporter(Long reporterId);

    /**
     * Save plain request from employee.
     *
     * @param request specified  request.
     * @return saved request
     */
    Request createEmpRequest(Request request);

    /**
     * Returns number of entities of type <code>T</code>.
     *
     * @return number of entities of type <code>T</code>.
     */
    Long countFreeRequests();

    /**
     * Returns number of entities of type <code>T</code>.
     *
     * @param id id of the {@link User}.
     * @return number of entities of type <code>T</code>.
     */
    Long countRequestsByAssignee(Long id);

    /**
     * Returns a list of requests with Free progress status {@link ProgressStatus}.
     *
     * @return list of requests with Free progress status {@link ProgressStatus}.
     */
    List<Request> findFreeRequests(int pageNumber, int size);

    /**
     * Returns list of filtered requests by specified searchRequests params in {@link RequestSearchDTO} object.
     *
     * @param searchDTO searchRequests params dto object
     * @return filtered requests list
     */
    List<Request> searchRequests(RequestSearchDTO searchDTO);

    /**
     * Returns list to build pie chart.
     *
     * @return list of statistic request by progress status.
     */
    List<Long> quantityByProgressStatus();

    /**
     * Returns list to build pie chart for user profile.
     *
     * @return list of statistic request for user profile.
     */
    List<Long> quantityForUser(Long userId);

    /**
     * Returns Returns list to build pie chart.
     *
     * @return list of statistic request by priority status.
     */
    List<Long> quantityByPriorityStatus();

    /**
     * @return list of statistic request by progress status.
     */
    List<Long> quantityByProgressStatusForSixMonths();

    /**
     * @return list of statistic request by progress status for user.
     */
    List<Long> quantityByProgressStatusForSixMonthsForUser(Long userId);

    /**
     * @return list of manager deadlines information entities.
     */
    List<DeadlineDTO> getManagerDeadlines(Long managerID);
}