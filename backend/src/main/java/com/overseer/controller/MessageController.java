package com.overseer.controller;

import com.overseer.model.Message;
import com.overseer.model.User;
import com.overseer.service.MessageService;
import com.overseer.service.UserService;
import io.jsonwebtoken.lang.Assert;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller provides api for privet message, sending and getting message.
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MessageController {
    private static final Logger LOG = LoggerFactory.getLogger(MessageController.class);

    private final UserService userService;
    private final MessageService messageService;

    @GetMapping("/users/managersByEmp")
    public ResponseEntity<List<User>> getManagersByEmployee(@RequestParam Long empId) {
        val managers = userService.findManagersByEmployee(empId);
        LOG.debug("Fetched {} managers for employee with id: {}", managers.size(), empId);
        return new ResponseEntity<>(managers, HttpStatus.OK);
    }

    @GetMapping("/users/empByManager")
    public ResponseEntity<List<User>> getUsersByManager(@RequestParam Long managerId) {
        val users = userService.findUsersByManager(managerId);
        LOG.debug("Fetched {} users for manager with id: {}", users.size(), managerId);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("/sendMessage")
    public void sendMessageToEmail(@RequestBody Message message) {
        Assert.notNull(message.getText(), "Message has to have text");
        Assert.notNull(message.getRecipient(), "Message has to have recipient");
        messageService.create(message);
    }

    @GetMapping("/messagesByRecipient")
    public ResponseEntity<List<Message>> getMessagesByRecipient(@RequestParam Long recipient,
                                                                @RequestParam int pageNumber) {
        val messages = messageService.findByRecipient(recipient, pageNumber);
        LOG.debug("Fetched {} messages for recipient with id: {}", messages.size(), recipient);
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }
}
