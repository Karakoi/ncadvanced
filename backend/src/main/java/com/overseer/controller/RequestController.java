package com.overseer.controller;

import com.overseer.model.PriorityStatus;
import com.overseer.model.ProgressStatus;
import com.overseer.model.Request;
import com.overseer.service.RequestService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Controller provides api for creating, getting and deleting request.
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RequestController {
    private static final Logger LOG = LoggerFactory.getLogger(RequestController.class);
    private static final Long DEFAULT_PAGE_SIZE = 20L;

    private final RequestService requestService;

    /**
     * Gets {@link Request} entity associated with provided id param.
     *
     * @param page identifier.
     * @return {@link Request} entity with http status 200 OK.
     */
    @GetMapping("/request/fetch")
    public List<Request> fetchRequestPage(@RequestParam int page) {
        System.out.println(page);
        return requestService.fetchPage(page);
    }

    /**
     * Gets {@link Request} entity associated with provided id param.
     *
     * @param id request identifier.
     * @return {@link Request} entity with http status 200 OK.
     */
    @GetMapping("/request/{id}")
    public ResponseEntity<Request> getRequest(@PathVariable Long id) {
        Request request = requestService.findOne(id);
        LOG.debug("Getting request with id: {}", id);
        return new ResponseEntity<>(request, HttpStatus.OK);
    }

    /**
     * Creates {@link Request} entity.
     *
     * @param request json object which represents {@link Request} entity.
     * @return json representation of created {@link Request} entity.
     */
    @PostMapping("/request/create")
    public ResponseEntity<Request> createRequest(@RequestBody Request request) {
        requestService.create(request);
        LOG.debug("Request has been added with id: {}", request.getId());
        return new ResponseEntity<>(request, HttpStatus.CREATED);
    }

    /**
     * Deletes {@link Request} entity associated with provided id param.
     *
     * @param id request identifier.
     * @return http status 204 NO_CONTENT.
     */
    @DeleteMapping("/request/delete/{id}")
    public ResponseEntity deleteRequest(@PathVariable Long id) {
        requestService.delete(id);
        LOG.debug("Request has been deleted with id: {}", id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * Updates {@link Request} entity.
     *
     * @param request json object which represents {@link Request} entity.
     * @return json representation of created {@link Request} entity.
     */
    @PutMapping("/request/update/")
    public ResponseEntity updateRequest(@RequestBody Request request) {
        requestService.update(request);
        LOG.debug("Request has been updated with id: {}", request.getId());
        return new ResponseEntity<>(request, HttpStatus.OK);
    }

    /**
     * Gets a list of requests which are joined in a specified parent request.
     *
     * @param id parent request id
     * @return list of requests which are joined in a parent request
     */
    @GetMapping("/request/getJoinedGroupRequests/{id}")
    public ResponseEntity<List<Request>> getJoinedGroupRequests(@PathVariable Long id) {
        Request parent = requestService.findOne(id);
        List<Request> requests = requestService.findJoinedRequests(parent);
        LOG.debug("Gets a list of requests which are joined in a specified parent request with id: {}", id);
        return new ResponseEntity<>(requests, HttpStatus.OK);
    }

    /**
     * Gets a list of sub requests for the request associated with provided id param.
     *
     * @param id of the request that collect sub requests
     * @return list of sub requests
     */
    @GetMapping("/request/getSubRequests/{id}")
    public ResponseEntity<List<Request>> getSubRequests(@PathVariable Long id) {
        Request parent = requestService.findOne(id);
        List<Request> requests = requestService.findSubRequests(parent);
        LOG.debug("Gets a list of sub requests for the request with id: {}", id);
        return new ResponseEntity<>(requests, HttpStatus.OK);
    }

    /**
     * Gets all Requests objects assigned to the same user.
     *
     * @param id represents belonging request to a user, must not be {@literal null}.
     * @return List of requests with same user.
     */
    @GetMapping("/request/getRequestsByAssignee/{id}")
    public ResponseEntity<List<Request>> getRequestsByAssignee(@PathVariable Long id) {
//        List<Request> requests = requestService.getRequestsByAssignee(id);
        LOG.debug("Gets all Requests objects assigned to the user with id: {}", id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Gets all Requests objects created with the same user.
     *
     * @param id represents belonging request to a user, must not be {@literal null}.
     * @return List of requests with same user.
     */
    @GetMapping("/request/getRequestsByReporter/{id}")
    public ResponseEntity<List<Request>> getRequestsByReporter(@PathVariable Long id) {
//        List<Request> requests = requestService.getRequestsByReporter(id);
        LOG.debug("Gets all Requests objects created with the user with id: {}", id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Gets all Requests objects with same progress status.
     *
     * @param progressStatus request's status which represents completion progress, must not be {@literal null}.
     * @return List of requests with same progress status.
     */
    @GetMapping("/request/getRequestsByStatus/{id}")
    public ResponseEntity<List<Request>> getRequestsByStatus(@PathVariable ProgressStatus progressStatus) {
//        List<Request> requests = requestService.getRequestsByStatus(progressStatus);
        LOG.debug("Gets all Requests objects with progress status: {}", progressStatus);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Gets all Requests objects with same priority status.
     *
     * @param priorityStatus request's property which represents belonging to a group, must not be {@literal null}.
     * @return List of requests with same priorityStatus status.
     */
    @GetMapping("/request/getRequestsByPriority/{id}")
    public ResponseEntity<List<Request>> getRequestsByPriority(@PathVariable PriorityStatus priorityStatus) {
//        List<Request> requests = requestService.getRequestsByPriority(priorityStatus);
        LOG.debug("Gets all Requests objects with same priority status: {}", priorityStatus);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    /**
     * Gets all Requests objects which created in the same period.
     *
     * @param beginDate date from
     * @param endDate   date to
     * @return rerun list of requests from one period of time
     */
    @GetMapping("/request/getRequestsByPeriod/begin/{beginDate}/end/{endDate}")
    public ResponseEntity<List<Request>> getRequestsByPeriod(@PathVariable LocalDate beginDate, LocalDate endDate,
                                                             int pageNumber) {
        List<Request> requests = requestService.findRequestsByPeriod(beginDate, endDate, pageNumber);
        LOG.debug("Gets all Requests objects which created in period: {} - {}", beginDate, endDate);
        return new ResponseEntity<>(requests, HttpStatus.OK);
    }

    /**
     * Gets all Requests objects created in the same day.
     *
     * @param date request's property which represents belonging to a group, must not be {@literal null}.
     * @return List of requests with same date.
     */
    @GetMapping("/request/getRequestByDate/{date}")
    public ResponseEntity<List<Request>> getRequestByDate(@PathVariable LocalDate date) {
        List<Request> requests = requestService.findRequestsByDate(date);
        LOG.debug("Gets all Requests objects created in day: {}", date);
        return new ResponseEntity<>(requests, HttpStatus.OK);
    }

    /**
     * Changes the progress status of the request.
     *
     * @param request request to update by.
     * @return changed request
     */
    @PutMapping("/request/changeProgressStatus")
    public ResponseEntity changeProgressStatus(@RequestBody Request request) {
//        requestService.changeProgressStatus(request);
        LOG.debug("ProgressStatus of Request has been changed and Request has been updated with id: {}", request.getId());
        return new ResponseEntity<>(request, HttpStatus.OK);
    }

    /**
     * Changes the priority status of the request.
     *
     * @param request request to update by.
     * @return changed request
     */
    @PutMapping("/request/changePriorityStatus")
    public ResponseEntity changePriorityStatus(@RequestBody Request request) {
//        requestService.changePriorityStatus(request);
        LOG.debug("PriorityStatus of Request has been changed and Request has been updated with id: {}", request.getId());
        return new ResponseEntity<>(request, HttpStatus.OK);
    }

    @GetMapping("/request/pageCount")
    public ResponseEntity<Long> getPagesCount() {
        Long pageCount = requestService.getCount() / DEFAULT_PAGE_SIZE + 1;
        return new ResponseEntity<Long>(pageCount, HttpStatus.OK);
    }
}
