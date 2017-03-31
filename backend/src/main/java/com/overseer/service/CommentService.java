package com.overseer.service;

import com.overseer.model.Comment;

import java.util.List;

/**
 * The <code>CommentService</code> interface provide functionality
 * of private messages.
 */
public interface CommentService extends CrudService<Comment, Long> {

    List<Comment> findByRequest(Long requestId);
}
