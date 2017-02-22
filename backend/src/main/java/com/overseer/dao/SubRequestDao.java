package com.overseer.dao;

import com.overseer.model.Request;
import com.overseer.model.SubRequest;
import org.springframework.util.Assert;

import java.util.List;

/**
 * The <code>SubRequestDao</code> interface represents access to SubRequest {@link SubRequest} object in database.
 */

public interface SubRequestDao extends CrudDao<SubRequest, Long> {
    /**
     * Fetch all request from DB which belongs to the same request.
     *
     * @param request general request which have some requests;
     * @return List of request, that belongs to the same request
     */
    default List<SubRequest> findByRequest(Request request) {
        Long id = request.getId();
        Assert.notNull(id);
        return findByRequest(id);
    }

    /**
     * Fetch all request from DB which belongs to the same request.
     *
     * @param requestId general request's id which have some requests;
     * @return List of request, that belongs to the same request
     */
    List<SubRequest> findByRequest(Long requestId);
}
