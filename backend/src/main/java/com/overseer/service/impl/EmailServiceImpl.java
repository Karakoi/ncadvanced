package com.overseer.service.impl;

import com.overseer.exception.EmptyMessageException;
import com.overseer.exception.MessageDestinationException;
import com.overseer.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    private JavaMailSender javaMailSender;

    @Override
    public void sendMessage(SimpleMailMessage message) throws EmptyMessageException, MessageDestinationException {

        if ((message.getTo() == null)) {
            throw new MessageDestinationException();
        }

        if ("".equals(message.getText())) {
            throw new EmptyMessageException();
        }
        javaMailSender.send(message);
    }

    @Override
    public void sendMessageWithAttachments(MimeMessage message) {
        throw new NotImplementedException();
    }
}
