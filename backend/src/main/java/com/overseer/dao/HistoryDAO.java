package com.overseer.dao;

import com.overseer.model.History;

import java.time.LocalDate;
import java.util.List;

/**
 *  The <code>HistoryDAO</code> interface represents access to {@link History} object in database.
 */
public interface HistoryDAO {

    /**
     * Saves a given history.
     *
     * @param history that will be saved.
     * @return the saved history.
     */
    History save(History history);

    /**
     * Returns all history details for specified entity by id.
     *
     * @param entityId entity id for which history is retrieving
     * @return all history details.
     */
    List<History> findAllForEntity(Long entityId);

    /**
     * Returns all history details for all entities by specified date.
     *
     * @param date date filter
     * @return all history details.
     */
    List<History> findAllByDate(LocalDate date);

    /**
     * Returns all history details for all entities by specified date period.
     *
     * @param fromDate date filter
     * @param toDate date filter
     * @return all history details.
     */
    List<History> findAllByPeriod(LocalDate fromDate, LocalDate toDate);
}