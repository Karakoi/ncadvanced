package com.overseer.service;

import com.overseer.model.PriorityStatus;

import java.util.List;

/**
 * The <code>PriorityStatusService</code> interface represents access to PriorityStatusDao.
 */
public interface PriorityStatusService {

    /**
     * Returns all priority statuses values.
     *
     * @return list of priority statuses
     */
    List<PriorityStatus> findAllPriorityStatuses();
}