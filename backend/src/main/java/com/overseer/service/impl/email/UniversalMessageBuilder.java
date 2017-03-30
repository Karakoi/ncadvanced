package com.overseer.service.impl.email;

import com.overseer.model.Request;
import com.overseer.model.User;
import lombok.val;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

/**
 * Email factory which create message for request subscriber.
 */
@Component
public class UniversalMessageBuilder {

    /**
     * Returen ready to send message.
     * @param request request which has been changed.
     * @param user user which have to get notification.
     * @return ready to send message.
     */
    public SimpleMailMessage getMessageBody(Request request, User user) {
        val message = new SimpleMailMessage();
        message.setText("Dear "
                + user.getFirstName()
                + " "
                + user.getLastName()
                + "\n"
                + "Request you're following: "
                + request.getTitle()
                + " was altered."
                + "\n"
                + "Request priority status was changed to: "
                + request.getPriorityStatus());
        message.setTo(user.getEmail());
        message.setSubject("Your request has been updated");
        return message;
    }
}
