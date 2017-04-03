package com.overseer.dao;


import com.overseer.model.Comment;

import java.util.List;

/**
 * The <code>CommentDao</code> interface represents access to {@link CommentDao} object in database.
 */
public interface CommentDao extends CrudDao<Comment, Long> {

    /**
     * Fetch all messages for specified request.
     *
     * @param requestId specified request id
     * @return fetched request messages
     */
    List<Comment> findByRequest(Long requestId);

    void deleteAllByRequest(Long requestId);
}
