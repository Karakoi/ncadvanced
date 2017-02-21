package com.overseer.service;

import com.overseer.exception.email.EmptyMessageException;
import com.overseer.exception.email.MessageDestinationException;
import org.springframework.mail.SimpleMailMessage;

import javax.mail.internet.MimeMessage;

/**
 * Email service, which process messages and send it.
 */
public interface EmailService {

     /**
     * Method just send a message.
     * @param message contains text, subject, recipient or recipients.
     */
     void sendMessage(SimpleMailMessage message) throws EmptyMessageException, MessageDestinationException;

     /**
     * Method send message with some attachment.
     * @param message contains text, subject, recipient or recipients and link to resource on classpath.
     */
     void sendMessageWithAttachments(MimeMessage message);
}
