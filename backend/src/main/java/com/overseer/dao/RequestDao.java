package com.overseer.dao;

import com.overseer.model.Request;

/**
 * The <code>RequestDao</code> interface represents access to Request {@link Request} object in database.
 */
public interface RequestDao {
    /**
     * Create Request object in database
     * @param request the request to be inserted
     * @return      inserted request
     * @see         Request
     */
    Request create(Request request);

    /**
     * Update Request object in database
     * @param request the request to be updated
     * @see         Request
     */
    void update(Request request);

    /**
     * Delete Request object in database
     * @param request the request to be deleted
     * @see         Request
     */
    void delete(Request request);

}
