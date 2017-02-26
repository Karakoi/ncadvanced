package com.overseer.dao;

import com.overseer.model.ProgressStatus;

/**
 * The <code>ProgressStatusDao</code> interface represents access to {@link ProgressStatus} object in database.
 */
public interface ProgressStatusDao extends SimpleEntityDao<ProgressStatus>, CrudDao<ProgressStatus, Long> {
}
