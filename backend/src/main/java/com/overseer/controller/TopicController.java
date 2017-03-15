package com.overseer.controller;

import com.overseer.model.Message;
import com.overseer.model.Topic;
import com.overseer.service.TopicService;
import io.jsonwebtoken.lang.Assert;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller provides api for creating, getting and deleting topic.
 */
@RestController
@RequestMapping("/api/topics")
@RequiredArgsConstructor
public class TopicController {

    private final TopicService topicService;

    private static final Long DEFAULT_PAGE_SIZE = 10L;

    /**
     * Gets {@link Topic} entity associated with provided id param.
     *
     * @param id topic identifier.
     * @return {@link Topic} entity with http status 200 OK.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Topic> fetchTopic(@PathVariable Long id) {
        val topic = topicService.findOne(id);
        return new ResponseEntity<>(topic, HttpStatus.OK);
    }

    /**
     * Creates {@link Topic} entity.
     *
     * @param topic json object which represents {@link Topic} entity.
     * @return json representation of created {@link Topic} entity.
     */
    @PostMapping
    public ResponseEntity<Topic> createTopic(@RequestBody Topic topic) {
        val createdTopic = topicService.create(topic);
        return new ResponseEntity<>(createdTopic, HttpStatus.CREATED);
    }

    /**
     * Deletes {@link Topic} entity associated with provided id param.
     *
     * @param id topic identifier.
     * @return http status 204 NO_CONTENT.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity deleteTopic(@PathVariable Long id) {
        topicService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * Updates {@link Topic} entity.
     *
     * @param topic json object which represents {@link Topic} entity.
     * @return json representation of created {@link Topic} entity.
     */
    @PutMapping
    public ResponseEntity updateTopic(@RequestBody Topic topic) {
        val updatedTopic = topicService.update(topic);
        return new ResponseEntity<>(updatedTopic, HttpStatus.OK);
    }

    /**
     * Returns all topics where specified user take participant.
     *
     * @param id user id
     * @return list user topics
     */
    @GetMapping("/user/{id}")
    public ResponseEntity<List<Topic>> getUserTopics(@PathVariable Long id) {
        val userTopics = topicService.findUserTopics(id);
        return new ResponseEntity<>(userTopics, HttpStatus.OK);
    }

    /**
     * Returns number of pages.
     *
     * @return number of pages.
     */
    @GetMapping("/pageCount")
    public ResponseEntity<Long> getPagesCount() {
        Long pageCount = topicService.getCount() / DEFAULT_PAGE_SIZE + 1;
        return new ResponseEntity<>(pageCount, HttpStatus.OK);
    }

    /**
     * Gets {@link Topic} list which corresponds to provided page.
     *
     * @param page identifier.
     * @return {@link Topic} list with http status 200 OK..
     */
    @GetMapping("/fetch")
    public ResponseEntity<List<Topic>> fetchRequestPage(@RequestParam int page) {
        val topics = topicService.fetchPage(page);
        return new ResponseEntity<>(topics, HttpStatus.OK);
    }

    @PostMapping("/message")
    public ResponseEntity<Message> createTopicMessage(@RequestBody Message message) {
        Assert.notNull(message.getText(), "Message has to have text");
        val savedMessage = topicService.saveTopicMessage(message);
        return new ResponseEntity<>(savedMessage, HttpStatus.OK);
    }
}
