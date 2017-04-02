package com.overseer.service;

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
    List<History> findHistory(Long entityId);

    /**
     * Method create message, using history data.
     * @param history history wich we use for getting message.
     * @param useTrimLongText boolean variable. {@code true} if we need to trim long String values.
     * @param maxNumberOfCharsInText max number of chars in long String values, if we need to use trimming of text.
     * @return message with changing data
     */
    String createMessageFromChanges(History history, boolean useTrimLongText, int maxNumberOfCharsInText);
}
