package com.overseer.event;

import com.overseer.model.Request;
import org.springframework.context.ApplicationEvent;

/**
 * Custom ApplicationEvent which appears when ProgressStatus of Request is changed.
 */
public class RequestChangeEvent extends ApplicationEvent {
    private Request request;

    public RequestChangeEvent(Object source, Request request) {
        super(source);
        this.request = request;
    }

    public Request getRequest() {
        return request;
    }
}
