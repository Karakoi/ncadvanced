package com.overseer.service.impl;

import com.overseer.service.EmailService;
import org.springframework.mail.SimpleMailMessage;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.mail.internet.MimeMessage;

public class EmailServiceImpl implements EmailService {

    //Todo inject sender bean, write implementation

    @Override
    public void sendMessage(SimpleMailMessage message) {
        throw new NotImplementedException();
    }

    @Override
    public void sendMessageWithAttachments(MimeMessage message) {
        throw new NotImplementedException();
    }
}
