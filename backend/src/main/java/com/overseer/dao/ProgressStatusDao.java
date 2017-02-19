package com.overseer.dao;

import com.overseer.model.ProgressStatus;

import java.util.List;

/**
 * Created by Romanova on 18.02.2017.
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
