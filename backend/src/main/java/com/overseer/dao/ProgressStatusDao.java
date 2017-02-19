package com.overseer.dao;

import com.overseer.model.ProgressStatus;

import java.util.List;

/**
 * The <code>ProgressStatusDao</code> interface represents access to ProgressStatus {@link ProgressStatus} object in database.
 */
public interface ProgressStatusDao {
    /**
     * Fetches from the database all ProgressStatus objects
     * @return      List of all ProgressStatus objects
     * @see         ProgressStatus
     */
    List<ProgressStatus> findAllProgressStatuses();

    /**
     * Fetches from the database ProgressStatus object with specified id
     * @param progressStatusId the id of ProgressStatus to be fetched
     * @return      ProgressStatus object with specified id
     * @see         ProgressStatus
     */
    ProgressStatus getProgressStatusById(Long progressStatusId);
}
