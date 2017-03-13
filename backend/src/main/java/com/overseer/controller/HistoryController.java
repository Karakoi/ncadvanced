package com.overseer.controller;

import com.overseer.model.History;
import com.overseer.service.HistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller provides api for getting history.
 */
@RestController
@RequestMapping("/api/histories")
@RequiredArgsConstructor
public class HistoryController {

    private final HistoryService historyService;

    /**
     * Method returns a list of {@link History} entities which have an information about changes of transferred entity.
     *
     * @param entityId id of entity for which we need to get all history of changes, must not be {@literal null}.
     * @return list of {@link History} entities.
     */
    @GetMapping
    public ResponseEntity<List<History>> getHistories(@RequestParam Long entityId) {
        List<History> users = historyService.findHistory(entityId);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
