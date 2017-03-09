package com.overseer.controller;

import com.overseer.model.ProgressStatus;
import com.overseer.service.ProgressStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller provides api for progress status operations.
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProgressStatusController {

    private final ProgressStatusService progressStatusService;

    /**
     * Returns all {@link ProgressStatus} entities.
     *
     * @return all {@link ProgressStatus} entities.
     */
    @GetMapping("/progressStatuses")
    public ResponseEntity<List<ProgressStatus>> getAllRoles() {
        List<ProgressStatus> roles = progressStatusService.findAllProgressStatuses();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }
}