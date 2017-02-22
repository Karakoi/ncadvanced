package com.overseer.service.impl;

import com.overseer.exception.email.EmptyMessageException;
import com.overseer.exception.email.MessageDestinationException;
import com.overseer.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

/**
 * Implementation of {@link com.overseer.service.EmailService} interface.
 */
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    /**
     * {@inheritDoc}.
     */
    @Override
    public void sendMessage(SimpleMailMessage message) throws EmptyMessageException, MessageDestinationException {
        if ("".equals(message.getText())) {
            throw new EmptyMessageException();
        }
        if (message.getTo() == null) {
            throw new MessageDestinationException();
        }
        javaMailSender.send(message);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void sendMessageWithAttachments(MimeMessage message) {
        throw new UnsupportedOperationException();
    }
}
