package com.overseer.dao;

import com.overseer.caching.annotation.CacheChanger;
import com.overseer.dto.DeadlineDTO;
import com.overseer.dto.RequestDTO;
import com.overseer.model.PriorityStatus;
import com.overseer.model.ProgressStatus;
import com.overseer.model.Request;
import com.overseer.model.User;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;

/**
 * The <code>RequestDao</code> interface represents access to Request {@link Request} object in database.
 */
public interface RequestDao extends CrudDao<Request, Long> {

    /**
     * Returns a list of sub requests for the given request {@link Request}.
     *
     * @param id id of the request that contains sub requests, must not be {@literal null}..
     * @return list of sub requests.
     */
    List<Request> findSubRequests(Long id);

    /**
     * Returns a list of sub requests for the given request {@link Request}.
     *
     * @param parent request that contains sub requests, must not be {@literal null}.
     * @return list of sub requests.
     */
    default List<Request> findSubRequests(Request parent) {
        Assert.notNull(parent);
        Long id = parent.getId();
        return findSubRequests(id);
    }

    /**
     * Returns a list of requests that have been joined by the given request {@link Request}.
     *
     * @param id id of the request that joined requests, must not be {@literal null}..
     * @return list of joined requests.
     */
    List<Request> findJoinedRequests(Long id);

    /**
     * Returns a list of requests that have been joined by the given request {@link Request}.
     *
     * @param parent parent request that joined requests, must not be {@literal null}.
     * @return list of joined requests.
     */
    default List<Request> findJoinedRequests(Request parent) {
        Assert.notNull(parent, "parent is null");
        Long id = parent.getId();
        return findJoinedRequests(id);
    }

    /**
     * Returns a list of requests which have provided {@link User} as assignee.
     *
     * @param assigneeId id of the assignee, must not be {@literal null}.
     * @return list of requests which have provided {@link User} as assignee.
     */
    List<Request> findRequestsByAssignee(Long assigneeId, int pageSize, int pageNumber);

    /**
     * Returns a list of requests which have provided {@link User} as assignee.
     *
     * @param assignee requests assignee, must not be {@literal null}.
     * @return list of requests which have provided {@link User} as assignee.
     */
    default List<Request> findRequestsByAssignee(User assignee, int pageSize, int pageNumber) {
        Assert.notNull(assignee);
        Long id = assignee.getId();
        return findRequestsByAssignee(id, pageSize, pageNumber);
    }

    /**
     * Returns a list of requests which have provided {@link User} as assignee.
     *
     * @param assigneeId id of the assignee, must not be {@literal null}.
     * @return list of requests which have provided {@link User} as assignee.
     */
    List<Request> findInProgressRequestsByAssignee(Long assigneeId, int pageSize, int pageNumber);

    /**
     * Returns a list of requests which have provided {@link User} as assignee.
     *
     * @param assigneeId id of the assignee, must not be {@literal null}.
     * @return list of requests which have provided {@link User} as assignee.
     */
    List<Request> findClosedRequestsByAssignee(Long assigneeId, int pageSize, int pageNumber);

    /**
     * Returns a list of requests which have provided {@link User} as reporter.
     *
     * @param reporterId id of the reporter, must not be {@literal null}.
     * @return list of requests which have provided {@link User} as reporter.
     */
    List<Request> findRequestsByReporter(Long reporterId, int pageSize, int pageNumber);

    /**
     * Returns a list of requests which have provided {@link User} as reporter.
     *
     * @param reporter requests reporter, must not be {@literal null}.
     * @return list of requests which have provided {@link User} as reporter.
     */
    default List<Request> findRequestsByReporter(User reporter, int pageSize, int pageNumber) {
        Assert.notNull(reporter);
        Long id = reporter.getId();
        return findRequestsByReporter(id, pageSize, pageNumber);
    }

    /**
     * Returns number of requests for reporter.
     *
     * @param reporterId reporter id must be not null.
     * @return number of requests for reporter.
     */
    Long countRequestsByReporter(Long reporterId);

    /**
     * Returns number of requests for assignee.
     *
     * @param managerId assignee id must be not null.
     * @return number of requests for assignee.
     */
    Long countRequestsByAssignee(Long managerId);

    /**
     * Returns number of requests for assignee.
     *
     * @param managerId assignee id must be not null.
     * @return number of requests for assignee.
     */
    Long countInProgressRequestByAssignee(Long managerId);

    /**
     * Returns number of requests for assignee.
     *
     * @param managerId assignee id must be not null.
     * @return number of requests for assignee.
     */
    Long countClosedRequestByAssignee(Long managerId);

    /**
     * Returns a list of closed requests which have provided {@link User} as reporter.
     *
     * @param reporterId id of the reporter, must not be {@literal null}.
     * @return list of closed requests which have provided {@link User} as reporter.
     */
    List<Request> findRequestsByReporterAndProgress(Long reporterId, String progress, int pageSize, int pageNumber);

    /**
     * Returns number of closed requests for reporter.
     *
     * @param reporterId reporter id must be not null.
     * @return number of requests for reporter.
     */
    Long countRequestsByReporterAndProgress(Long reporterId, String progress);

    /**
     * Returns a list of requests with provided progress status {@link ProgressStatus}.
     *
     * @param statusId id of the progress status, must not be {@literal null}.
     * @return list of requests with provided progress status {@link ProgressStatus}.
     */
    List<Request> findRequestsByProgress(Long statusId, int pageSize, int pageNumber);

