package com.overseer.service.impl;

import com.overseer.model.User;
import com.overseer.model.enumreason.MessageReason;
import com.overseer.service.MessageBuilder;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.SimpleMailMessage;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Igor Gnes on 3/3/17.
 */
@PropertySource("classpath:email.properties")
public class MessageBuilderImpl implements MessageBuilder {

    private static Map<MessageReason, SimpleMailMessage> reasonAndTextMessage = new HashMap<>();
    private static final String SUBJECT_FOR_RECOVERING_PASSWORD = "new password";
    private static final String SUBJECT_FOR_CONFIRM_REGISTRY = "welcome to overseer";
    private static final String SUBJECT_FOR_REQUEST_CHANGED = "changed request";

    @Value("${mail.from}")
    private String emailFrom;

    @Override
    public SimpleMailMessage builderMessage(User user, MessageReason reason) {

        // The special number one
        if (reasonAndTextMessage.size() == 0) {
            return generate(user).get(reason);
        }
        return reasonAndTextMessage.get(reason);

       /* the special number two
       switch (reason) {
            case FORGOT_PASSWORD:
                return generateMessageForRecoveringPassword(user);
            case CHANGE_REQUEST:
                return generateMessageForChangeRequest(user);
            case CONFIRM_REGISTRY:
                return generateMessageForConfirmRegistry(user);
            default:
                return null;
        }*/
    }

    /**
     * generate collection Map with reason and prepared email text.
    * */
    private Map<MessageReason, SimpleMailMessage> generate(User user) {

        reasonAndTextMessage.put(MessageReason.FORGOT_PASSWORD, generateMessageForRecoveringPassword(user));
        reasonAndTextMessage.put(MessageReason.CHANGE_REQUEST, generateMessageForChangeRequest(user));
        reasonAndTextMessage.put(MessageReason.CONFIRM_REGISTRY, generateMessageForConfirmRegistry(user));

        return reasonAndTextMessage;
    }

    /**
     * generate SimpleMailMessage if reason is forgot password.
     * */
    private SimpleMailMessage generateMessageForRecoveringPassword(User user) {

        SimpleMailMessage message = buildProperties(user);
        message.setSubject(SUBJECT_FOR_RECOVERING_PASSWORD);
        String text = "Dear "
                + user.getFirstName()
                + " "
                + user.getLastName()
                + "\n"
                + "You requested a password recovery to "
                + user.getEmail()
                + "\n"
                + "Your new password is: "
                + user.getPassword();
        message.setText(text);
        return message;
    }

    /**
     * generate SimpleMailMessage if reason is change request.
     * */
    private SimpleMailMessage generateMessageForChangeRequest(User user) {

        SimpleMailMessage message = buildProperties(user);
        message.setSubject(SUBJECT_FOR_REQUEST_CHANGED);
        String text = "Dear "
                + user.getFirstName()
                + " "
                + user.getLastName()
                + "\n"
                + "Your request is accepted";
        message.setText(text);
        return message;
    }

    /**
    * generate SimpleMailMessage if reason is confirm registry.
    * */
    private SimpleMailMessage generateMessageForConfirmRegistry(User user) {

        SimpleMailMessage message = buildProperties(user);
        message.setSubject(SUBJECT_FOR_CONFIRM_REGISTRY);
        String text =  "Dear "
                + user.getFirstName()
                + " "
                + user.getLastName()
                + "\n"
                + "You successfully registered";
        message.setText(text);
        return message;
    }

    /**
     * generate header of email message.
     */
    private SimpleMailMessage buildProperties(User user) {

        val simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(user.getEmail());
        simpleMailMessage.setFrom(emailFrom);
        return simpleMailMessage;
    }
}
