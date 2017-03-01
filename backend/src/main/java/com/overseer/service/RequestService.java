package com.overseer.service;

import com.overseer.model.PriorityStatus;
import com.overseer.model.ProgressStatus;
import com.overseer.model.Request;
import com.overseer.model.User;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;

/**
 * The <code>RequestService</code> interface represents access to RequestDao.
 */
public interface RequestService extends CrudService<Request, Long> {

    /**
     * Returns a list of joined requests.
     *
     * @return list of joined requests
     */
    List<Request> getJoinedGroups();

    /**
     * Returns a list of requests which are joined in a specified parent request.
     *
     * @param id parent request id
     * @return list of requests which are joined in a parent request
     */
    List<Request> getJoinedGroupRequests(Long id);

    /**
     * Returns a list of sub requests for the request of the input id.
     *
     * @param id of the request that collect sub requests
     * @return list of sub requests
     */
    List<Request> getSubRequests(Long id);

    /**
     * Fetches from the database all Requests objects with same group.
     *
     * @param parent request's property which represents belonging to a group, must not be {@literal null}.
     * @return List of requests with same progress status.
     */
    default List<Request> getRequestsByParent(Request parent) {
        Long id = parent.getId();
        Assert.notNull(id);
        return getRequestsByParent(id);
    }

    /**
     * Fetches from the database all Requests objects with same group.
     *
     * @param parentId request's property which represents belonging to a group, must not be {@literal null}.
     * @return List of requests with same progress status.
     */
    List<Request> getRequestsByParent(Long parentId);

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
     * Fetches from the database all Requests objects with same progress status.
     *
     * @param progressStatus request's status which represents completion progress, must not be {@literal null}.
     * @return List of requests with same progress status.
     */
    List<Request> getRequestsByStatus(ProgressStatus progressStatus);

    /**
     * Fetches from the database all Requests objects with same priority status.
     *
     * @param priorityStatus request's property which represents belonging to a group, must not be {@literal null}.
     * @return List of requests with same priorityStatus status.
     */
    List<Request> getRequestsByPriority(PriorityStatus priorityStatus);

    /**
     * Changes the progress status of the request, notifies the request reporter and save change in the history.
     *
     * @param requestId changed request id.
     * @return changed request
     */
    Request changeProgressStatus(Long requestId);

    /**
     * Changes the priority status of the request, notifies the request reporter and save change in the history.
     *
     * @param requestId changed request request id.
     * @return changed request
     */
    Request changePriorityStatus(Long requestId);
}