package com.overseer.controller;

import com.overseer.model.PriorityStatus;
import com.overseer.service.PriorityStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller provides api for priority status operations.
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PriorityStatusController {

    private final PriorityStatusService priorityStatusService;

    /**
     * Returns all {@link PriorityStatus} entities.
     *
     * @return all {@link PriorityStatus} entities.
     */
    @GetMapping("/priorityStatuses")
    public ResponseEntity<List<PriorityStatus>> getPriorityStatuses() {
        List<PriorityStatus> roles = priorityStatusService.findAllPriorityStatuses();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }
}