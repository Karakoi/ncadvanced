package com.overseer.service.impl.email;

import com.overseer.model.User;
import com.overseer.service.EmailBuilder;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link EmailBuilder} interface, that specifies how
 * to build recovery email for {@link User} entity.
 */
@Service
public class RecoverBuilderImpl extends EmailBuilderImpl<User> implements EmailBuilder<User> {
    private static final String MESSAGE_SUBJECT = "Password recovery";

    @Override
    String getMessageRecipient(User user) {
        return user.getEmail();
    }

    @Override
    String getMessageSubject() {
        return MESSAGE_SUBJECT;
    }

    @Override
    String getMessageBody(User user) {
        return "Dear "
                + user.getFirstName()
                + " "
                + user.getLastName()
                + "\n"
                + "You requested a password recovery to "
                + user.getEmail()
                + "\n"
                + "Your new password is: "
                + user.getPassword();
    }
}
