package com.overseer.service;

import com.overseer.model.ProgressStatus;

import java.util.List;

/**
 * The <code>ProgressStatusService</code> interface represents access to ProgressStatusDao.
 */
public interface ProgressStatusService {

    /**
     * Returns all progress statuses values.
     *
     * @return list of progress statuses
     */
    List<ProgressStatus> findAllProgressStatuses();
}