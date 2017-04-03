package com.overseer.controller;

import com.overseer.model.Comment;
import com.overseer.service.CommentService;
import io.jsonwebtoken.lang.Assert;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller provides api request comments, sending and getting comment.
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/sendComment")
    public ResponseEntity<Comment> sendComment(@RequestBody Comment comment) {
        Assert.notNull(comment.getText(), "Comment has to have text");
        Comment savedComment = commentService.create(comment);
        return new ResponseEntity<>(savedComment, HttpStatus.OK);
    }

    @GetMapping("/commentsByRequest")
    public ResponseEntity<List<Comment>> getCommentsByRequest(@RequestParam Long requestId) {
        val comments = commentService.findByRequest(requestId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @DeleteMapping("comments/{id}")
    public ResponseEntity deleteComment(@PathVariable Long id) {
        commentService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
