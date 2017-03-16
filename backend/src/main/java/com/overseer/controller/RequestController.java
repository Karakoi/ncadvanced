package com.overseer.controller;

import com.overseer.model.Request;
import com.overseer.service.RequestService;
import lombok.RequiredArgsConstructor;
import lombok.val;
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
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller provides api for creating, getting and deleting request.
 */
@RestController
@RequestMapping("/api/requests")
@RequiredArgsConstructor
public class RequestController {
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
        val request = requestService.findOne(id);
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
        val createdRequest = requestService.create(request);
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
        val updatedRequest = requestService.update(request);
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
        val requests = requestService.fetchPage(page);
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
        val parent = requestService.findOne(id);
        val requests = requestService.findJoinedRequests(parent);
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
        val parent = requestService.findOne(id);
        val requests = requestService.findSubRequests(parent);
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
        val requests = requestService.findRequestsByPeriod(beginDate, endDate, pageNumber);
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
        val requests = requestService.findRequestsByDate(date);
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

    /**
     * Returns number of pages.
     *
     * @return number of pages.
     */
    @GetMapping("/pageCountFree")
    public ResponseEntity<Long> getPagesCountFree() {
        Long pageCount = requestService.countFreeRequests() / DEFAULT_PAGE_SIZE + 1;
        return new ResponseEntity<>(pageCount, HttpStatus.OK);
    }

    /**
     * Gets {@link Request} list which corresponds to provided page.
     *
     * @param page identifier.
     * @return {@link Request} list with http status 200 OK..
     */
    @GetMapping("/fetchFree")
    public ResponseEntity<List<Request>> fetchFreeRequestPage(@RequestParam int page) {
        val requests = requestService.findFreeRequests(page);
        return new ResponseEntity<>(requests, HttpStatus.OK);
    }

    /**
     * Creates {@link Request} entity of parent request and joins some another
     * requests to it.
     * @param request json object which represents {@link Request} entity.
     * @param ids string representation if id`s array.
     * @return json representation of created {@link Request} entity.
     */
    @PostMapping("/join/{ids}")
    public ResponseEntity<Request> joinRequests(@RequestBody Request request, @PathVariable String ids) {
        List<Long> list = Arrays.asList(ids.split(",")).stream().map(Long::parseLong).collect(Collectors.toList());
        val createdRequest = requestService.joinRequestsIntoParent(list, request);
        return new ResponseEntity<>(createdRequest, HttpStatus.CREATED);
    }

}
