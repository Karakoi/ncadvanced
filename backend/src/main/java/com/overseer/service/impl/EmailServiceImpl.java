package com.overseer.service.impl;

import com.overseer.exception.email.EmptyMessageException;
import com.overseer.exception.email.MessageDestinationException;
import com.overseer.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.internet.MimeMessage;

/**
 * Implementation of {@link com.overseer.service.EmailService} interface.
 */
@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender javaMailSender;

    /**
     * {@inheritDoc}.
     */
    @Override
    @Async
    public void sendMessage(SimpleMailMessage message) {
        if ("".equals(message.getText())) {
            throw new EmptyMessageException("Supplied message contained no text " + message);
        }
        if (message.getTo() == null) {
            throw new MessageDestinationException("Supplied message contained no destination " + message);
        }
        log.debug("Sending message with subject: {} to: {}", message.getSubject(), message.getTo());
        this.javaMailSender.send(message);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void sendMessageWithAttachments(MimeMessage message) {
        throw new UnsupportedOperationException();
    }
}
