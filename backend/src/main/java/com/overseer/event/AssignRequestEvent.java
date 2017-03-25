package com.overseer.event;

import com.overseer.model.Progress;
import com.overseer.model.Request;
import org.springframework.context.ApplicationEvent;

/**
 * Custom ApplicationEvent which appears when ProgressStatus of Request is changed into 'In progress'.
 */
public class AssignRequestEvent extends ApplicationEvent {
    private Request request;
    public static final Progress PROGRESS_STATUS = Progress.IN_PROGRESS;

    public AssignRequestEvent(Object source, Request request) {
        super(source);
        this.request = request;
    }

    public Request getRequest() {
        return request;
    }
}
