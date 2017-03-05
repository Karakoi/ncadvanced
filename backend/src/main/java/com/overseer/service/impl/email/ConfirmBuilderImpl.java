package com.overseer.service.impl.email;

import com.overseer.model.User;
import com.overseer.service.EmailBuilder;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link EmailBuilder} interface, that specifies how
 * to build confirmation email for {@link User} registration.
 */
@Service
public class ConfirmBuilderImpl extends EmailBuilderImpl<User> implements EmailBuilder<User> {
    private static final String MESSAGE_SUBJECT = "Confirm registration to Overseer";

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
                + "You successfully registered";
    }
}
