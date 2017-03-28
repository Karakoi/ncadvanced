package com.overseer.controller;

import com.overseer.dto.DeadlineDTO;
import com.overseer.dto.RequestSearchDTO;
import com.overseer.model.Request;
import com.overseer.service.RequestService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
    //private static final Long DEFAULT_PAGE_SIZE = 20L;
    private final RequestService requestService;

    /**
     * Gets {@link Request} entity associated with provided id param.
     *
     * @param id request identifier.
     * @return {@link Request} entity with http status 200 OK.
     */
    @PostAuthorize("hasAnyRole('ADMIN', 'MANAGER') || returnObject.getBody().reporter.email == authentication.name")
    @GetMapping("/{id}")
    public ResponseEntity<Request> fetchRequest(@PathVariable Long id) {
        val request = requestService.findOne(id);
        return new ResponseEntity<>(request, HttpStatus.OK);
    }

    /**
     * Creates sub request of {@link Request} entity.
     *
     * @param subRequest json object which represents {@link Request} entity.
     * @return json representation of created {@link Request} entity.
     */
    @PostMapping("/createSubRequest")
    public ResponseEntity<Request> createSubRequest(@RequestBody Request subRequest) {
        val createdRequest = requestService.saveSubRequest(subRequest);
        return new ResponseEntity<>(createdRequest, HttpStatus.CREATED);
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
     * @return json representation of updated {@link Request} entity.
     */
    @PutMapping
    public ResponseEntity updateRequest(@RequestBody Request request) {
        val updatedRequest = requestService.update(request);
        return new ResponseEntity<>(updatedRequest, HttpStatus.OK);
    }

    /**
     * Assignes {@link Request} entity.
     *
     * @param request json object which represents {@link Request} entity.
     * @return json representation of assigned {@link Request} entity.
     */
    @PutMapping("/assignRequest")
    public ResponseEntity assignRequest(@RequestBody Request request) {
        val assignedRequest = requestService.assignRequest(request);
        return new ResponseEntity<>(assignedRequest, HttpStatus.OK);
    }

    /**
     * Closes {@link Request} entity.
     *
     * @param request json object which represents {@link Request} entity.
     * @return json representation of closed {@link Request} entity.
     */
    @PutMapping("/closeRequest")
    public ResponseEntity closeRequest(@RequestBody Request request) {
        val closedRequest = requestService.closeRequest(request);
        return new ResponseEntity<>(closedRequest, HttpStatus.OK);
    }

    /**
     * Reopens {@link Request} entity.
     *
     * @param request json object which represents {@link Request} entity.
     * @return json representation of reopend {@link Request} entity.
     */
    @PutMapping("/reopenRequest")
    public ResponseEntity reopenRequest(@RequestBody Request request) {
        val reopenedRequest = requestService.reopenRequest(request.getId());
        return new ResponseEntity<>(reopenedRequest, HttpStatus.OK);
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
     * Returns list of filtered requests by specified search params in {@link RequestSearchDTO} object.
     *
     * @param searchDTO search params dto object
     * @return {@link Request} list with http status 200 OK..
     */
    @GetMapping("/search")
    public ResponseEntity<List<Request>> searchRequests(RequestSearchDTO searchDTO) {
        List<Request> requests = requestService.searchRequests(searchDTO);
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
        return new ResponseEntity<>(requestService.getCount(), HttpStatus.OK);
    }

    /**
     * Returns number of pages.
     *
     * @return number of pages.
     */
    @GetMapping("/pageCountFree")
    public ResponseEntity<Long> getPagesCountFree() {
        return new ResponseEntity<>(requestService.countFreeRequests(), HttpStatus.OK);
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
     *
     * @param request json object which represents {@link Request} entity.
     * @param ids     string representation if id`s array.
     * @return json representation of created {@link Request} entity.
     */
    @PostMapping("/join/{ids}")
    public ResponseEntity<Request> joinRequests(@RequestBody Request request, @PathVariable String ids) {
        List<Long> list = Arrays.asList(ids.split(",")).stream().map(Long::parseLong).collect(Collectors.toList());
        val createdRequest = requestService.joinRequestsIntoParent(list, request);
        return new ResponseEntity<>(createdRequest, HttpStatus.CREATED);
    }


    @GetMapping("/requestsByReporter")
    public ResponseEntity<List<Request>> getRequestsByReporter(@RequestParam long userId, int pageNumber) {
        val requests = requestService.findRequestsByReporter(userId, pageNumber);
        return new ResponseEntity<>(requests, HttpStatus.OK);
    }

    @GetMapping("/countInProgressRequestsByAssignee")
    public ResponseEntity<Long> countInProgressRequestByAssignee(@RequestParam long manager) {
        return new ResponseEntity<>(requestService.countInProgressRequestByAssignee(manager), HttpStatus.OK);
    }

    @GetMapping("/inProgressRequestsByAssignee")
    public ResponseEntity<List<Request>> getInProgressRequestsByAssignee(@RequestParam int page,
                                                                         @RequestParam long manager) {
        val requests = requestService.findInProgressRequestsByAssignee(manager, page);
        return new ResponseEntity<>(requests, HttpStatus.OK);
    }

    @GetMapping("/countClosedRequestsByAssignee")
    public ResponseEntity<Long> countClosedRequestByAssignee(@RequestParam long manager) {
        return new ResponseEntity<>(requestService.countClosedRequestByAssignee(manager), HttpStatus.OK);
    }

    @GetMapping("/closedRequestsByAssignee")
    public ResponseEntity<List<Request>> getClosedRequestsByAssignee(@RequestParam int page,
                                                                     @RequestParam long manager) {
        val requests = requestService.findClosedRequestsByAssignee(manager, page);
        return new ResponseEntity<>(requests, HttpStatus.OK);
    }

    @GetMapping("/closedRequestsByReporter")
    public ResponseEntity<List<Request>> getClosedRequestsByReporter(@RequestParam long userId, int pageNumber) {
        val requests = requestService.findClosedRequestsByReporter(userId, pageNumber);
        return new ResponseEntity<>(requests, HttpStatus.OK);
    }

    @GetMapping("/countRequestsByReporter")
    public ResponseEntity<Long> countRequestByReporter(@RequestParam Long reporterId) {
        return new ResponseEntity<>(requestService.countRequestByReporter(reporterId), HttpStatus.OK);
    }

    @GetMapping("/countClosedRequestsByReporter")
    public ResponseEntity<Long> countClosedRequestByReporter(@RequestParam Long reporterId) {
        return new ResponseEntity<>(requestService.countClosedRequestsByReporter(reporterId), HttpStatus.OK);
    }

    @PostMapping("/employeeRequest")
    public ResponseEntity<Request> createEmployeeRequest(@RequestBody Request request) {
        return new ResponseEntity<>(requestService.createEmpRequest(request), HttpStatus.OK);
    }

    /**
     * Reopen array of requests.
     *
     * @param requestsId array of request id's.
     * @return reopened requests.
     */
    @PostMapping("/reopen")
    public ResponseEntity<Request> createEmployeeRequest(@RequestBody Long[] requestsId) {
        for (Long g : requestsId) {
            requestService.reopenRequest(g);
        }
        return new ResponseEntity<>(new Request(), HttpStatus.OK);
    }

    /**
     * Returns list of progress for User.
     *
     * @param userId value of User id in database.
     * @return list of progress for User.
     */
    @GetMapping("/getStatisticByProgressStatusForUser")
    public ResponseEntity<List<Long>> getQuantityForUser(@RequestParam Long userId) {
        final List<Long> quantityUser = requestService.quantityByProgressStatusForUser(userId);
        return new ResponseEntity<>(quantityUser, HttpStatus.OK);
    }

    /**
     * Returns list for bar chart in admin page.
     *
     * @param userId value of User id in database.
     * @param howLong means for which period we need statistic in months.
     * @return list of open and closed requests for User.
     */
    @GetMapping("/getOpenClosedRequestForUser")
    public ResponseEntity<List<Long>> getOpenClosedRequestForUser(@RequestParam Long userId,
                                                                  @RequestParam Long howLong) {
        final List<Long> openClosedRequestForUser = requestService.quantityOpenClosedRequestForUser(userId, howLong);
        return new ResponseEntity<>(openClosedRequestForUser, HttpStatus.OK);
    }

    /**
     * Returns number of pages.
     *
     * @return number of pages.
     */
    @GetMapping("/pageCountByAssignee")
    public ResponseEntity<Long> getPagesCountByAssignee(@RequestParam Long assigneeId) {
        return new ResponseEntity<>(requestService.countRequestsByAssignee(assigneeId), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @GetMapping("/fetchByAssignee")
    public ResponseEntity<List<Request>> getRequestsByAssignee(@RequestParam Long assigneeId,
                                                               @RequestParam int pageNumber) {
        val list = this.requestService.findRequestsByAssignee(assigneeId, pageNumber);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * @return value of total Users.
     */
    @GetMapping("getTotalUsers")
    public ResponseEntity<Long> getTotalUsers() {
        Long totalUsers = requestService.countTotalUsers();
        return new ResponseEntity<>(totalUsers, HttpStatus.OK);
    }

    /**
     * @return value of total Requests.
     */
    @GetMapping("getTotalRequests")
    public ResponseEntity<Long> getTotalRequests() {
        Long totalRequests = requestService.countTotalRequests();
        return new ResponseEntity<>(totalRequests, HttpStatus.OK);
    }

    /**
     * @return value of today's requests.
     */
    @GetMapping("getRequestToday")
    public ResponseEntity<Long> getRequestToday() {
        Long requestsCreatedToday = requestService.countRequestsCreatedToday();
        return new ResponseEntity<>(requestsCreatedToday, HttpStatus.OK);
    }

    /**
     * @return value of today's requests that change progress status from free to anyone except to closed and free.
     */
    @GetMapping("getRunningToday")
    public ResponseEntity<Long> getRunningToday() {
        Long runningRequestToday = requestService.getRunningRequestToday();
        return new ResponseEntity<>(runningRequestToday, HttpStatus.OK);
    }

    /**
     * @param howLong set which exactly statistic we need.
     * @return list of statistic for admin dashboard.
     */
    @GetMapping("getStatisticForAdminDashBoard")
    public ResponseEntity<List<Long>> getStatisticForAdminDashBoard(@RequestParam Long howLong) {
        List<Long> totalStatistic = requestService.countStatisticForAdminDashBoard(howLong);
        return new ResponseEntity<>(totalStatistic, HttpStatus.OK);
    }

    @GetMapping("getDeadlines")
    public ResponseEntity<List<DeadlineDTO>> getManagerDeadlines(@RequestParam Long managerID) {
        return new ResponseEntity<>(requestService.getManagerDeadlines(managerID), HttpStatus.OK);
    }
}

