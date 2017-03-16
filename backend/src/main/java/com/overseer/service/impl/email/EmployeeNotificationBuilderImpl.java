package com.overseer.service.impl.email;

import com.overseer.model.Request;
import com.overseer.model.User;
import org.springframework.stereotype.Service;

/**
 * Implementation of <code>EmailBuilder</code> interface, that specifies how
 * to build notification email for {@link User} entity.
 */
@Service
public class EmployeeNotificationBuilderImpl extends EmailBuilderImpl<Request> {
    private static final String MESSAGE_SUBJECT = "Request progress status changed 1 ";

    @Override
    String getMessageRecipient(Request request) {
        User reporter = request.getReporter();
        return reporter.getEmail();
    }

    @Override
    String getMessageBody(Request request) {
        User reporter = request.getReporter();
        return "Dear "
                + reporter.getFirstName()
                + " "
                + reporter.getLastName()
                + "\n"
                + "Request: ["
                + request.getTitle()
                + "] was altered."
                + "\n"
                + "Request progress status was changed to: "
                + request.getProgressStatus();
    }

    @Override
    String getMessageSubject() {
        return MESSAGE_SUBJECT;
    }
}
