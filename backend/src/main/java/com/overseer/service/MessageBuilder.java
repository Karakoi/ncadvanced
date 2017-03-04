package com.overseer.service;

import com.overseer.model.User;
import com.overseer.model.enumreason.MessageReason;
import org.springframework.mail.SimpleMailMessage;

/**
 * @author Igor Gnes on 3/3/17.
 */
@FunctionalInterface
public interface MessageBuilder {

    SimpleMailMessage builderMessage(User user, MessageReason reason);
}
