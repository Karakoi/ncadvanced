package com.overseer.service;

import com.overseer.dao.HistoryDAO;
import com.overseer.model.History;

import java.util.List;

/**
 * The <code>HistoryService</code> interface represents access to {@link HistoryDAO}.
 */
public interface HistoryService {

    /**
     * Method returns a list of {@link History} entities which have an information about changes of transferred entity.
     *
     * @param entityId id of entity for which we need to get all history of changes, must not be {@literal null}.
     * @return list of {@link History} entities.
     */
    List<History> findHistory(Long entityId);
}
