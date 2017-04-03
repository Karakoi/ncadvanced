package com.overseer.controller;

import com.overseer.dto.HistoryMessageDTO;
import com.overseer.model.History;
import com.overseer.service.HistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/{entityId}")
    public ResponseEntity<List<History>> getHistories(@PathVariable Long entityId) {
        List<History> histories = historyService.findHistoryList(entityId);
        return new ResponseEntity<>(histories, HttpStatus.OK);
    }

    /**
     * Method returns a list of {@link HistoryMessageDTO} entities which have an information about changes of transferred entity.
     *
     * @param entityId id of entity for which we need to get all history of changes, must not be {@literal null}.
     * @return list of {@link HistoryMessageDTO} entities.
     */
    @GetMapping("/dto/{entityId}")
    public ResponseEntity<List<HistoryMessageDTO>> getHistorieDTOs(@PathVariable Long entityId) {
        boolean useTrimLongText = true;
        final int maxNumberOfCharsInText = 20;
        List<HistoryMessageDTO> histories = historyService.findHistoryMessageDTO(entityId, useTrimLongText, maxNumberOfCharsInText);
        return new ResponseEntity<>(histories, HttpStatus.OK);
    }

    /**
     * Method return a {@link HistoryMessageDTO} object with long data value by its id.
     *
     * @param entityId id of {@link HistoryMessageDTO} object, for witch we get long data value.
     * @return {@link HistoryMessageDTO} object with long data value.
     */
    @GetMapping("/dto/longValue/{entityId}")
    public ResponseEntity<HistoryMessageDTO> getLongHistorieDTOs(@PathVariable Long entityId) {
        boolean useTrimLongText = false;
        final int maxNumberOfCharsInText = -1;
        HistoryMessageDTO histories = historyService.getLongHistoryMessageDTO(entityId, useTrimLongText, maxNumberOfCharsInText);
        return new ResponseEntity<>(histories, HttpStatus.OK);
    }
}