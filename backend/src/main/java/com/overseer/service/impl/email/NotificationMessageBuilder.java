package com.overseer.service.impl.email;

import com.overseer.model.Request;
import com.overseer.model.User;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

/**
 * Email factory which create message for notification about changing request.
 */
@Component
public class NotificationMessageBuilder {
    /**
     * Return ready to send message.
     * @param request request which has been changed.
     * @param user user which have to get notification.
     * @return message.
     */
    public SimpleMailMessage getMessageBody(Request request, User user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setText("Dear "
                + user.getFirstName()
                + " "
                + user.getLastName()
                + "\n"
                + "Request: ["
                + request.getTitle()
                + "] was altered."
                + "\n"
                + "Request progress status was changed to: "
                + request.getProgressStatus());
        message.setTo(user.getEmail());
        message.setSubject("Your request has been updated");
        return message;
    }
}
