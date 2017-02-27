package com.overseer.controller;

import com.overseer.model.Request;
import com.overseer.service.RequestService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

/**
 * Controller provides api for creating, getting and deleting request.
 */
@RestController
@RequiredArgsConstructor
public class RequestController {
    private static final Logger LOG = LoggerFactory.getLogger(RequestController.class);

    private final RequestService requestService;

    /**
     * Gets {@link Request} entity associated with provided id param.
     *
     * @param id request identifier.
     * @return {@link Request} entity with http status 200 OK.
     */
    @GetMapping("/request/{id}")
    public ResponseEntity<Request> getRequest(@PathVariable("id") Long id) {
        Assert.notNull(id, "Request Id is null");
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
        Assert.notNull(request, "Create request error request is null");
        Assert.notNull(request.getTitle(), "Request has no title");
        Assert.notNull(request.getReporter(), "Request has no reporter");
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
    @DeleteMapping("/request/{id}")
    public ResponseEntity deleteRequest(@PathVariable("id") Long id) {
        Assert.notNull(id, "Request Id is null");
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
    @PutMapping("/customers/{id}")
    public ResponseEntity updateRequest(@RequestBody Request request) {
        Assert.notNull(request, "Update request error request is null");
        Assert.notNull(request.getId(), "Request has no ID");
        Assert.notNull(request.getTitle(), "Request has no title");
        Assert.notNull(request.getReporter(), "Request has no reporter");
        requestService.update(request);
        LOG.debug("Request has been updated with id: {}", request.getId());
        return new ResponseEntity<>(request, HttpStatus.OK);
    }

}
