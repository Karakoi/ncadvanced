package com.overseer.service.impl;

import com.overseer.dao.CommentDao;
import com.overseer.model.Comment;
import com.overseer.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * Implementation of {@link CommentService} interface.
 */
@Service
@Slf4j
public class CommentServiceImpl extends CrudServiceImpl<Comment> implements CommentService {

    private CommentDao commentDao;

    public CommentServiceImpl(CommentDao commentDao) {
        super(commentDao);
        this.commentDao = commentDao;
    }

    @Override
    public List<Comment> findByRequest(Long requestId) {
        val list = commentDao.findByRequest(requestId);
        log.debug("Fetched comments for request with id: {}", requestId);
        return list;
    }
}
