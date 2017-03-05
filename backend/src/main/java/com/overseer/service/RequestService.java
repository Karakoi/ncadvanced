package com.overseer.service;

import com.overseer.model.PriorityStatus;
import com.overseer.model.ProgressStatus;
import com.overseer.model.Request;
import com.overseer.model.User;

import java.time.LocalDate;
import java.util.List;

/**
 * The <code>RequestService</code> interface represents access to RequestDao.
 */
public interface RequestService extends CrudService<Request, Long> {

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
     * @param assignee requests assignee, must not be {@literal null}.
     * @return list of requests which have provided {@link User} as assignee.
     */
    List<Request> findRequestsByAssignee(User assignee, int pageNumber);

    /**
     * Returns a list of requests which have provided {@link User} as reporter.
     *
     * @param reporter requests reporter, must not be {@literal null}.
     * @return list of requests which have provided {@link User} as reporter.
     */
    List<Request> findRequestsByReporter(User reporter, int pageNumber);

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
     * Returns a list of requests created in provided date.
     *
     * @param date creation date, must not be {@literal null}.
     * @return list of requests created in provided date.
     */
    List<Request> findRequestsByDate(LocalDate date);
}