package com.overseer.dao;

import com.overseer.model.History;

import java.util.List;

/**
 *  The <code>HistoryDAO</code> interface represents access to {@link History} object in database.
 */
public interface HistoryDAO {

    /**
     * Returns all {@link History} objects by entity id.
     *
     * @param entityId entity id for which history is retrieving.
     * @return list of all {@link History} objects.
     */
    List<History> findAllForEntity(Long entityId);

    /**
     * Return {@link History} object by entity id.
     * @param entityId entity id for which history is retrieving.
     * @return {@link History} object.
     */
    History findEntity(Long entityId);
}