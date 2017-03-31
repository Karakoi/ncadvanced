package com.overseer.service.impl.email;


import com.overseer.model.Request;
import com.overseer.model.User;
import org.springframework.stereotype.Service;

/**
 * Implementation of <code>EmailBuilder</code> interface, that specifies how
 * to build notification email for {@link User} entity.
 */
@Service
public class OfficeManagerNotificationBuilderImpl extends EmailBuilderImpl<Request> {
    private static final String MESSAGE_SUBJECT = "Request progress status changed";

    @Override
    String getMessageRecipient(Request request) {
        User assignee = request.getAssignee();
        return assignee.getEmail();
    }

    @Override
    String getMessageBody(Request request) {
        User assignee = request.getAssignee();
        return "Dear "
                + assignee.getFirstName()
                + " "
                + assignee.getLastName()
                + "\n"
                + "Request: "
                + request.getTitle()
                + " was altered."
                + "\n"
                + "Request progress status was changed to: "
                + request.getProgressStatus().getName();
    }

    @Override
    String getMessageSubject() {
        return MESSAGE_SUBJECT;
    }
}