    /**
     * Returns a list of requests with provided progress status {@link ProgressStatus}.
     *
     * @param progressStatus request's progress status, must not be {@literal null}.
     * @return list of requests with provided progress status {@link ProgressStatus}.
     */
    default List<Request> findRequestsByProgress(ProgressStatus progressStatus, int pageSize, int pageNumber) {
        Assert.notNull(progressStatus);
        Long id = progressStatus.getId();
        return findRequestsByProgress(id, pageSize, pageNumber);
    }

    /**
     * Returns a list of requests with provided progress status {@link PriorityStatus}.
     *
     * @param priorityId id of the priority status, must not be {@literal null}.
     * @return list of requests with provided progress status {@link PriorityStatus}.
     */
    List<Request> findRequestsByPriority(Long priorityId, int pageSize, int pageNumber);

    /**
     * Returns a list of requests with provided progress status {@link PriorityStatus}.
     *
     * @param priorityStatus request's priority status, must not be {@literal null}.
     * @return list of requests with provided progress status {@link PriorityStatus}.
     */
    default List<Request> findRequestsByPriority(PriorityStatus priorityStatus, int pageSize, int pageNumber) {
        Assert.notNull(priorityStatus);
        Long id = priorityStatus.getId();
        return findRequestsByPriority(id, pageSize, pageNumber);
    }

    /**
     * Returns a list of requests created in provided period.
     *
     * @param start period start.
     * @param end   period end.
     * @return list of requests created in provided period.
     */
    List<Request> findRequestsByPeriod(LocalDate start, LocalDate end, int pageSize, int pageNumber);


    /**
     * Returns a list of request DTO created in provided period.
     *
     * @param start              period start.
     * @param end                period end.
     * @param progressStatusName progress status name.
     * @return list of request DTO created in provided period.
     */
    List<RequestDTO> findListCountRequestsByPeriod(LocalDate start, LocalDate end, String progressStatusName);

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
     * Returns a list of request DTO created in provided period.
     *
     * @param start              period start.
     * @param end                period end.
     * @param progressStatusName progress status name.
     * @param id                 manager id.
     * @return list of request DTO created in provided period.
     */
    List<RequestDTO> findListCountRequestsByManagerAndPeriod(LocalDate start, LocalDate end, String progressStatusName, int id);

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
     * Returns a list of best managers in provided period.
     *
     * @param start        period start.
     * @param end          period end.
     * @param progressName progress status name.
     * @return list of best managers in provided period.
     */
    List<RequestDTO> findListOfBestManagersByPeriod(LocalDate start, LocalDate end, String progressName);

    /**
     * Returns a list of requests created in provided date.
     *
     * @param date creation date, must not be {@literal null}.
     * @return list of requests created in provided date.
     */
    List<Request> findRequestsByDate(LocalDate date);

    /**
     * Returns a list of requests with specified id's.
     *
     * @param ids input id's
     * @return filtered list of requests
     */
    List<Request> findRequestsByIds(List<Long> ids);

    /**
     * Returns number of entities of type <code>T</code>.
     *
     * @return number of entities of type <code>T</code>.
     */
    Long countFree();

    /**
     * Returns number of entities of type <code>T</code>.
     *
     * @param assigneeId id of the {@link User}.
     * @return number of entities of type <code>T</code>.
     */
    Long countByAssignee(Long assigneeId);

    /**
     * Returns a list of requests with Free progress status {@link ProgressStatus}.
     *
     * @return list of requests with Free progress status {@link ProgressStatus}.
     */
    List<Request> findFreeRequests(int pageSize, int pageNumber);

    /**
     * Returns list of filtered requests by specified search query.
     *
     * @param searchQuery search params sql query
     * @return list of filtered requests
     */
    List<Request> searchRequests(String searchQuery);

    /**
     * Gets a list of requests which have provided {@link User} as reporter and specified {@link ProgressStatus}.
     *
     * @param statusIds list of progress status ids, must not be {@literal null}
     * @param reporterId id of the reporter, must not be {@literal null}.
     * @return list of requests
     */
    List<Request> findRequestsByProgressStatusesAndReporterId(List<Long> statusIds, Long reporterId);

    /**
     * Deletes parent request if it has no children.
     *
     * @param parentId list of progress status ids, must not be {@literal null}
     */
    @CacheChanger
    void deleteParentRequestIfItHasNoChildren(Long parentId);

    /**
     * Returns list to build pie chart for user profile.
     *
     * @return list of statistic request for user profile.
     */
    List<Long> countRequestByProgressStatusForUser(Long userId);

    /**
     * @return list of statistic for six months by progress status for user.
     */
    List<Long> countOpenClosedRequestForUser(Long userId, Long howLong);

    /**
     * @return number of total Users.
     */
    Long countTotalUsers();

    /**
     * @return number of total Requests.
     */
    Long countTotalRequests();

    /**
     * @return number of today's requests for admin dashboard to create statistic.
     */
    Long countRequestsCreatedToday();

    /**
     * @return number of today's requests that were changed progress status from free to anyone except to closed.
     */
    Long countRequestsRunningToday();

    /**
     * @param howLong set which exactly statistic we need.
     * @return list of statistic for admin dashboard.
     */
    List<Long> statisticForAdminDashBoard(Long howLong);

    /**
     * @return list of manager deadlines information entity.
     */
    List<DeadlineDTO> getDeadlinesByAssignee(Long assigneeID);
}
