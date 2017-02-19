package com.overseer.dao;

import com.overseer.model.PriorityStatus;

import java.util.List;

/**
 * The <code>PriorityStatusDao</code> interface represents access to PriorityStatus {@link PriorityStatus} object in database.
 */
public interface PriorityStatusDao {
    /**
     * Pulls from the database all PriorityStatus objects
     * @return      List of all PriorityStatus objects
     * @see         PriorityStatus
     */
    List<PriorityStatus> findAllPriorityStatuses();

    /**
     * Fetches from the database PriorityStatus object with specified id
     * @param priorityStatusId the id of PriorityStatus to be fetched
     * @return      PriorityStatus object with specified id
     * @see         PriorityStatus
     */
    PriorityStatus getPriorityStatusById(Long priorityStatusId);
}
