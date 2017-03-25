package com.overseer.controller;

import com.overseer.model.ProgressStatusView;
import com.overseer.service.impl.ProgressStatusUtil;
import lombok.RequiredArgsConstructor;
import lombok.val;
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
@RequestMapping("/api/progressStatuses")
@RequiredArgsConstructor
public class ProgressStatusController {

    private final ProgressStatusUtil progressStatusUtil;

    /**
     * Returns all {@link ProgressStatusView} entities.
     *
     * @return all {@link ProgressStatusView} entities.
     */
    @GetMapping
    public ResponseEntity<List<ProgressStatusView>> getAllProgressStatusViews() {
        val progressStatuses = progressStatusUtil.getAllProgressStatusViews();
        return new ResponseEntity<>(progressStatuses, HttpStatus.OK);
    }
}