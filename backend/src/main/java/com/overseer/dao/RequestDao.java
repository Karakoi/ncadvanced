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
     * Fetches from the database all Requests objects created or assigned with the same user.
     *
     * @param user represents belonging request to a user, must not be {@literal null}.
     * @return List of requests with same user.
     */

    default List<Request> getRequestsByUser(User user) {
        Long id = user.getId();
        Assert.notNull(id);
        return getRequestsByUser(id);
    }

    /**
     * Fetches from the database all Requests objects created or assigned with the same user.
     *
     * @param userId represents belonging request to a user, must not be {@literal null}.
     * @return List of requests with same user.
     */

    List<Request> getRequestsByUser(Long userId);

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
