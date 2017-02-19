package com.overseer.dao;

import com.overseer.model.PriorityStatus;

import java.util.List;

/**
 * Created by Romanova on 18.02.2017.
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
