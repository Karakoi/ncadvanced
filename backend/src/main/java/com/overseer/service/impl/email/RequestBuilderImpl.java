package com.overseer.service.impl.email;

import com.overseer.model.Request;
import com.overseer.model.User;
import com.overseer.service.EmailBuilder;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link EmailBuilder} interface, that specifies how
 * to build notification email for {@link Request} entity.
 */
@Service
public class RequestBuilderImpl extends EmailBuilderImpl<Request> implements EmailBuilder<Request> {
    private static final String MESSAGE_SUBJECT = "Request status changed";

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
                + "Your request: "
                + request.getTitle()
                + " was altered."
                + "\n"
                + "Request priority status was changed to: "
                + request.getPriorityStatus();
    }

    @Override
    String getMessageSubject() {
        return MESSAGE_SUBJECT;
    }
}
