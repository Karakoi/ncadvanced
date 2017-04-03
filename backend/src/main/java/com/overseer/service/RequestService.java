package com.overseer.service;

import com.overseer.dto.DeadlineDTO;
import com.overseer.dto.RequestDTO;
import com.overseer.dto.RequestSearchDTO;
import com.overseer.model.PriorityStatus;
import com.overseer.model.Request;
import com.overseer.model.User;
import com.overseer.model.enums.ProgressStatus;
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

    /**
     * Returns number of requests for assignee.
     *
     * @param assigneeId     assignee id must be not null.
     * @param progressStatus progress status must be not null.
     * @return number of requests for assignee.
     */
    Long countRequestsWithGivenProgressByAssignee(Long assigneeId, ProgressStatus progressStatus);

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
     * @param assigneeId     id of the assignee, must not be {@literal null}.
     * @param progressStatus progress status of request, must not be {@literal null}.
     * @param pageSize       amount of fetch size, must not be {@literal null}.
     * @param pageNumber     page number, must not be {@literal null}.
     * @return list of requests which have provided {@link User} as assignee with given request.
     */
    List<Request> findRequestsWithGivenProgressByAssignee(Long assigneeId, ProgressStatus progressStatus, int pageSize, int pageNumber);

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
     * @param start            period start.
     * @param end              period end.
     * @param progressStatusId progress status id.
     * @return request DTO created in provided period.
     */
    RequestDTO findCountRequestsByPeriod(LocalDate start, LocalDate end, Long progressStatusId);

    /**
     * Returns a request DTO created in small period.
     *
     * @param start            period start.
     * @param end              period end.
     * @param progressStatusId progress status id.
     * @return request DTO created in small period.
     */
    RequestDTO findCountRequestsBySmallPeriod(LocalDate start, LocalDate end, Long progressStatusId);

    /**
     * Returns a request DTO created in provided period by manager id.
     *
     * @param start            period start.
     * @param end              period end.
     * @param progressStatusId progress status id.
     * @param id               manager id.
     * @return request DTO created in provided period.
     */
    RequestDTO findCountRequestsByManagerAndPeriod(LocalDate start, LocalDate end, Long progressStatusId, int id);

    /**
     * Returns a request DTO created in small period by manager id.
     *
     * @param start            period start.
     * @param end              period end.
     * @param progressStatusId progress status id.
     * @param id               manager id.
     * @return request DTO created in small period.
     */
    RequestDTO findCountRequestsByManagerAndSmallPeriod(LocalDate start, LocalDate end, Long progressStatusId, int id);

    /**
     * Returns a list of requests DTO created in provided period.
     *
     * @param start            period start.
     * @param end              period end.
     * @param progressStatusId progress status id.
     * @return list of requests DTO created in provided period.
     */
    List<RequestDTO> findListCountRequestsByPeriod(LocalDate start, LocalDate end, Long progressStatusId);

    /**
     * Returns a list of requests DTO created in provided period by manager id.
     *
     * @param start            period start.
     * @param end              period end.
     * @param progressStatusId progress status id.
     * @param id               manager id.
     * @return list of requests DTO created in provided period.
     */
    List<RequestDTO> findListCountRequestsByManagerAndPeriod(LocalDate start, LocalDate end, Long progressStatusId, int id);

    /**
     * Returns a list of best managers in provided period.
     *
     * @param start            period start.
     * @param end              period end.
     * @param progressStatusId progress status id.
     * @param countTop         count managers in top.
     * @return list of best managers in provided period.
     */
    List<RequestDTO> findBestManagersByPeriod(String start, String end, Long progressStatusId, int countTop);

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
    Request joinRequestsIntoParent(String ids, Request parentRequest);

    /**
     * Creates sub request for parent request.
     * Sub request will have null {@link Request#progressStatus}, {@link Request#priorityStatus}
     * and not null {@link Request#parentId}
     *
     * @param subRequest specified sub request
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
     * Closes all requests which have provided {@link User} as Assignee and have specified ProgressStatus.
     *
     * @param assigneeId id of the Assignee, must not be {@literal null}.
     */
    void closeAllRequestsOfGivenAssignee(Long assigneeId);

    /**
     * Closes all requests which have provided {@link User} as reporter and have specified ProgressStatus.
     *
     * @param reporterId id of the reporter, must not be {@literal null}.
     */
    void closeAllRequestsOfGivenReporter(Long reporterId);


    /**
     * Delete all Free requests which have provided {@link User} as reporter.
     *
     * @param reporterId id of the reporter, must not be {@literal null}.
     */
    void deleteAllFreeRequestsOfGivenReporter(Long reporterId);

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
     * Returns a list of requests with Free progress status  ProgressStatus.
     *
     * @return list of requests with Free progress status ProgressStatus.
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
     * @return list of statistic request by progress status for user.
     */
    List<Long> quantityByProgressStatusForUser(Long userId);

    /**
     * @return list of closed and open requests for bar chart on user page.
     */
    List<Long> quantityOpenClosedRequestForUser(Long userId, Long howLong);

    /**
     * @return number of total Users.
     */
    Long countTotalUsers();

    /**
     * @return number of total requests.
     */
    Long countTotalRequests();

    /**
     * @return number of today's requests.
     */
    Long countRequestsCreatedToday();

    /**
     * @return number of today's requests that were chanced progress status from free to anyone except to closed and free.
     */
    Long getRunningRequestToday();

    /**
     * @return list of statistic for admin dashboard.
     */
    List<Long> countStatisticForAdminDashBoard(Long howLong);

    /**
     * @return list of manager deadlines information entities.
     */
    List<DeadlineDTO> getManagerDeadlines(Long managerID);
}