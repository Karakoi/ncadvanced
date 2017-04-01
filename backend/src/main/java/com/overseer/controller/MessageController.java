package com.overseer.controller;

import com.overseer.model.Message;
import com.overseer.service.MessageService;
import io.jsonwebtoken.lang.Assert;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller provides api for privet message, sending and getting message.
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    /**
     * @param message open message from frontend that going to be encrypted.
     * @return decrypted message.
     */
    @PostMapping("/sendMessage")
    public ResponseEntity<Message> sendMessageToEmail(@RequestBody Message message) {
        Assert.notNull(message.getText(), "Message has to have text");
        val encryptMessage = messageService.encryptMessage(message);
        val savedMessage = messageService.create(encryptMessage);
        val decryptMessage = messageService.decryptMessage(savedMessage);
        return new ResponseEntity<>(decryptMessage, HttpStatus.OK);
    }

    @GetMapping("/messagesByTopic")
    public ResponseEntity<List<Message>> getMessagesByTopic(@RequestParam Long topicId) {
        val messages = messageService.findByTopic(topicId);
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    @GetMapping("/messagesByDialog")
    public ResponseEntity<List<Message>> findDialogMessages(@RequestParam Long senderId,
                                                            @RequestParam Long recipientId) {
        val messages = messageService.findDialogMessages(senderId, recipientId);
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    @DeleteMapping("messages/{id}")
    public ResponseEntity deleteMessage(@PathVariable Long id) {
        messageService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
