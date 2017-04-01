package com.overseer.event;

import com.overseer.model.Request;
import com.overseer.model.enums.ProgressStatus;
import org.springframework.context.ApplicationEvent;

/**
 * Custom ApplicationEvent which appears when ProgressStatus of Request is changed.
 */
public class ChangeProgressEvent extends ApplicationEvent {
    private Request request;
    private ProgressStatus progressStatus;
    private boolean isHandled;

    public ChangeProgressEvent(Object source, Request request, ProgressStatus progressStatus) {
        super(source);
        this.request = request;
        this.progressStatus = progressStatus;
    }

    public Request getRequest() {
        return request;
    }

    public ProgressStatus getProgressStatus() {
        return progressStatus;
    }

    public boolean isHandled() {
        return isHandled;
    }

    public void setIsHandled(boolean isHandled) {
        this.isHandled = isHandled;
    }
}
