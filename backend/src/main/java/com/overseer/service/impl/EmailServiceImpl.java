package com.overseer.service.impl;

import com.overseer.exception.MessageDestinationException;
import com.overseer.exception.email.EmptyMessageException;
import com.overseer.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


import javax.mail.internet.MimeMessage;

/**
 * Implementation of {@link com.overseer.service.EmailService} interface.
 */
@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    private JavaMailSender javaMailSender;

    @Override
    public void sendMessage(SimpleMailMessage message) throws EmptyMessageException, MessageDestinationException {
        if ("".equals(message.getText())) {
            throw new EmptyMessageException();
        }

        if ((message.getTo() == null)) {
            throw new MessageDestinationException();
        }


        javaMailSender.send(message);
    }

    @Override
    public void sendMessageWithAttachments(MimeMessage message) {
        throw new UnsupportedOperationException();
    }
}
