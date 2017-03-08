package com.overseer.controller;

import com.overseer.model.Request;
import com.overseer.service.RequestService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

/**
 * Controller provides api for creating, getting and deleting request.
 */
@RestController
@RequestMapping("/api/requests")
@RequiredArgsConstructor
public class RequestController {
    private static final Logger LOG = LoggerFactory.getLogger(RequestController.class);
    private static final Long DEFAULT_PAGE_SIZE = 20L;

    private final RequestService requestService;

    /**
     * Gets {@link Request} entity associated with provided id param.
     *
     * @param id request identifier.
     * @return {@link Request} entity with http status 200 OK.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Request> fetchRequest(@PathVariable Long id) {
        Request request = requestService.findOne(id);
        LOG.debug("Fetching request with id: {}", id);
        return new ResponseEntity<>(request, HttpStatus.OK);
    }

    /**
     * Creates {@link Request} entity.
     *
     * @param request json object which represents {@link Request} entity.
     * @return json representation of created {@link Request} entity.
     */
    @PostMapping
    public ResponseEntity<Request> createRequest(@RequestBody Request request) {
        Request createdRequest = requestService.create(request);
        LOG.debug("Saved request with id: {}", createdRequest.getId());
        return new ResponseEntity<>(createdRequest, HttpStatus.CREATED);
    }

    /**
     * Deletes {@link Request} entity associated with provided id param.
     *
     * @param id request identifier.
     * @return http status 204 NO_CONTENT.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity deleteRequest(@PathVariable Long id) {
        requestService.delete(id);
        LOG.debug("Removed request with id: {}", id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * Updates {@link Request} entity.
     *
     * @param request json object which represents {@link Request} entity.
     * @return json representation of created {@link Request} entity.
     */
    @PutMapping
    public ResponseEntity updateRequest(@RequestBody Request request) {
        Request updatedRequest = requestService.update(request);
        LOG.debug("Updated request with id: {}", updatedRequest.getId());
        return new ResponseEntity<>(updatedRequest, HttpStatus.OK);
    }

    /**
     * Gets {@link Request} list which corresponds to provided page.
     *
     * @param page identifier.
     * @return {@link Request} list with http status 200 OK..
     */
    @GetMapping("/fetch")
    public ResponseEntity<List<Request>> fetchRequestPage(@RequestParam int page) {
        List<Request> requests = requestService.fetchPage(page);
        LOG.debug("Fetched {} requests for page: {}", requests.size(), page);
        return new ResponseEntity<>(requests, HttpStatus.OK);
    }

    /**
     * Gets a list of requests which are joined in a specified parent request.
     *
     * @param id parent request id
     * @return list of requests which are joined in a parent request
     */
    @GetMapping("/getJoinedGroupRequests/{id}")
    public ResponseEntity<List<Request>> getJoinedGroupRequests(@PathVariable Long id) {
        Request parent = requestService.findOne(id);
        List<Request> requests = requestService.findJoinedRequests(parent);
        LOG.debug("Fetched {} requests for parent request with id: {}", requests.size(), id);
        return new ResponseEntity<>(requests, HttpStatus.OK);
    }

    /**
     * Gets a list of sub requests for the request associated with provided id param.
     *
     * @param id of the request that collect sub requests
     * @return list of sub requests
     */
    @GetMapping("/getSubRequests/{id}")
    public ResponseEntity<List<Request>> getSubRequests(@PathVariable Long id) {
        Request parent = requestService.findOne(id);
        List<Request> requests = requestService.findSubRequests(parent);
        LOG.debug("Fetched {} subRequests for request with id: {}", requests.size(), id);
        return new ResponseEntity<>(requests, HttpStatus.OK);
    }

    /**
     * Gets all Requests objects which created in the same period.
     *
     * @param beginDate date from
     * @param endDate   date to
     * @return rerun list of requests from one period of time
     */
    @GetMapping("/getRequestsByPeriod/{beginDate}/{endDate}")
    public ResponseEntity<List<Request>> getRequestsByPeriod(@PathVariable LocalDate beginDate,
                                                             @PathVariable LocalDate endDate,
                                                             int pageNumber) {
        List<Request> requests = requestService.findRequestsByPeriod(beginDate, endDate, pageNumber);
        LOG.debug("Fetched {} requests created in period: {} - {}", requests.size(), beginDate, endDate);
        return new ResponseEntity<>(requests, HttpStatus.OK);
    }

    /**
     * Gets all Requests objects created in the same day.
     *
     * @param date request's property which represents belonging to a group, must not be {@literal null}.
     * @return List of requests with same date.
     */
    @GetMapping("/getRequestByDate/{date}")
    public ResponseEntity<List<Request>> getRequestByDate(@PathVariable LocalDate date) {
        List<Request> requests = requestService.findRequestsByDate(date);
        LOG.debug("Fetched {} requests created on: {}", requests.size(), date);
        return new ResponseEntity<>(requests, HttpStatus.OK);
    }

    /**
     * Returns number of pages.
     *
     * @return number of pages.
     */
    @GetMapping("/pageCount")
    public ResponseEntity<Long> getPagesCount() {
        Long pageCount = requestService.getCount() / DEFAULT_PAGE_SIZE + 1;
        return new ResponseEntity<>(pageCount, HttpStatus.OK);
    }
}
