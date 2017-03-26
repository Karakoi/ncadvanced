package com.overseer.event;

import com.overseer.model.Request;
import com.overseer.model.enums.ProgressStatus;
import org.springframework.context.ApplicationEvent;

/**
 * Custom ApplicationEvent which appears when ProgressStatus of Request is changed into 'Closed'.
 */
public class CloseRequestEvent extends ApplicationEvent {
    private Request request;
    public static final ProgressStatus PROGRESS_STATUS = ProgressStatus.CLOSED;

    public CloseRequestEvent(Object source, Request request) {
        super(source);
        this.request = request;
    }

    public Request getRequest() {
        return request;
    }
}
