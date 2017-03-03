package com.overseer.service.impl;

import com.overseer.model.User;
import com.overseer.model.modelEnum.MessageReason;
import com.overseer.service.MessageBuilder;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.SimpleMailMessage;

/**
 * @author Igor Gnes on 3/3/17.
 */
@PropertySource("classpath:email.properties")
public class MessageBuilderImpl implements MessageBuilder {

    private static final String SUBJECT_FOR_RECOVERING_PASSWORD = "new password";

    @Value("${mail.from}")
    private String emailFrom;

    @Override
    public SimpleMailMessage builderMessage(User user, MessageReason reason) {

        val simpleMailMessage = buildProperties(user);

        switch (reason) {
            case FORGOT_PASSWORD:
                simpleMailMessage.setSubject(SUBJECT_FOR_RECOVERING_PASSWORD);
                simpleMailMessage.setText(generateMessageForRecoveringPassword(user));
                break;
            case CHANGE_REQUEST:
                // TODO the same
                break;
            case CONFIRM_RECOVERY:
                // TODO the same
                break;
        }
        return simpleMailMessage;
    }

    private String generateMessageForRecoveringPassword(User user) {
        // here can be StringBuilder
        return "Dear " + user.getFirstName() + "....";
    }

    private SimpleMailMessage buildProperties(User user) {

        val simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(emailFrom);
        simpleMailMessage.setFrom(user.getEmail());
        return simpleMailMessage;
    }
}
