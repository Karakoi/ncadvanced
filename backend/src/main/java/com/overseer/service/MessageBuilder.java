package com.overseer.service;

import com.overseer.model.User;
import com.overseer.model.modelEnum.MessageReason;
import org.springframework.mail.SimpleMailMessage;

/**
 * @author Igor Gnes on 3/3/17.
 */
public interface MessageBuilder {

    SimpleMailMessage builderMessage(User user, MessageReason forgotPassword);
}
