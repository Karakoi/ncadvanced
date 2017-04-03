package com.overseer.service;

import com.overseer.dto.HistoryMessageDTO;
import com.overseer.model.History;

import java.util.List;

/**
 * The <code>HistoryService</code> interface represents access to {@link com.overseer.dao.HistoryDAO}.
 */
public interface HistoryService {

    /**
     * Method returns a list of {@link History} entities which have an information about changes of transferred entity.
     *
     * @param entityId id of entity for which we need to get all history of changes, must not be {@literal null}.
     * @return list of {@link History} entities.
     */
    List<History> findHistoryList(Long entityId);

    /**
     * Method returns a list of {@link HistoryMessageDTO} entities which have an information about changes of transferred entity.
     *
     * @param entityId id of entity for which we need to get all history of changes, must not be {@literal null}.
     * @param maxNumberOfCharsInText max number of chars in long String values, if we need to use trimming of text.
     * @return list of {@link History} entities.
     */
    List<HistoryMessageDTO> getHistoryMessageDTOs(Long entityId, int maxNumberOfCharsInText);

    /**
     * Method create {@link HistoryMessageDTO} object and fill its messages.
     * @param history history which we use for getting messages.
     * @param maxNumberOfCharsInText max number of chars in long String values, if we need to use trimming of text.
     * @return message with changing data
     */
    HistoryMessageDTO createHistoryDtoWithMessages(History history, int maxNumberOfCharsInText);

    /**
     * Method convert {@link HistoryMessageDTO} object to {@link History} object.
     * @param history {@link History} object that will be converted to {@link HistoryMessageDTO} object.
     * @param maxNumberOfCharsInText max number of chars in long string values, if we need to trim its.
     * @return {@link HistoryMessageDTO} object, created from {@link History} object.
     */
    HistoryMessageDTO convertHistoryInHistoryMessageDTO(History history, int maxNumberOfCharsInText);

    /**
     * Method convert {@link List} of {@link HistoryMessageDTO} object to {@link List} of {@link History} object.
     * @param histories {@link List} of {@link History} objects that will be converted to {@link List} of {@link HistoryMessageDTO} objects.

     * @param maxNumberOfCharsInText max number of chars in long string values, if we need to trim its.
     * @return {@link List} of {@link HistoryMessageDTO} objects, created from {@link List} of {@link History} objects.
     */
    List<HistoryMessageDTO> convertHistoryInHistoryMessageDTO(List<History> histories, int maxNumberOfCharsInText);
}
