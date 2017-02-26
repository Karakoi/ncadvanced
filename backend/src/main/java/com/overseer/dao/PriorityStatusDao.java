package com.overseer.dao;

import com.overseer.model.PriorityStatus;

/**
 *  The <code>PriorityStatusDao</code> interface represents access to {@link PriorityStatus} object in database.
 */
public interface PriorityStatusDao extends SimpleEntityDao<PriorityStatus>, CrudDao<PriorityStatus, Long> {
}
