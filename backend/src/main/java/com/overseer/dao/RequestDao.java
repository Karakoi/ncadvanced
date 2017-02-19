package com.overseer.dao;

import com.overseer.model.Request;

/**
 * Created by Romanova on 18.02.2017.
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
