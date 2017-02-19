package com.overseer.service.impl;

import com.overseer.service.EmailService;
import org.springframework.mail.SimpleMailMessage;

import javax.mail.internet.MimeMessage;

/**
 * @{inheritDoc}.
 */
public class EmailServiceImpl implements EmailService {

    //Todo inject sender bean, write implementation

    @Override
    public void sendMessage(SimpleMailMessage message) {
        throw new UnsupportedOperationException();
    }


    @Override
    public void sendMessageWithAttachments(MimeMessage message) {
        throw new UnsupportedOperationException();
    }
}
