package com.overseer.dao;

import com.overseer.model.*;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;

/**
 * The <code>RequestDao</code> interface represents access to Request {@link Request} object in database.
 */
public interface RequestDao extends CrudDao<Request, Long> {
    /**
     * Fetches from the database all Requests objects with same progress status.
     *
     * @param progressStatus request's status which represents completion progress, must not be {@literal null}.
     * @return List of requests with same progress status.
     */
    List<Request> getRequestsByStatus(ProgressStatus progressStatus);

    /**
     * Fetches from the database all Requests objects with same group.
     *
     * @param joinedRequest request's property which represents belonging to a group, must not be {@literal null}.
     * @return List of requests with same progress status.
     */

    default List<Request> getRequestsByJoined(JoinedRequest joinedRequest) {
        Long id = joinedRequest.getId();
        Assert.notNull(id);
        return  getRequestsByJoined(id);
    }

    /**
     * Fetches from the database all Requests objects with same group.
     *
     * @param joinedRequestId request's property which represents belonging to a group, must not be {@literal null}.
     * @return List of requests with same progress status.
     */
    List<Request> getRequestsByJoined(Long joinedRequestId);

    /**
     * Fetches from the database all Requests objects assigned to the same user.
     *
     * @param assignee represents belonging request to a user, must not be {@literal null}.
     * @return List of requests with same user.
     */

    default List<Request> getRequestsByAssignee(User assignee) {
        Long id = assignee.getId();
        Assert.notNull(id);
        return getRequestsByAssignee(id);
    }

    /**
     * Fetches from the database all Requests objects assigned to the same user.
     *
     * @param assigneeId represents belonging request to a user, must not be {@literal null}.
     * @return List of requests with same user.
     */

    List<Request> getRequestsByAssignee(Long assigneeId);

    /**
     * Fetches from the database all Requests objects created with the same user.
     *
     * @param reporter represents belonging request to a user, must not be {@literal null}.
     * @return List of requests with same user.
     */

    default List<Request> getRequestsByReporter(User reporter) {
        Long id = reporter.getId();
        Assert.notNull(id);
        return getRequestsByReporter(id);
    }

    /**
     * Fetches from the database all Requests objects created with the same user.
     *
     * @param reporterId represents belonging request to a user, must not be {@literal null}.
     * @return List of requests with same user.
     */

    List<Request> getRequestsByReporter(Long reporterId);

    /**
     * Fetches from the database all Requests objects which created in the same period.
     *
     * @param begin date from
     * @param end   date from
     * @return rerun list of requests from one period of time
     */

    List<Request> getRequestsByPeriod(LocalDate begin, LocalDate end);

    /**
     * Fetches from the database all Requests objects created in the same day.
     *
     * @param date request's property which represents belonging to a group, must not be {@literal null}.
     * @return List of requests with same date.
     */
    List<Request> getRequestByDate(LocalDate date);

    /**
     * Fetches from the database all Requests objects with same priority status.
     *
     * @param priorityStatus request's property which represents belonging to a group, must not be {@literal null}.
     * @return List of requests with same priorityStatus status.
     */
    List<Request> getRequestsByPriority(PriorityStatus priorityStatus);

}
